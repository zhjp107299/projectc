<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>题库练习成绩</span>
            </div>
            <div class="text item">
                <span style="">满分：100分</span>
                <span style="margin-left: 200px;">合格：60分</span>
            </div>
            <div class="text item">
                <span>你的得分为：{{score}}</span>
                <span style="margin-left: 176px;">是否合格：{{passOrNot}}</span>
            </div>
            <div class="text item">
                <span>备注：<el-tag size="middle">{{this.$global.testTag}}</el-tag></span>
            </div>
            <div class="text item">
                <span>测试次数：{{seq+1}}/不限次数</span>
            </div>
            <el-button @click="goBack" type="primary" plain style="width: 160px;height: 50px;margin-top: 100px">返回首页</el-button>
        </el-card>

        </div>

</template>

<script>
    import axios from "../api";

    export default {
        name: "ScoredPageBank",
        data(){
            return {
                id: '',
                count: '',
                seq: '',
                passOrNot: '',
                score: '',
            }
        },
        methods: {
            goBack(){
                this.$router.push('/home_student');
            },
            getSeqAndStoreData(){
                //获取练习题数
                this.count = parseInt(this.$global.testTag.substring(4));
                //获取类型
                this.type = this.$global.testTag.substring(0,3);
                axios({
                    method: 'post',
                    url: '/seq_bank',
                    data: this.$qs.stringify({id:this.id,type:this.type,count:this.count})
                }).then(res=>{
                    console.log(res);
                    if (res.data.info.code===200){
                        this.seq = res.data.data;
                    }

                    //持久化分数
                    axios({
                        method: 'post',
                        url: '/score_store_bank',
                        data: this.$qs.stringify({id:this.id,type:this.type,count:this.count,score:this.score})
                    }).then(res=>{
                        console.log(res);
                    }).catch(err=>{
                        console.log(err);
                    });

                }).catch(err=>{
                    console.log(err);
                })
            },
        },

        mounted() {
            this.id = JSON.parse(localStorage.getItem('loginUser')).sId;
            this.score = this.$route.query.score;
            this.passOrNot = this.$route.query.passOrNot;
            this.getSeqAndStoreData();
            //全局变量置为空
            this.$global.choiceValues = [];
            this.$global.gapfillingValues = [];
            this.$global.judgeValues = [];
            this.$global.programmeValues = [];
        }
    }
</script>

<style scoped>
    .clearfix {
        margin: -50px auto;
    }
    .text {
        font-size: 14px;
    }

    .item {
        margin-top: -60px;
        text-align: left;
        letter-spacing: 2px;
        margin-bottom: -100px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 880px;
        height: 600px;
        margin: 0 auto;
    }
</style>