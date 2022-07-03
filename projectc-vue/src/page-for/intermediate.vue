<template>
    <div>


        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>章节测试说明</span>
            </div>
            <div class="text item">
                <span>说明：本测试为{{tag}}，时间为90分钟</span>
            </div>
            <div class="text item">
                <span>题型：选择题12题/填空题8题/判断题12题/编程题3题</span>
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
            <el-button @click="testStarts" type="primary" plain style="width: 160px;height: 50px;margin-top: 100px">开始测试</el-button>
        </el-card>


    </div>
</template>

<script>
    import axios from '../api/index'
    export default {
        name: "intermediate",
        data(){
          return {
              id:'',
              path: '',
              index: '',
              tag: '',
              seq: '',
          }
        },
        methods: {
            getSeq(){
                //获取章节数
                this.index = parseInt(this.tag.charAt(2));
              axios({
                  method: 'post',
                  url: '/seq_chapter',
                  data: this.$qs.stringify({id:this.id,chapterIndex:this.index})
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
                console.log(this.$global.passSwitch);
                this.$global.passSwitch = true;
                console.log("改变为true");
                console.log(this.$global.passSwitch);
                //拼接路径字符串
                let path = '/hs/test_chapter/chapter'+this.index;
                this.$router.push({
                    path: path,
                });
            },
    },
        mounted() {
            this.id = JSON.parse(localStorage.getItem('loginUser')).sId;
            this.tag = this.$route.query.tag;
            this.getSeq();
            this.$global.testTag = this.tag;
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