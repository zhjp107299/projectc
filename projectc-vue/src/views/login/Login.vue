<template>
   <div  class="login-box">
           <el-form class="login-ruleForm" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
               <h3 class="login-title">欢迎登陆</h3>
               <el-form-item label="账号" prop="username">
                   <el-input type="text" placeholder="请输入账号" v-model="ruleForm.username"/>
               </el-form-item>
               <el-form-item label="密码" prop="password">
                   <el-input type="password" show-password placeholder="请输入密码" v-model="ruleForm.password"/>
               </el-form-item>
               <el-form-item>
                   <div style="margin-left: -80px;font-family: Arial">角色：
                       <el-radio-group v-model="radioRole" size="small">
                           <el-radio  label="admin" border>管理员</el-radio>
                           <el-radio  label="teacher" border>教师</el-radio>
                           <el-radio  label="student" border>学生</el-radio>
                           <el-radio  label="tourist" border>游客</el-radio>
                       </el-radio-group>
                   </div>
               </el-form-item>
               <el-form-item>
                   <el-button type="primary" :plain="true" v-on:click="onSubmit('ruleForm')">登录</el-button>
                   <el-button @click="resetForm('ruleForm')">重置</el-button>
                   <el-button type="primary" @click="studentRegister">游客注册</el-button>
               </el-form-item>
           </el-form>


   </div>
</template>

<script>
    import axios from '../../api';

    export default {
        name: "Login",
        data(){
            return{
                radioRole:'student',
                ruleForm:{
                    username:'',
                    password:'',
                },
                rules:{
                    username:{required: true, message: '账号不可为空', trigger: 'blur'},
                    password:[
                        {required: true, message: '密码不可为空', trigger: 'blur'},
                        {min: 5, max: 18, message: '长度在6~18个字符', trigger: 'blur'}
                    ]
                }
            }
        },
        methods:{
            studentRegister(){
                this.$router.push('/register');
            },
            onSubmit(formName){
                let {username,password} = this.ruleForm;
                console.log(this.ruleForm);
                this.$refs[formName].validate((valid=>{
                    if (valid) {
                        //管理员登录
                        if (this.radioRole === 'admin') {

                            axios({
                                method: 'post',
                                url: '/admin/login',
                                data: this.$qs.stringify({
                                    username: this.ruleForm.username,
                                    password: this.ruleForm.password
                                })
                            }).then(res => {
                                console.log(res);
                                let {code, msg} = res.data.info;
                                this.$global.studentId = res.data.data.sId;
                                if (code === 200) {
                                    //存储角色
                                    localStorage.setItem("role", "admin");
                                    //设置token/角色信息失效时间
                                    const expire = 1000 * 60 * 60 * 24 * 365;  //一天
                                    localStorage.setStorageWithAge('token', res.data.data.token.token, expire);

                                    localStorage.setItem("loginUser", JSON.stringify(res.data.data.loginAdmin));
                                    this.$router.push('/admin');
                                    this.$message({
                                        message: msg,
                                        type: 'success'
                                    });
                                    return 0;
                                } else {
                                    this.$message({
                                        message: msg,
                                        type: 'error'
                                    });
                                    return 0;
                                }

                            }).catch(err => {

                                console.log(err);
                              this.$message({
                                message: "后端服务未启动或出错，请联系管理员",
                                type: 'error'
                              });
                            });

                        } else {
                            axios({
                                method: 'post',
                                url: '/user/login',
                                data: this.$qs.stringify({
                                    username: this.ruleForm.username, password: this.ruleForm.password,
                                    role: this.radioRole
                                })
                            }).then(res => {
                                console.log(res);

                                let {code, msg} = res.data.info;
                                this.$global.studentId = res.data.data.sId;

                                if (code === 200) {
                                    //存储角色
                                    localStorage.setItem("role", this.radioRole);
                                    //设置token/角色信息失效时间
                                    const expire = 1000 * 60 * 60 * 24;  //一天
                                    localStorage.setStorageWithAge('token', res.data.data.token.token, expire);


                                    if (this.radioRole === 'teacher') {
                                        //登录成功进行操作
                                        //存储数据
                                        localStorage.setItem("loginUser", JSON.stringify(res.data.data.loginTeacher));
                                        this.$router.push({
                                            name: 'ht_index',
                                            url: '/home_teacher',
                                        })//页面跳转
                                    } else if (this.radioRole === 'student') {
                                        //登录成功进行操作
                                        //存储数据
                                        localStorage.setItem("loginUser", JSON.stringify(res.data.data.loginStudent));
                                        this.$router.push({
                                            name: 'hs_index',
                                            url: '/home_student',
                                        });//页面跳转

                                    } else {
                                        localStorage.setItem("loginUser", JSON.stringify(res.data.data.loginStudent));
                                        this.$router.push({
                                            name: 'hs_index',
                                            url: '/home_student',
                                        });//页面跳转
                                    }
                                    this.$message({
                                        message: msg,
                                        type: 'success'
                                    });
                                } else {
                                    this.$message({
                                        message: msg,
                                        type: 'error'
                                    });
                                }


                            }).catch(err => {
                              this.$message({
                                message: "后端服务未启动或出错，请联系管理员",
                                type: 'error'
                              });
                                console.log(err)
                            })
                        }
                    }
                    else
                        {
                            this.$message({
                                message: '请按要求输入',
                                type: 'warning'
                            });
                            return false;
                        }
                }))
            },
            resetForm(formName){
                this.$refs[formName].resetFields();
            }
        },
    }
</script>

<style scoped>
    .el-radio {
        margin-right: 10px;
    }
    .login-box {
        width: 100%;
        height: 100%;
        background-image: url("../../assets/bg_login.jpg");
        background-size: cover;
        position: absolute;
    }
    .login-ruleForm {
        border: 1px solid #DCDFE6;
        background-color: white;
        opacity: 0.95;
        width: 440px;
        margin: 180px auto;
        padding: 35px 35px 15px 35px;
        border-radius: 5px;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        box-shadow: 0 0 15px #1b81c6;
    }
    .login-title {
        text-align: center;
        margin: 0 auto 40px auto;
        color: #0083c6;
        font-size: 30px;
        font-weight: bold;
    }
</style>