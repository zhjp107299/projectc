<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>模拟C测试成绩</span>
            </div>
            <div class="text item">
                <span style="">满分：160分</span>
                <span style="margin-left: 200px;">合格：96分</span>
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
        name: "ScoredPageC",
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
            getSeqAndStoreData(){

                //获取类型
                this.type = this.$global.testTag.substring(5);
                axios({
                    method: 'post',
                    url: '/seq_c',
                    data: this.$qs.stringify({id:this.id,type:this.type})
                }).then(res=>{
                    console.log(res);
                    if (res.data.info.code===200){
                        this.seq = res.data.data;
                    }

                    //持久化分数
                    axios({
                        method: 'post',
                        url: '/score_store_c',
                        data: this.$qs.stringify({id:this.id,type:this.type,score:this.score})
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
            alert('您的分数为：'+this.$route.query.score+'    '+'用时：'+this.$route.query.spendTime);
            this.id = JSON.parse(localStorage.getItem('loginUser')).sId;
            this.score = this.$route.query.score;
            this.passOrNot = this.$route.query.passOrNot;
            this.getSeqAndStoreData();
            this.$global.choiceValues = [];
            this.$global.gapfillingValues = [];
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