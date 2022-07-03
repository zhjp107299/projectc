<template>
    <div class="test-box">
        <el-col span="6">
            <el-container>
                <el-header>判断题库练习—10题</el-header>
                <el-main class="content-style">
                    <div class="type-box">
                        <el-row style="margin-left: 10px;height: 40px;">判断题</el-row>
                        <el-row style="margin-top:-10px;width: 340px;line-height: 20px;">
                            <span style="margin-left: 10px"></span><el-button type="mini" @click="jumpTo(index+1,'judge')" v-for="(judge,index) in judgeArray" :index="index">{{index+1}}</el-button>
                        </el-row>
                    </div>
                </el-main>
            </el-container>
        </el-col>
        <div class="type-title">判断题—10题（共100分，10题，每题10分）</div>
        <JudgesItem style="margin-left: 140px"  :class="`judgeJump${index+1}`" v-for="(judge,index) in judgeArray" :index="index"  :item="judge"/>
        <el-button @click="changeVisibleToTrue" type="primary" plain style="text-align: center;width: 160px;height: 50px;margin-left: 625px;">提交</el-button>

        <el-dialog
                :before-close="handleClose"
                :close-on-click-modal="false"
                :visible.sync="centerDialogVisible"
                width="30%"
                center>
            <i class="el-icon-loading" style="margin: 0 auto;margin-top: -30px"></i>
            &emsp;
            <span style="margin-top: -100px;">正在提交后台评分中...</span>
            <el-progress  :format="format" :stroke-width="26" :percentage="percentage"></el-progress>
            <span slot="footer" class="dialog-footer">
  </span>
        </el-dialog>
    </div>
</template>

<script>
    import axios from '../../../api/index'
    import JudgesItem from "./JudgesItem";

    import global_judgeValues from '../../../global/Global'

    import {bankJudgeScoring} from '../../../global/scoring_sys'


    export default {
        name: "BankJudge",
        components: {JudgesItem},
        data() {
            return {
                judgeAnswers: [],
                percentage: 0,
                centerDialogVisible: false,
                judgeValues: global_judgeValues.judgeValues,
                judgeArray: [],
            }
        },
        methods: {
            handleClose(done){
                this.$confirm('正在提交，请勿关闭！')
                    .then(_ => {})
                    .catch(_ => {});
            },
            jumpTo(index,type){
                let jump = '';
                let el;
                switch (type) {
                    case 'judge':
                        jump = "judgeJump"+index;
                        break;
                }
                el = document.getElementsByClassName(jump)[0];
                //console.log(el.offsetTop);
                this.$nextTick(function () {
                    window.scrollTo({
                        "behavior":"smooth",
                        "top":el.offsetTop
                    })
                })
            },
            format(percentage) {
                return percentage >= 100 ? '100%' : `${percentage}%`;
            },
            changeVisibleToTrue(){
                if (this.judgeValues.length!==10){
                    this.$confirm('练习未完成, 是否提交?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.$message({
                            type: 'success',
                            message: '正在提交'
                        });
                        this.submitChapterTest();
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消提交'
                        });
                    });
                } else{
                    this.$confirm('是否提交?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.$message({
                            type: 'success',
                            message: '正在提交'
                        });
                        this.submitChapterTest();
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消提交'
                        });
                    });

                }
            },
            submitChapterTest(){
                this.centerDialogVisible = true;
                //调用评分方法,拿到分数
                let score = bankJudgeScoring(this.judgeAnswers, this.judgeValues);

                //拿到分数后清空答案数组，可能会有缓存什么的
                this.judgeValues = [];

                //判断是否合格
                let passOrNot;
                if (score>=60){
                    passOrNot = '合格';
                }else {
                    passOrNot = '不合格';
                }
                let interval = setInterval(()=>{
                    if (this.percentage>=100){
                        this.$router.replace({
                            path: '/hs/scored_bank',
                            query:{
                                score:score,
                                passOrNot:passOrNot
                            }
                        });

                        //停止计时器
                        clearInterval(interval);
                    }
                    if (this.percentage<100) {
                        this.percentage += Math.floor(Math.random()*10)+10;
                    }
                },Math.random()*1000+500);
            },
            getChapterData(){
                axios({
                    method: 'post',
                    url: '/bank_judge',
                    data: this.$qs.stringify({N:10})
                }).then(res=>{
                    console.log(res);
                    if (res.data.info.code===200){

                        this.judgeArray = res.data.data;
                        //拿到判断题正确答案
                        for (let i=0;i<this.judgeArray.length;i++){
                            let judge = {seq: i,answerRight: this.judgeArray[i].answerRight};
                            this.judgeAnswers.push(judge);
                        }
                    }
                }).catch(err=>{
                    console.log(err);
                });
            },
        },
        beforeRouteLeave(to,from,next){
            if (window.confirm("数据未保存（若是提交请忽略），确定离开此页面吗？")){
                next();
            }else {
                next(false);
            }
        },
        mounted() {
            this.getChapterData();

            window.onbeforeunload = function (e) {
                e = e || window.event;  //兼容IE8和FireFox4 之前的版本

                if (e){     //
                    e.returnValue = '数据尚未提交，是否离开？';
                }
                return '数据尚未提交，是否离开？'
            }
        },

    }
</script>

<style type="scss" scoped>

    .test-box >>> .el-progress-bar {
        padding-right: 62px;width: 96%;
    }
    .type-box {
        height: 90px;
    }

    .el-button {
        text-align: center;
        padding: 12px 24px 12px 14px;
        width: 38px;height: 22px;line-height: 0.2px;
    }




    .type-title {
        margin-left: 100px
    }
    .content-style {
        text-align: left;
        padding-left: 1px;
        background-color: #E9EEF3;
    }
    .el-header {
        width: 300px;
        line-height: 60px;
        height: 40px;
        background-color: #E9EEF3;
        color: #333;
        margin-top: 60px;
        text-align: center;

    }
    .el-main {
        line-height: 40px;
    }

    .test-box {
        background-color: #E9EEF3;
        color: #333;
        line-height: 160px;
    }
    .el-container {
        margin-left:1350px;position: fixed;
    }
</style>