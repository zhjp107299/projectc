<template>
    <div style="height: 100%">

        <el-container style="height: 100%">
            <!--左侧导航栏-->
            <NavMenu/>
            <!--右側主体-->
            <el-container>
                <!--头部-->
                <el-header>
                    <Breadcrumb/>

                    <el-button class="exit" type="primary" style="margin-right: 15px" @click="goBack">退出</el-button>

                    <el-dropdown style="float: right" @command="handleCommand">

                        <span> <i class="el-icon-setting" style="margin-right: 30px"></i></span>

                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="a">个人信息</el-dropdown-item>
                            <el-dropdown-item command="b">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <span style="margin-right: 45px;float: right">欢迎您，{{username}}</span>

                </el-header>
                <!--主体-->
                <el-main><router-view/></el-main>
            </el-container>
        </el-container>

    </div>
</template>

<script>
    import NavMenu from "./navMenu";
    import Breadcrumb from './breadcrumb';

    export default {
        name: "index",
        components: {NavMenu,Breadcrumb},
        data(){
            return {
                data: [],
                username: '',
            }
        },
        methods: {
            handleCommand(command) {
                if (command === 'b') {
                    this.$router.push('/login');
                } else if (command === 'a') {
                    this.$router.push({
                        name: 'set_personal_teacher',
                        url: '/ht/set',
                    });
                }
            },
            goBack() {
                this.$confirm('是否退出登录?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {

                    this.$message({
                        type: 'success',
                        message: '已退出'
                    });
                    localStorage.removeItem('token');
                    localStorage.removeItem('loginUser');
                    localStorage.removeItem('role');
                    this.$router.push('/login');
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消退出'
                    });
                });


            },
        },
             mounted() {

            this.data = JSON.parse(localStorage.getItem('loginUser'));
                 this.username = this.data.tName;
             }
    }
</script>

<style type="scss" scoped>
    .exit {
        float: right;
        margin-top: 10px;
    }

    .el-header, .el-footer {
        background-color: #dcedf3;
        color: #333;
        line-height: 60px;
    }


    .el-main {
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
        line-height: 160px;
    }

    body > .el-container {
        background-color: #1b81c6;
        margin-bottom: 40px;
        height: 100%;
    }

</style>