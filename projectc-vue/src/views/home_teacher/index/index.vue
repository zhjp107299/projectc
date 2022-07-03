<template>
    <div>
        <div>
            <div class="intro">
                <el-collapse @change="handleChange" v-model="activeNames">
                    <span style="font-size: 20px;margin-top:20px;">系统功能介绍</span>
                    <el-collapse-item style="margin-top: -20px" class="collapse-item" name="1">
                        <template slot="title">
                            <p class="title">功能1：自主命题</p>
                        </template>
                        <div >
                            <p class="function-comment">通过Excel导入自主命题试卷，对学生进行测试。
                                试卷题型为：选择题12、填空题8、判断题12、程序填空题3，
                                设置开启和关闭两种状态，开启状态可让学生进行答题，关闭状态学生无法答题。</p>
                        </div>
                    </el-collapse-item>
                    <el-collapse-item class="collapse-item" name="2">
                        <template slot="title">
                            <p class="title">功能2：导入题库</p>
                        </template>
                        <p class="function-comment">批量导入选择题、填空题、判断题、程序填空题。</p>
                    </el-collapse-item>
                    <el-collapse-item class="collapse-item" name="3">
                        <template slot="title">
                            <p class="title">功能3：查看学生</p>
                        </template>
                        <p class="function-comment">查看学生信息：查看学生测试情况、编辑学生信息、删除学生等。</p>
                    </el-collapse-item>
                    <el-collapse-item class="collapse-item" name="6">
                        <template slot="title">
                            <p class="title">功能4：个人信息修改</p>
                        </template>
                        <p class="function-comment">可修改个人的基本信息。</p>
                    </el-collapse-item>
                    <el-collapse-item class="collapse-item" name="7">
                        <template slot="title">
                            <p class="title">权限说明</p>
                        </template>
                        <p class="function-comment">权限分为0、1、2、3：>0级权限只可浏览界面；</p>
                        <p class="function-comment">>1级权限可以使用查看学生功能；</p>
                        <p class="function-comment">>2级权限可以增加使用题库导入功能；</p>
                        <p class="function-comment">>3级权限可以增加使用自主命题功能。</p>
                    </el-collapse-item>
                </el-collapse>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                activeNames: ['1','2','3','4','5','6','7']
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
        height: 1200px;
        width: 1200px;
        margin: 40px auto;

    }

    .collapse-item {
        margin: 40px;
        //margin-top: -40px;
    }
</style>