<template>
    <div class="programmes-box">
        <div style="margin-left: 40px;margin-top: 20px;text-align: left;line-height: 26px;white-space: pre-wrap;letter-spacing: 1px;">{{index+1}}.{{item.topic}}</div>
            <div class="programme-style" style="margin-top: 20px;"> <el-input @blur="handleBlur(item,index,index)" style="width: 400px;" v-model="inputList1[index]" placeholder="第一空" suffix-icon="el-icon-edit"></el-input> </div>
            <div class="programme-style"> <el-input style="width: 400px;" @blur="handleBlur(item,index,index)" v-model="inputList2[index]" placeholder="第二空" suffix-icon="el-icon-edit"></el-input> </div>
            <div class="programme-style"> <el-input style="width: 400px;" @blur="handleBlur(item,index,index)" v-model="inputList3[index]" placeholder="第三空" suffix-icon="el-icon-edit"></el-input> </div>

    </div>
</template>

<script>
    import global_programmeValues1 from '../../global/Global'

    export default {
        name: "ProgrammesItem",
        props: ['item','index'],
        data() {
            return {

                programmeValues: global_programmeValues1.programmeValues,

                inputList1: [],
                inputList2: [],
                inputList3: [],
            }
        },
        methods: {
            handleBlur(item,seq,index){
                let programme = {seq: seq,answer1: this.inputList1[index]
                    ,answer2: this.inputList2[index],answer3: this.inputList3[index]};
                this.programmeValues.push(programme);

                //去重
                for (let i=0;i<this.programmeValues.length;i++){
                    for (let j=i+1;j<this.programmeValues.length;j++){
                        if (this.programmeValues[i].seq===this.programmeValues[j].seq){
                            this.programmeValues.splice(i,1);
                            j--;
                        }
                    }
                }
                console.log(this.programmeValues);
            },
        }
    }
</script>

<style scoped>
    .programme-style {
        margin-top: -80px;
    }
    .programmes-box {
        border: 1px solid white;
        background-color: white;
        width: 1100px;
        margin: 40px auto;
        margin-right: 600px;
        border-radius: 10px;
        height: fit-content;
        text-align: center;
    }
</style>