<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>模拟C测试说明</span>
            </div>
            <div class="text item">
                <span>说明：本测试为{{tag}}，时间120分钟</span>
            </div>
            <div class="text item">
                <span>题型：选择题：50题、 填空题：20题、 程序填空题：4题</span>
            </div>
            <div class="text item">
                <span style="">满分：160分</span>
                <span style="margin-left: 200px;">合格：96分</span>
            </div>
            <div class="text item">
                <span>备注：<el-tag size="middle">{{tag}}</el-tag></span>
            </div>
            <div class="text item">
                <span>测试次数：{{seq}}/不限次数</span>
            </div>
            <el-button @click="testStarts" type="primary" plain style="width: 160px;height: 50px;margin-top: 100px">开始测试</el-button>
        </el-card>

<!--
        <el-descriptions border="true" title="模拟C测试说明：" column="2" style="margin:-100px auto;width:900px;margin-top: -200px;">

            <el-descriptions-item label="满分">100分</el-descriptions-item>
            <el-descriptions-item label="合格">60分</el-descriptions-item>
            <el-descriptions-item label="说明">本测试为{{tag}}，时间120分钟</el-descriptions-item>
            <el-descriptions-item label="题型">选择题：50题、    填空题：20题、   程序填空题：4题</el-descriptions-item>
            <el-descriptions-item label="备注">
                <el-tag size="small">{{tag}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="测试次数">{{seq}}/不限次数</el-descriptions-item>
        </el-descriptions>
        <el-button @click="testStarts" type="primary" plain style="width: 160px;height: 50px;margin-top: 180px">开始测试</el-button>-->

    </div>
</template>

<script>
    import axios from '../api/index'
    export default {
        name: "IntermediateC",
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
                //获取类型
                this.type = this.tag.substring(5);
              axios({
                  method: 'post',
                  url: '/seq_c',
                  data: this.$qs.stringify({id:this.id,type:this.type})
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
                this.$alert('测试开始', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {
                        this.$message({
                            type: 'info',
                            message: `计时开始，请注意时间`
                        });
                    }
                });

                //判断类型
                let sType = '';
                switch (this.type) {
                    case '简单':
                        sType = 'simple';
                        break;
                    case '中等':
                        sType = 'middle';
                        break;
                    case '困难':
                        sType = 'hard';
                        break;
                }
                //拼接路径字符串
                this.$global.passSwitch = true;
                console.log("改变为true");
                let path = '/hs/test_c' + '/' + 'c_' + sType;
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