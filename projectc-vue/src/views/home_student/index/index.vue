<template>
    <div>
        <div class="intro">
            <el-collapse @change="handleChange" v-model="activeNames">
                <span style="font-size: 20px;margin-top:20px;">系统功能介绍</span>
                <el-collapse-item style="margin-top: -20px" class="collapse-item" name="1">
                    <template slot="title">
                        <p class="title">功能1：章节测试</p>
                    </template>
                    <div >
                        <p class="function-comment">第一章：C语言概述&emsp;&emsp;第二章：C语言开发集成环境、表达式与语句&emsp;&emsp;第三章：顺序和分支结构
                            &emsp;&emsp;第四章：循环结构&emsp;&emsp;第五章节：函数</p>
                        <p class="function-comment">第六章：数组&emsp;&emsp;第七章：指针&emsp;&emsp;第八章：结构体&emsp;&emsp;第九章：文件</p>
                    </div>
                </el-collapse-item>
                <el-collapse-item class="collapse-item" name="2">
                    <template slot="title">
                        <p class="title">功能2：题库练习</p>
                    </template>
                    <p class="function-comment">选择题练习、填空题练习、判断题练习：10/25/50题&emsp;&emsp;程序填空题练习：5/10/25题</p>
                </el-collapse-item>
                <el-collapse-item class="collapse-item" name="3">
                    <template slot="title">
                        <p class="title">功能3：模拟全国计算机二级C语言测试</p>
                    </template>
                    <p class="function-comment">模拟C难度分为：简单、中等、困难，题型分布：选择题50题，填空题20题，程序填空题4题</p>
                </el-collapse-item>
                <el-collapse-item class="collapse-item" name="4">
                    <template slot="title">
                        <p class="title">功能4：自主命题测试</p>
                    </template>
                    <p class="function-comment">所属教师下发自主命题测试</p>
                </el-collapse-item>
                <el-collapse-item class="collapse-item" name="5">
                    <template slot="title">
                        <p class="title">功能5：查看成绩</p>
                    </template>
                    <p class="function-comment">可查看各类测试及练习的历史成绩</p>
                </el-collapse-item>
                <el-collapse-item class="collapse-item" name="6">
                    <template slot="title">
                        <p class="title">功能6：个人信息修改</p>
                    </template>
                    <p class="function-comment">可修改个人的基本信息</p>
                </el-collapse-item>
                <el-collapse-item class="collapse-item" name="7">
                    <template slot="title">
                        <p class="title">权限说明</p>
                    </template>
                    <p class="function-comment">权限分为0、1、2、3、4: >0级权限只可浏览界面；<p>
                    <p class="function-comment">>1级权限可以使用章节测试功能；</p>
                    <p class="function-comment">>2级权限可以增加题库练习功能；</p>
                    <p class="function-comment">>3级权限可以增加模拟C语言测试功能；</p>
                    <p class="function-comment">>4级权限可以增加自主命题测试功能。</p>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                activeNames: ['1','2','3','4','5','6','7']
            }
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
        height: 1500px;
        width: 1200px;
        margin: 40px auto;

    }

    .collapse-item {
        margin: 40px;
        //margin-top: -40px;
    }
</style>