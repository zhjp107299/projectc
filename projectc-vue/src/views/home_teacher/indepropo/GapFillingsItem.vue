<template>
    <div class="gap-fillings-box">
        <div style="margin-top: 20px;margin-left: 40px;text-align: left;white-space: pre-wrap;line-height: 40px;">{{index+1}}.{{item.topic}}</div>
       <div style="margin: -20px 0;margin-left: 20px;"><el-input @blur="handleBlur(item,index,index)" style="width: 300px;" v-model="inputList[index]" placeholder="请输入你的答案" suffix-icon="el-icon-edit"></el-input></div>
    </div>
</template>

<script>
    export default {
        name: "GapFillingsItem",
        props: ['item','index'],
        data(){
            return {
                inputList:[],
            }
        },
    }
</script>

<style scoped>
    .gap-fillings-box {
        border: 1px solid white;
        background-color: white;
        border-radius: 10px;
        height: fit-content;
        width: 1000px;
        margin: 40px auto;
        margin-right: 600px;
        text-align: center;
    }
</style>