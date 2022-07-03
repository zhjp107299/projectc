<template>
    <div class="test-box">
        <el-col span="6">
            <el-container style="margin-left:1100px;position: fixed;">
                <el-header>自主命题测试</el-header>
                <el-main class="content-style">
                    <div class="type-box">
                        <el-row style="margin-left: 10px;height: 40px;">选择题</el-row>
                        <el-row style="margin-top:-10px;width: 340px;line-height: 20px;">
                            <span style="margin-left: 10px"></span><el-button @click="jumpTo(index+1,'choice')" v-for="(choice,index) in choiceArray" :index="index">{{index+1}}</el-button>
                        </el-row>
                    </div>
                    <div class="type-box">
                        <div style="margin-left: 10px;height: 40px;">填空题</div>
                        <el-row style="margin-top:-10px;width: 340px;line-height: 20px;">
                            <span style="margin-left: 10px"></span><el-button @click="jumpTo(index+1,'gapfilling')" v-for="(gapfilling,index) in gapfillingArray" :index="index">{{index+1}}</el-button>
                        </el-row>
                    </div>
                    <div class="type-box">
                        <el-row style="margin-left: 10px;height: 40px;">判断题</el-row>
                        <el-row style="margin-top:-10px;width: 340px;line-height: 20px;">
                            <span style="margin-left: 10px"></span><el-button type="mini" @click="jumpTo(index+1,'judge')" v-for="(judge,index) in judgeArray" :index="index">{{index+1}}</el-button>
                        </el-row>
                    </div>
                    <div class="type-box">
                        <el-row style="margin-left: 10px;height: 40px;">程序填空题</el-row>
                        <el-row style="margin-top:-10px;width: 340px;line-height: 20px;">
                            <span style="margin-left: 10px"></span><el-button type="mini" @click="jumpTo(index+1,'programme')" v-for="(programme,index) in programmeArray" :index="index">{{index+1}}</el-button>
                        </el-row>
                    </div>


                </el-main>
            </el-container>
        </el-col>
        <div class="type-title">一、选择题（共24分，12题，每题2分）</div>
        <ChoicesItem style="margin-left: 50px"  :class="`choiceJump${index+1}`" v-for="(choice,index) in choiceArray" :index="index" :item="choice"/>
        <div class="type-title">二、填空题（共16分，8题，每题2分）</div>
        <GapFillingsItem  style="margin-left: 50px"   :class="`gapfillingJump${index+1}`" v-for="(gapfilling,index) in gapfillingArray"  :index="index" :item="gapfilling"/>
        <div class="type-title">三、判断题（共12题，24分，每题2分）</div>
        <JudgesItem style="margin-left: 50px"  :class="`judgeJump${index+1}`" v-for="(judge,index) in judgeArray" :index="index"  :item="judge"/>
        <div class="type-title" style="margin-right: -100px;">四、程序填空题（共36分，3题，每题12分,每空4分）</div>
        <ProgrammesItem style="margin-left: 50px"   :class="`programmeJump${index+1}`" v-for="(programme,index) in programmeArray"  :index="index" :item="programme"/>

    </div>
</template>

<script>
    import axios from '../../../api/index'
    import ChoicesItem from "./ChoicesItem";
    import GapFillingsItem from "./GapFillingsItem";
    import JudgesItem from "./JudgesItem";
    import ProgrammesItem from "./ProgrammesItem";


    export default {
        components: {ProgrammesItem, JudgesItem, GapFillingsItem, ChoicesItem},
        data() {
            return {

                programmeArray: [],
                judgeArray: [],
                gapfillingArray: [],
                choiceArray: [],
            }
        },
        methods: {
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
                    case 'judge':
                        jump = "judgeJump"+index;
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


            getIndepropoData(){
                axios({
                    method: 'post',
                    url: '/indepropo_all',
                    data: this.$qs.stringify({indepropoName:this.$global.testName})
                }).then(res=>{
                    console.log(res);
                    if (res.data.info.code===200){
                        this.choiceArray = res.data.data.choiceList;
                        this.gapfillingArray = res.data.data.gapFillingList;
                        this.judgeArray = res.data.data.judgeList;
                        this.programmeArray = res.data.data.programmeList;

                    }
                }).catch(err=>{
                    console.log(err);
                });
            },
        },
        mounted() {
            this.getIndepropoData();
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
        margin-left: -1100px
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
</style>