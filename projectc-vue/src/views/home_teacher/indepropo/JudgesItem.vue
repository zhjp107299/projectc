<template>
    <div class="judges-box">
        <div style="margin-top: 20px;margin-left: 40px;text-align: left;white-space: pre-wrap;line-height: 40px;">{{index+1}}.{{item.topic}}</div>
        <el-radio-group v-model="radioList[index]" @change="handleChange(item,index,index)">
            <div class="judge-style"><el-radio border :label="this.true"></el-radio></div>
            <div class="judge-style"><el-radio border :label="this.false"></el-radio></div>
        </el-radio-group>
    </div>
</template>

<script>
    export default {
        name: "JudgeItem",
        props: ['item','index'],
        data() {
            return {
                true: 'True',
                false: 'False',
                radioList: [],
            }
        },
    }
</script>

<style type="scss" scoped>
    .el-radio-group {
        margin: 20px 0;


    }
    .judges-box {
        border: 1px solid white;
        background-color: white;
        width: 1000px;
        margin: 40px auto;
        margin-right: 600px;
        border-radius: 10px;
        height: fit-content;
        text-align: left;
    }
    .judge-style {
        margin-bottom: 20px;
        text-align: left;
        margin-left: 40px;
    }
</style>