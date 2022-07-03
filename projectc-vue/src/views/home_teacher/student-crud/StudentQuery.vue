<template>
    <div>
        <el-row>
            <el-col :offset="2" :span="20">
                <div ref="myChartChapter" class="chart-score"></div>
            </el-col>
        </el-row>

        <el-row>
            <el-col :offset="2" :span="20">
                <div ref="myChartBank" class="chart-score"></div>
            </el-col>
        </el-row>

        <el-row>
            <el-col :offset="2" :span="20">
                <div ref="myChartC" class="chart-score"></div>
            </el-col>
        </el-row>

        <el-row>
            <el-col :offset="2" :span="20">
                <div ref="myChartIndepropo" class="chart-score"></div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import * as echarts from 'echarts';
    import axios from '../../../api/index'

    export default {
        name: "StudentQuery",

        data() {
            return {
                data: [],
                sId:'',
                chapter1Values: [], //chapter模块
                chapter2Values: [],
                chapter3Values: [],
                chapter4Values: [],
                chapter5Values: [],
                chapter6Values: [],
                chapter7Values: [],
                chapter8Values: [],
                chapter9Values: [],
                chapterKey1: [],
                chapterKey2: [],
                chapterKey3: [],
                chapterKey4: [],
                chapterKey5: [],
                chapterKey6: [],
                chapterKey7: [],
                chapterKey8: [],
                chapterKey9: [],
                maxChapterKey:[],

                //bank模块
                bankChoice10Values: [],
                bankChoice25Values: [],
                bankChoice50Values: [],
                bankGapFilling10Values: [],
                bankGapFilling25Values: [],
                bankGapFilling50Values: [],
                bankJudge10Values: [],
                bankJudge25Values: [],
                bankJudge50Values: [],
                bankProgramme5Values: [],
                bankProgramme10Values: [],
                bankProgramme20Values: [],
                bankKeyChoice10: [],
                bankKeyChoice25: [],
                bankKeyChoice50: [],
                bankKeyGapFilling10: [],
                bankKeyGapFilling25: [],
                bankKeyGapFilling50: [],
                bankKeyJudge10: [],
                bankKeyJudge25: [],
                bankKeyJudge50: [],
                bankKeyProgramme5: [],
                bankKeyProgramme10: [],
                bankKeyProgramme20: [],
                maxBankKey: [],
                //c模块
                cSimpleValues: [],
                cMiddleValues: [],
                cHardValues: [],
                cKeySimple:[],
                cKeyMiddle:[],
                cKeyHard:[],
                maxCKey: [],

                //自主命题模块
                indepropoValues: [],
                indepropoKey: [],
                maxIndepropoKey: [],
                indepropoNames: [],
            }
        },
        methods: {
            getIndepropoChartData() {
                axios({
                    method: 'post',
                    url: '/name_indepropo',
                    data: this.$qs.stringify({id:this.sId})
                }).then(res=>{
                    console.log(res);
                    this.indepropoNames = res.data.data;
                    console.log(this.indepropoNames)
                }).catch(err=>{
                    console.log(err);
                });
                axios({
                    method: 'post',
                    url: '/score_indepropo',
                    data: this.$qs.stringify({id: this.sId})
                }).then(res => {
                    if (res.data.info.code===400){
                        this.$message('自主命题测试暂无成绩记录');
                    }else {
                        this.setIndepropoNValues(res);
                        this.getIndepropoKeyNMaxCount(res);
                        this.drawLineIndepropo();
                    }
                }).catch(err => {
                    console.log(err);
                })
            },
            getCChartData() {
                axios({
                    method: 'post',
                    url: '/score_c',
                    data: this.$qs.stringify({id: this.sId})
                }).then(res => {
                    if (res.data.info.code===400){
                        this.$message('模拟C测试暂无成绩记录');
                    }else {
                        this.setCNValues(res);
                        this.getCKeyNMaxCount(res);
                        this.drawLineC();
                    }


                }).catch(err => {
                    console.log(err);
                })
            },
            getBankChartData() {
                axios({
                    method: 'post',
                    url: '/score_bank',
                    data: this.$qs.stringify({id: this.sId})
                }).then(res => {
                    console.log(res);
                    if (res.data.info.code===400){
                        this.$message('题库练习暂无成绩记录');
                    }
                    this.setBankNValues(res);
                    this.getBankKeyNMaxCount(res);
                    this.drawLineBank();
                }).catch(err => {
                    console.log(err);
                })
            },
            getChapterChartData() {
                axios({
                    method: 'post',
                    url: '/score_chapter',
                    data: this.$qs.stringify({id: this.sId})
                }).then(res => {
                    console.log(res);
                    if (res.data.info.code===400){
                        this.$message('章节测试暂无成绩记录');
                    }
                    this.setChapterNValues(res);
                    this.getChapterKeyNMaxCount(res);
                    this.drawLineChapter();
                }).catch(err => {
                    console.log(err);
                })
            },
            getIndepropoKeyNMaxCount(res){
                let list = [];
                for (let key in res.data.data.scoreIndepropo){
                    list.push(key);
                }
                this.indepropoKey = list;
                list = [];

                this.maxIndepropoKey = list[list.length-1];
                list = [];
            },
            setIndepropoNValues(res){
                let list = [];
                for (let key in res.data.data.scoreIndepropo){
                    list.push(res.data.data.scoreIndepropo[key]);
                }
                this.indepropoValues = list;
                list = [];
            },
            getCKeyNMaxCount(res){
                let list = [];
                for (let key in res.data.data.scoreCSimple){
                    list.push(key);
                }
                this.cKeySimple = list;
                list = [];
                for (let key in res.data.data.scoreCMiddle){
                    list.push(key);
                }
                this.cKeyMiddle = list;
                list = [];
                for (let key in res.data.data.scoreCHard){
                    list.push(key);
                }
                this.cKeyHard = list;
                list = [];

                list = [this.cKeySimple,this.cKeyMiddle,this.cKeyHard];
                let tempList = [];

                for (let i=0;i<list.length-1;i++){      //冒泡排序法求得长度最大的数组
                    for (let j=0;j<list.length-i-1;j++)
                        if (list[j].length<list[j+1].length){
                            tempList = list[j+1];
                            list[j+1]=list[j];
                            list[j]=tempList;
                        }
                }
                tempList = [];
                this.maxCKey = list[0];
                list = [];
            },
            setCNValues(res){
                let list = [];
                for (let key in res.data.data.scoreCSimple){
                    list.push(res.data.data.scoreCSimple[key]);
                }
                this.cSimpleValues = list;
                list = [];
                for (let key in res.data.data.scoreCMiddle){
                    list.push(res.data.data.scoreCMiddle[key]);
                }
                this.cMiddleValues = list;
                list = [];
                for (let key in res.data.data.scoreCHard){
                    list.push(res.data.data.scoreCHard[key]);
                }
                this.cHardValues = list;
                list = [];
            },
            getBankKeyNMaxCount(res){
                let list = [];

                for (let key in res.data.data.scoreBankChoice10){
                    list.push(key);
                }
                this.bankKeyChoice10 = list;
                list = [];
                for (let key in res.data.data.scoreBankChoice25){
                    list.push(key);
                }
                this.bankKeyChoice25 = list;
                list = [];
                for (let key in res.data.data.scoreBankChoice50){
                    list.push(key);
                }
                this.bankKeyChoice50 = list;
                list = [];

                for (let key in res.data.data.scoreBankGapFilling10){
                    list.push(key);
                }
                this.bankKeyGapFilling10 = list;
                list = [];
                for (let key in res.data.data.scoreBankGapFilling25){
                    list.push(key);
                }
                this.bankKeyGapFilling25 = list;
                list = [];
                for (let key in res.data.data.scoreBankGapFilling50){
                    list.push(key);
                }
                this.bankKeyGapFilling50 = list;
                list = [];

                for (let key in res.data.data.scoreBankJudge10){
                    list.push(key);
                }
                this.bankKeyJudge10 = list;
                list = [];
                for (let key in res.data.data.scoreBankJudge25){
                    list.push(key);
                }
                this.bankKeyJudge25 = list;
                list = [];
                for (let key in res.data.data.scoreBankJudge50){
                    list.push(key);
                }
                this.bankKeyJudge50 = list;
                list = [];

                for (let key in res.data.data.scoreBankProgramme5){
                    list.push(key);
                }
                this.bankKeyProgramme5 = list;
                list = [];
                for (let key in res.data.data.scoreBankProgramme10){
                    list.push(key);
                }
                this.bankKeyProgramme10 = list;
                list = [];
                for (let key in res.data.data.scoreBankProgramme20){
                    list.push(key);
                }
                this.bankKeyProgramme20 = list;
                list = [];

                list = [this.bankKeyChoice10,this.bankKeyChoice25,this.bankKeyChoice50,
                    this.bankKeyGapFilling10,this.bankKeyGapFilling25,this.bankKeyGapFilling50,
                    this.bankKeyJudge10,this.bankKeyJudge25,this.bankKeyJudge50,
                    this.bankKeyProgramme5,this.bankKeyProgramme10,this.bankKeyProgramme20];
                let tempList = [];

                for (let i=0;i<list.length-1;i++){      //冒泡排序法求得长度最大的数组
                    for (let j=0;j<list.length-i-1;j++)
                        if (list[j].length<list[j+1].length){
                            tempList = list[j+1];
                            list[j+1]=list[j];
                            list[j]=tempList;
                        }
                }
                tempList = [];
                this.maxBankKey = list[0];
                list = [];
            },
            setBankNValues(res){
                let list = [];

                for (let key in res.data.data.scoreBankChoice10){
                    list.push(res.data.data.scoreBankChoice10[key]);
                }
                this.bankChoice10Values = list;
                list = [];
                for (let key in res.data.data.scoreBankChoice25){
                    list.push(res.data.data.scoreBankChoice25[key]);
                }
                this.bankChoice25Values = list;
                list = [];
                for (let key in res.data.data.scoreBankChoice50){
                    list.push(res.data.data.scoreBankChoice50[key]);
                }
                this.bankChoice50Values = list;
                list = [];

                for (let key in res.data.data.scoreBankGapFilling10){
                    list.push(res.data.data.scoreBankGapFilling10[key]);
                }
                this.bankGapFilling10Values = list;
                list = [];
                for (let key in res.data.data.scoreBankGapFilling25){
                    list.push(res.data.data.scoreBankGapFilling25[key]);
                }
                this.bankGapFilling25Values = list;
                list = [];
                for (let key in res.data.data.scoreBankGapFilling50){
                    list.push(res.data.data.scoreBankGapFilling50[key]);
                }
                this.bankGapFilling150Values = list;
                list = [];

                for (let key in res.data.data.scoreBankJudge10){
                    list.push(res.data.data.scoreBankJudge10[key]);
                }
                this.bankJudge10Values = list;
                list = [];
                for (let key in res.data.data.scoreBankJudge25){
                    list.push(res.data.data.scoreBankJudge25[key]);
                }
                this.bankJudge25Values = list;
                list = [];
                for (let key in res.data.data.scoreBankJudge50){
                    list.push(res.data.data.scoreBankJudge50[key]);
                }
                this.bankJudge50Values = list;
                list = [];

                for (let key in res.data.data.scoreBankProgramme5){
                    list.push(res.data.data.scoreBankProgramme5[key]);
                }
                for (let key in res.data.data.scoreBankProgramme10){
                    list.push(res.data.data.scoreBankProgramme10[key]);
                }
                for (let key in res.data.data.scoreBankProgramme20){
                    list.push(res.data.data.scoreBankProgramme20[key]);
                }
                this.bankProgramme20Values = list;
                list = [];
            },
            getChapterKeyNMaxCount(res){       //获取每个章节的考试次数，拿到最大的次数作为x轴
                let list = [];
                for (let key in res.data.data.scoreChapter1){
                    list.push(key);
                }
                this.chapterKey1 = list;
                list = [];
                for (let key in res.data.data.scoreChapter2){
                    list.push(key);
                }
                this.chapterKey2 = list;
                list = [];
                for (let key in res.data.data.scoreChapter3){
                    list.push(key);
                }
                this.chapterKey3 = list;
                list = [];
                for (let key in res.data.data.scoreChapter4){
                    list.push(key);
                }
                this.chapterKey4 = list;
                list = [];
                for (let key in res.data.data.scoreChapter5){
                    list.push(key);
                }
                this.chapterKey5 = list;
                list = [];
                for (let key in res.data.data.scoreChapter6){
                    list.push(key);
                }
                this.chapterKey6 = list;
                list = [];
                for (let key in res.data.data.scoreChapter7){
                    list.push(key);
                }
                this.chapterKey7 = list;
                list = [];
                for (let key in res.data.data.scoreChapter8){
                    list.push(key);
                }
                this.chapterKey8 = list;
                list = [];
                for (let key in res.data.data.scoreChapter9){
                    list.push(key);
                }
                this.chapterKey9 = list;
                list = [];

                list = [this.chapterKey1,this.chapterKey2,this.chapterKey3,this.chapterKey4
                    ,this.chapterKey5,this.chapterKey6,this.chapterKey7,this.chapterKey8
                    ,this.chapterKey9];
                let tempList = [];

                for (let i=0;i<list.length-1;i++){      //冒泡排序法求得长度最大的数组
                    for (let j=0;j<list.length-i-1;j++)
                        if (list[j].length<list[j+1].length){
                            tempList = list[j+1];
                            list[j+1]=list[j];
                            list[j]=tempList;
                        }
                }
                tempList = [];
                this.maxChapterKey = list[0];
                list = [];
            },
            setChapterNValues(res){     //设置每个章节的值
                let list = [];
                for (let key in res.data.data.scoreChapter1){
                    list.push(res.data.data.scoreChapter1[key]);
                }
                this.chapter1Values = list;
                list = [];
                for (let key in res.data.data.scoreChapter2){
                    list.push(res.data.data.scoreChapter2[key]);
                }
                this.chapter2Values = list;
                list = [];
                for (let key in res.data.data.scoreChapter3){
                    list.push(res.data.data.scoreChapter3[key]);
                }
                this.chapter3Values = list;
                list = [];
                for (let key in res.data.data.scoreChapter4){
                    list.push(res.data.data.scoreChapter4[key]);
                }
                this.chapter4Values = list;
                list = [];
                for (let key in res.data.data.scoreChapter5){
                    list.push(res.data.data.scoreChapter5[key]);
                }
                this.chapter5Values = list;
                list = [];
                for (let key in res.data.data.scoreChapter6){
                    list.push(res.data.data.scoreChapter6[key]);
                }
                this.chapter6Values = list;
                list = [];
                for (let key in res.data.data.scoreChapter7){
                    list.push(res.data.data.scoreChapter7[key]);
                }
                this.chapter7Values = list;
                list = [];
                for (let key in res.data.data.scoreChapter8){
                    list.push(res.data.data.scoreChapter8[key]);
                }
                this.chapter8Values = list;
                list = [];
                for (let key in res.data.data.scoreChapter9){
                    list.push(res.data.data.scoreChapter9[key]);
                }
                this.chapter9Values = list;
                list = [];
            },
            drawLineIndepropo() {
                let myChartIndepropo = echarts.init(this.$refs.myChartIndepropo);
                let optionIndepropo;

                let indepropoValues = this.indepropoValues;
                let indepropoNames = this.indepropoNames;
                setTimeout(function (){
                    optionIndepropo = {
                        legend: {},
                        tooltip: {
                            trigger: 'axis',
                            showContent: true
                        },
                        xAxis: {type: 'category',data: indepropoNames},
                        yAxis: {gridIndex: 0},
                        grid: {top: '10%'},
                        series: [
                            {
                                name: '自主命题',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: indepropoValues
                            },
                        ],
                        title: {
                            text: '自主命题成绩',
                            top: '0',
                            left: '10',
                            textStyle: {
                                fontSize: 20,
                                align: 'center',


                            },
                        }
                    };
                    myChartIndepropo.setOption(optionIndepropo);
                });
            },
            drawLineC() {
                let myChartC = echarts.init(this.$refs.myChartC);
                let optionC;

                let cSimpleValues = this.cSimpleValues;
                let cMiddleValues = this.cMiddleValues;
                let cHardValues = this.cHardValues;
                let maxCKey = this.maxCKey;
                setTimeout(function (){
                    optionC = {
                        legend: {},
                        tooltip: {
                            trigger: 'axis',
                            showContent: true
                        },
                        xAxis: {type: 'category',data: maxCKey},
                        yAxis: {gridIndex: 0},
                        grid: {top: '10%'},
                        series: [
                            {
                                name: '模拟C简单',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: cSimpleValues
                            },
                            {
                                name: '模拟C中等',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: cMiddleValues
                            },
                            {
                                name: '模拟C困难',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: cHardValues
                            },
                        ],
                        title: {
                            text: '模拟C成绩',
                            top: '0',
                            left: '10',
                            textStyle: {
                                fontSize: 20,
                                align: 'center',


                            },
                        }
                    };
                    myChartC.setOption(optionC);
                });
            },
            drawLineBank() {
                let myChartBank = echarts.init(this.$refs.myChartBank);
                let optionBank;

                let bankChoice10Values = this.bankChoice10Values;
                let bankChoice25Values = this.bankChoice25Values;
                let bankChoice50Values = this.bankChoice50Values;
                let bankGapFilling10Values = this.bankGapFilling10Values;
                let bankGapFilling25Values = this.bankGapFilling25Values;
                let bankGapFilling50Values = this.bankGapFilling50Values;
                let bankJudge10Values = this.bankJudge10Values;
                let bankJudge25Values = this.bankJudge25Values;
                let bankJudge50Values = this.bankJudge50Values;
                let bankProgramme5Values = this.bankProgramme5Values;
                let bankProgramme10Values = this.bankProgramme10Values;
                let bankProgramme20Values = this.bankProgramme20Values;
                let maxBankKey = this.maxBankKey;

                setTimeout(function (){
                    optionBank = {
                        legend: {},
                        tooltip: {
                            trigger: 'axis',
                            showContent: true
                        },
                        xAxis: {type: 'category',data: maxBankKey},
                        yAxis: {gridIndex: 0},
                        grid: {top: '10%'},
                        series: [
                            {
                                name: '选择-10',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankChoice10Values
                            },
                            {
                                name: '选择-25',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankChoice25Values
                            },
                            {
                                name: '选择-50',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankChoice50Values
                            },
                            {
                                name: '填空-10',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankGapFilling25Values
                            },
                            {
                                name: '填空-25',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankGapFilling50Values
                            },
                            {
                                name: '判断-10',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankJudge10Values
                            },
                            {
                                name: '判断-25',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankJudge25Values
                            },
                            {
                                name: '判断-50',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankJudge50Values
                            },
                            {
                                name: '程序填空-5',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankProgramme5Values
                            },
                            {
                                name: '程序填空-10',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankProgramme10Values
                            },
                            {
                                name: '程序填空-20',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: bankProgramme20Values
                            },
                        ],
                        title: {
                            text: '题库成绩',
                            top: '0',
                            left: '0',
                            textStyle: {
                                fontSize: 20,
                                align: 'center',


                            },
                        }
                    };
                    myChartBank.setOption(optionBank);
                });
            },
            drawLineChapter() {
                let myChartChapter = echarts.init(this.$refs.myChartChapter);
                let optionChapter;

                let chapter1Values = this.chapter1Values;
                let chapter2Values = this.chapter2Values;
                let chapter3Values = this.chapter3Values;
                let chapter4Values = this.chapter4Values;
                let chapter5Values = this.chapter5Values;
                let chapter6Values = this.chapter6Values;
                let chapter7Values = this.chapter7Values;
                let chapter8Values = this.chapter8Values;
                let chapter9Values = this.chapter9Values;
                let maxChapterKey = this.maxChapterKey;

                setTimeout(function (){
                    optionChapter = {
                        legend: {},
                        tooltip: {
                            trigger: 'axis',
                            showContent: true
                        },
                        /* dataset:{
                             dimensions: ['product','choice','gapfilling','judge','programme'],
                         },*/

                        xAxis: {type: 'category',data:maxChapterKey},
                        yAxis: {gridIndex: 0},
                        grid: {top: '10%'},
                        series: [
                            {
                                name: '章节1测试',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: chapter1Values
                            },
                            {
                                name: '章节2测试',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: chapter2Values
                            },
                            {
                                name: '章节3测试',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: chapter3Values
                            },
                            {
                                name: '章节4测试',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: chapter4Values
                            },
                            {
                                name: '章节5测试',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: chapter5Values
                            },
                            {
                                name: '章节6测试',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: chapter6Values
                            },
                            {
                                name: '章节7测试',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: chapter7Values
                            },
                            {
                                name: '章节8测试',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: chapter8Values
                            },
                            {
                                name: '章节9测试',
                                type: 'line',
                                smooth: false,
                                seriesLayoutBy: 'row',
                                emphasis: {focus: 'series'},
                                data: chapter9Values
                            },
                        ],
                        title: {
                            text: '章节成绩',
                            top: '0',
                            left: '10',
                            textStyle: {
                                fontSize: 20,
                                align: 'center',
                            },
                        }
                    };
                    myChartChapter.setOption(optionChapter);
                });
            },
        },
        mounted() {
            this.sId = this.$route.params.data.sId;
            this.getChapterChartData();
            this.getBankChartData();
            this.getCChartData();
            this.getIndepropoChartData();

        },
    }
</script>

<style scoped>
    .chart-score {
        margin: 100px auto;
        height: 400px;
    }

</style>