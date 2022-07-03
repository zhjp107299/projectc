<template>
    <div class="radios-box">
        <div style="margin-top: 20px;margin-left: 40px;text-align: left;white-space: pre-wrap;line-height: 40px;">{{index+1}}.{{item.topic}}</div>
        <el-radio-group v-model="radioList[index]" @change="handleChange(item,index,index)">
        <div class="radio-style" ><el-radio border :label="A">A.</el-radio>&emsp;{{item.choiceA}}</div>
        <div class="radio-style"><el-radio border :label="B">B.</el-radio>&emsp;{{item.choiceB}}</div>
        <div class="radio-style"><el-radio border :label="C">C.</el-radio>&emsp;{{item.choiceC}}</div>
        <div class="radio-style"><el-radio border :label="D">D.</el-radio>&emsp;{{item.choiceD}}</div>
        </el-radio-group>
    </div>
</template>

<script>
    export default {
        name: "Chapter1Item",
        props: ['item','index'],
        data(){
            return {
                A:'A',
                B:'B',
                C:'C',
                D:'D',
                radioList: [],
            }
        },
    }
</script>

<style type="scss" scoped>
    .radios-box {
        border: 1px solid white;
        background-color: white;
        width: 1000px;
        margin: 40px auto;
        margin-right: 600px;
        border-radius: 10px;
        height: fit-content;
        text-align: left;

    }
    .radio-style {
        margin-bottom: 20px;
        text-align: left;
    }
    .el-radio-group {
        margin: 10px 0;
        margin-left: 40px;

    }
</style>