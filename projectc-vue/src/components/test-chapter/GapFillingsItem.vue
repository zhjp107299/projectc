<template>
    <div class="gap-fillings-box">
        <div style="margin-top: 20px;margin-left: 40px;text-align: left;white-space: pre-wrap;line-height: 40px;">{{index+1}}.{{item.topic}}</div>
       <div style="margin: -20px 0;margin-left: 20px;"><el-input @blur="handleBlur(item,index,index)" style="width: 300px;" v-model="inputList[index]" placeholder="请输入你的答案" suffix-icon="el-icon-edit"></el-input></div>
    </div>
</template>

<script>
    import global_gapfillingValues from '../../global/Global'
    export default {
        name: "GapFillingsItem",
        props: ['item','index'],
        data(){
            return {
                gapfillingVlues: global_gapfillingValues.gapfillingValues,
                inputList:[],
            }
        },
        methods: {
            handleBlur(item,seq,index){
                let gapfilling = {seq:seq,answer: this.inputList[index]};
                this.gapfillingVlues.push(gapfilling);

                //去重
                for (let i=0;i<this.gapfillingVlues.length;i++){
                    for (let j=i+1;j<this.gapfillingVlues.length;j++){
                        if (this.gapfillingVlues[i].seq===this.gapfillingVlues[j].seq){
                            this.gapfillingVlues.splice(i,1);
                            j--;
                        }
                    }
                }
                console.log(this.gapfillingVlues);
            }
        }
    }
</script>

<style scoped>
    .gap-fillings-box {
        border: 1px solid white;
        background-color: white;
        border-radius: 10px;
        height: fit-content;
        width: 1100px;
        margin: 40px auto;
        margin-right: 600px;
        text-align: center;
    }
</style>