<template>
    <div class="test-box">
        <el-col span="6">
            <el-container style="margin-left:1250px;position: fixed;">
                <el-header>模拟全国计算机二级C语言测试({{this.$global.testTag}})</el-header>
                <el-main class="content-style">
                    <div class="type-box">
                        <el-row style="margin-left: 10px;height: 40px;">选择题</el-row>
                        <el-row style="margin-top:-10px;width: 400px;line-height: 20px;">
                            <span style="margin-left: 10px"></span><el-button type="mini" @click="jumpTo(index+1,'choice')" v-for="(choice,index) in choiceArray" :index="index">{{index+1}}</el-button>
                        </el-row>
                    </div>
                    <div class="type-box" style="margin-top: 130px;">
                        <el-row style="margin-left: 10px;height: 40px;">填空题</el-row>
                        <el-row style="margin-top:-10px;width: 400px;line-height: 20px;">
                            <span style="margin-left: 10px"></span><el-button type="mini" @click="jumpTo(index+1,'gapfilling')" v-for="(gapfilling,index) in gapfillingArray" :index="index">{{index+1}}</el-button>
                        </el-row>
                    </div>
                    <div class="type-box">
                        <el-row style="margin-left: 10px;height: 40px;margin-top: 30px;">编程题</el-row>
                        <el-row style="margin-top:-10px;width: 400px;line-height: 20px;">
                            <span style="margin-left: 10px"></span><el-button type="mini" @click="jumpTo(index+1,'programme')" v-for="(programme,index) in programmeArray" :index="index">{{index+1}}</el-button>
                        </el-row>
                    </div>
                    <div style="margin-top: 20px">
                        <el-row>
                            <el-progress :percentage="100" stroke-width="0" type="circle" style="margin-top: 50px;margin-right:150px; float: right" :format="formatTimer"></el-progress>
                        </el-row>
                        <el-row>
                            <el-progress width="200" stroke-width="10" style="float: right;margin-top: -163px;margin-right: 112px"  :percentage="percentageTimer" :format="formatNull" type="circle"></el-progress>
                        </el-row>

                    </div>


                </el-main>
            </el-container>
        </el-col>
        <div class="type-title">一、选择题（在下列各题的A)、B)、C)、D)四个选项中，只有一个选项是正确的。（（1-10每小题2分，11-50每小题1分）共60分））</div>
        <ChoicesItem style="margin-left: 100px"  :class="`choiceJump${index+1}`" v-for="(choice,index) in choiceArray" :index="index" :item="choice"/>
        <div class="type-title">二、填空题（请将每一个空的正确答案写在方框中。（20题，每题2分，共40分））</div>
        <GapFillingsItem  style="margin-left: 100px"   :class="`gapfillingJump${index+1}`" v-for="(gapfilling,index) in gapfillingArray"  :index="index" :item="gapfilling"/>
        <div class="type-title">四、程序填空题（请将以下程序补充完整。（4题，每题15分,每空5分，共60分））</div>
        <ProgrammesItem style="margin-left: 100px"  :class="`programmeJump${index+1}`" v-for="(programme,index) in programmeArray"  :index="index" :item="programme"/>
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
    import axios from '../../api/index'
    import ChoicesItem from "./ChoicesItem";
    import GapFillingsItem from "./GapFillingsItem";
    import ProgrammesItem from "./ProgrammesItem";

    import global_choiceValues from '../../global/Global'
    import global_gapfillingValues from '../../global/Global'
    import global_programmeValues1 from '../../global/Global'

    import {cScoring} from '../../global/scoring_sys'

    export default {
        name: 'CSimple',
        components: {ProgrammesItem, GapFillingsItem, ChoicesItem},
        data() {
            return {
                programmeAnswers: [],
                gapfillingAnswers: [],
                choiceAnswers: [],

                interval2: '',
                percentageTimer: 0,

                timer :{
                    interval: '',
                    hour: 2,
                    minutes: 0,
                    seconds: 1,

                },

                percentage: 0,
                centerDialogVisible: false,

                programmeValues: global_programmeValues1.programmeValues,
                gapfillingValues: global_gapfillingValues.gapfillingValues,
                choiceValues: global_choiceValues.choiceValues,

                programmeArray: [],
                gapfillingArray: [],
                choiceArray: [],
            }
        },
        methods: {
            handleClose(done){
                this.$confirm('正在提交，请勿关闭！')
                    .then(_ => {})
                    .catch(_ => {});
            },
            formatNull(percentage){
                return  "";
            },
            changeSeconds(){
                this.timer.seconds--;
                this.percentageTimer+=0.0138;
            },
            //捣鼓了一下午的坑，format函数会自己跑一次，为自己的组件赋初值，所以此时给他就行了
            //然后还要注意：format函数内的参数改变也会导致组件执行一次：format函数，有点像组件一直监听者
            formatTimer(percentage){
                if (this.timer.hour===2 && this.timer.minutes===0 && this.timer.seconds===1){
                    console.log('0初始化');
                    return percentage = '0'+this.timer.hour+':'
                        +(this.timer.minutes<10?'0'+this.timer.minutes:this.timer.minutes) +':'
                        +(this.timer.seconds<10?'0'+this.timer.seconds:this.timer.seconds);
                }
                if (this.timer.hour===0 && this.timer.minutes===0 && this.timer.seconds<0){
                    this.$alert('时间已到，已自动提交！', '提示', {
                        confirmButtonText: '确定',
                        callback: action => {
                            this.$message({
                                type: 'info',
                                message: `正在提交`
                            });
                        }
                    });
                    this.submitChapterTest();
                    interval(this.interval);

                }
                if (this.timer.seconds < 0){
                    this.timer.seconds = 59;
                    this.timer.minutes--;
                    if (this.timer.minutes<0){
                        this.timer.hour--;
                        this.timer.minutes = 59;
                    }
                }
                return percentage = '0'+this.timer.hour+':'
                    +(this.timer.minutes<10?'0'+this.timer.minutes:this.timer.minutes) +':'
                    +(this.timer.seconds<10?'0'+this.timer.seconds:this.timer.seconds);
            },
            jumpTo(index,type){
                let jump = '';
                let el;
                switch (type) {
                    case 'choice':
                        jump = "choiceJump"+index;
                        break;
                    case 'gapfilling':
                        jump = "gapfillingJump"+index;
                        break;
                    case 'programme':
                        jump = "programmeJump"+index;
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
                if (this.choiceValues.length!==50 && this.gapfillingValues.length !== 20
                    && this.programmeValues.length!==4){
                    this.$confirm('试卷未完成, 是否提交?', '提示', {
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
                    this.$confirm('时间未到, 是否提交?', '提示', {
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
                let score = cScoring(this.choiceAnswers,this.gapfillingAnswers,this.programmeAnswers
                    ,this.choiceValues,this.gapfillingValues,this.programmeValues);

                let spendHour = 1-this.timer.hour;
                if (spendHour===0){
                    let spendTime = spendHour+'时'+(60-this.timer.minutes)+'分'+(60-this.timer.seconds+'秒');
                }
                let spendTime = spendHour+'时'+(29-this.timer.minutes)+'分'+(60-this.timer.seconds+'秒');
                //判断是否合格
                let passOrNot;
                if (score>=96){
                    passOrNot = '合格';
                }else {
                    passOrNot = '不合格';
                }
                let interval = setInterval(()=>{
                    if (this.percentage>=100){
                        this.$router.replace({
                            path: '/hs/scored_c',
                            query:{
                                spendTime: spendTime,
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
                //获取类型
                let sType = '';
                let type = this.$global.testTag.substring(5);
                switch (type) {
                    case '简单':
                        sType = 'simple';
                        break;
                    case '中等':
                        sType= 'middle';
                        break;
                    case '困难':
                        sType = 'hard';
                        break;
                }
                axios({
                    method: 'post',
                    url: '/c_type',
                    data: this.$qs.stringify({type: sType})
                }).then(res=>{
                    console.log(res);
                    if (res.data.info.code===200){
                        this.choiceArray = res.data.data.choice_50;
                        this.gapfillingArray = res.data.data.gapFilling_20;
                        this.programmeArray = res.data.data.programme_4;

                        //拿到选择题正确答案
                        for (let i=0;i<this.choiceArray.length;i++){
                            let choice = {seq: i,answerRight: this.choiceArray[i].choiceRight};
                            this.choiceAnswers.push(choice);
                        }

                        //拿到填空题正确答案
                        for (let i=0;i<this.gapfillingArray.length;i++){
                            let gapfilling = {seq: i,answerRight1: this.gapfillingArray[i].answerRight1
                                ,answerRight2: this.gapfillingArray[i].answerRight2,answerRight3: this.gapfillingArray[i].answerRight3
                                ,answerRight4: this.gapfillingArray[i].answerRight4,};
                            this.gapfillingAnswers.push(gapfilling);
                        }

                        //获取编程题正确答案
                        for (let i=0;i<this.programmeArray.length;i++){
                            let programme = {seq: i,answerRight1: this.programmeArray[i].answer1
                                ,answerRight2: this.programmeArray[i].answer2,answerRight3: this.programmeArray[i].answer3};
                            this.programmeAnswers.push(programme);
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
            this.interval = setInterval(this.changeSeconds,1000);
            this.interval2 = setInterval(this.formatNull,1000);

            window.onbeforeunload = function (e) {
                e = e || window.event;  //兼容IE8和FireFox4 之前的版本

                if (e){     //
                    e.returnValue = '数据尚未提交，是否离开？';
                }
                return '数据尚未提交，是否离开？'
            }
        },
        destroyed() {
            clearInterval(this.interval);
            clearInterval(this.interval2);
        }
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
        width: 38px;height: 22px;line-height: 0.2px
    }




    .type-title {
        margin-left: 50px
    }
    .content-style {
        text-align: left;
        padding-left: 1px;
        background-color: #E9EEF3;
    }
    .el-header {
        width: 400px;
        line-height: 60px;
        height: 40px;
        background-color: #E9EEF3;
        color: #333;
        margin-top: 60px;
    }
    .el-main {
        line-height: 40px;
    }

    .test-box {
        background-color: #E9EEF3;
        color: #333;
        line-height: 160px;
    }
</style>