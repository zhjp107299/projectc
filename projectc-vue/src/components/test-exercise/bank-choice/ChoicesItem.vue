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
    //引入全局变量，因为这里是通过组件引入的方式，所以变量私有了
    import global_choiceValues from '../../../global/Global'
    export default {
        name: "Chapter1Item",
        props: ['item','index'],
        data(){
            return {
                choiceValues:global_choiceValues.choiceValues,
                A:'A',
                B:'B',
                C:'C',
                D:'D',
                radioList: [],
            }
        },
        methods: {
            handleChange(item,seq,index){
                let choice = {seq:seq,answer:this.radioList[index]};
                this.choiceValues.push(choice);

                //去重
                for (let i=0;i<this.choiceValues.length;i++){
                    for (let j=i+1;j<this.choiceValues.length;j++){
                        if (this.choiceValues[i].seq===this.choiceValues[j].seq){
                            this.choiceValues.splice(i,1);
                            j--;
                        }
                    }
                }
                console.log(this.choiceValues);
            }
        },
        mounted() {

        }
    }
</script>

<style type="scss" scoped>
    .radios-box {
        border: 1px solid white;
        background-color: white;
        width: 1100px;
        margin: 40px auto;
        margin-right: 600px;
        border-radius: 10px;
        height: fit-content;

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