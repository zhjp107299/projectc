<template>
    <div>
        <div class="intro">
            <el-collapse @change="handleChange" v-model="activeNames">
                <span style="font-size: 20px;margin-top:20px;">系统功能介绍</span>
                <el-collapse-item style="margin-top: -20px" class="collapse-item" name="1">
                    <template slot="title">
                        <p class="title">功能1：查看所有教师</p>
                    </template>
                    <div >
                        <p class="function-comment">查看所有使用该系统的老师，并对其进行权限操作。延期功能可在账号到期时间上增加相应的时间。</p>
                        <p class="function-comment">教师权限分为0、1、2、3：>0级权限只可浏览界面；</p>
                        <p class="function-comment">>1级权限可以使用查看学生功能；</p>
                        <p class="function-comment">>2级权限可以增加使用题库导入功能；</p>
                        <p class="function-comment">>3级权限可以增加使用自主命题功能。</p>

                    </div>
                </el-collapse-item>
                <el-collapse-item class="collapse-item" name="2">
                    <template slot="title">
                        <p class="title">功能2：查看所有学生</p>
                    </template>
                    <p class="function-comment">查看所有使用该系统的学生，并对其进行权限操作。延期功能可在账号到期时间上增加相应的时间。</p>
                    <p class="function-comment">学生权限分为0、1、2、3、4: >0级权限只可浏览界面；</p>
                    <p class="function-comment">>1级权限可以使用章节测试功能；</p>
                    <p class="function-comment">>2级权限可以增加题库练习功能；</p>
                    <p class="function-comment">>3级权限可以增加模拟C语言测试功能；</p>
                    <p class="function-comment">>4级权限可以增加自主命题测试功能。</p>
                </el-collapse-item>
                <el-collapse-item class="collapse-item" name="6">
                    <template slot="title">
                        <p class="title">功能3：个人信息修改</p>
                    </template>
                    <p class="function-comment">可修改个人的基本信息</p>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                activeNames: ['1','2','3','4','5','6']
            };
        },
        methods: {

            handleChange(val) {
                console.log(val);
            }
        }
    }
</script>

<style type="scss" scoped>
    .title {
        //font-size: 20px;
    }
    .function-comment {
        font-size: 14px;
        line-height: 30px;
        text-align: left;
        letter-spacing: 2px;
    }
    .intro {
        border: 1px solid #DCDFE6;
        background-color: white;
        opacity: 0.95;
        border-radius: 5px;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        box-shadow: 4px 4px 20px rgba(84, 92, 104, 0.57);
        height: 1000px;
        width: 1200px;
        margin: 40px auto;

    }

    .collapse-item {
        margin: 40px;
        //margin-top: -40px;
    }
</style>