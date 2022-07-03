<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>题库练习说明</span>
            </div>
            <div class="text item">
                <span>说明：本练习为{{tag}}，无时间限制</span>
            </div>
            <div class="text item">
                <span>题型：选择题/填空题/判断题：10题/25题/50题/   编程题：5题/10题/20题</span>
            </div>
            <div class="text item">
                <span style="">满分：100分</span>
                <span style="margin-left: 200px;">合格：60分</span>
            </div>
            <div class="text item">
                <span>备注：<el-tag size="middle">{{tag}}</el-tag></span>
            </div>
            <div class="text item">
                <span>测试次数：{{seq}}/不限次数</span>
            </div>
            <el-button @click="testStarts" type="primary" plain style="width: 160px;height: 50px;margin-top: 100px">开始练习</el-button>
        </el-card>

    </div>
</template>

<script>
    import axios from '../api/index'
    export default {
        name: "IntermediateBank",
        data(){
          return {
              id:'',
              type: '',
              path: '',
              count: '',
              tag: '',
              seq: '',
          }
        },
        methods: {
            getSeq(){
                //获取练习题数
                this.count = parseInt(this.tag.substring(4));
                //获取类型
                this.type = this.tag.substring(0,3);
              axios({
                  method: 'post',
                  url: '/seq_bank',
                  data: this.$qs.stringify({id:this.id,type:this.type,count:this.count})
              }).then(res=>{
                  console.log(res);
                  if (res.data.info.code===200){
                      this.seq = res.data.data;
                  }
              }).catch(err=>{
                  console.log(err);
              })
            },
            testStarts(){
                this.$alert('练习开始', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {
                        this.$message({
                            type: 'info',
                            message: `无时间限制`
                        });
                    }
                });

                //判断类型
                let sType = '';
                switch (this.type) {
                    case '选择题':
                        sType = 'choice';
                        break;
                    case '判断题':
                        sType = 'judge';
                        break;
                    case '编程题':
                        sType = 'programme';
                        break;
                    case '填空题':
                        sType = 'gapfilling';
                        break;
                }
                //拼接路径字符串
                this.$global.passSwitch = true;
                console.log("改变为true");
                let path = '/hs/test_exercise' + '/' + 'bank_' + sType + '/' + sType + '_' + this.count;
                this.$router.push({
                    path: path
                });
            },
    },
        mounted() {
            this.id = JSON.parse(localStorage.getItem('loginUser')).sId;
            this.tag = this.$route.query.tag;
            this.$global.testTag = this.tag;
            this.getSeq();

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