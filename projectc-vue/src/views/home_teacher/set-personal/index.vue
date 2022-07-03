<template>
    <div>
        <el-card class="box-card">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="教师ID" prop="tId">
                    <el-input @blur="validateTID" type="text" v-model="ruleForm.tId" :disabled="true"></el-input><span  class="validateInfo">{{validateInfo}}</span>
                </el-form-item>

                <el-form-item label="姓名" prop="tName">
                    <el-input type="text" v-model="ruleForm.tName"></el-input>
                </el-form-item>

                <el-form-item label="密码" prop="tPassword">
                    <el-input type="password" show-password  v-model="ruleForm.tPassword"></el-input><span  class="pwdInfo">{{pwdInfo}}</span>
                </el-form-item>
                <el-form-item label="确认密码" prop="pwdRequire">
                    <el-input @blur="pwdRequired" show-password  type="password" v-model="ruleForm.pwdRequire"></el-input><span  class="pwdInfo">{{pwdInfo}}</span>
                </el-form-item>
                <el-form-item label="权限等级" prop="tPermission">
                    <el-input  type="text" v-model="ruleForm.tPermission" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="账号到期时间" prop="tDeadline">
                    <el-input  type="text" v-model="ruleForm.tDeadline" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="激活状态" prop="tStatus">
                    <el-input  type="text" v-model="ruleForm.tStatus" :disabled="true"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">立即修改</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                    <el-button @click='goBack()'>返回</el-button>
                </el-form-item>
            </el-form>
        </el-card>

    </div>
</template>

<script>
    import axios from '../../../api/index'
    export default {
        props: ['validateInfo','pwdInfo'],
        data(){
            return {
                ruleForm: {
                    tId: '',
                    tName: '',
                    tPassword: '',
                    pwdRequire: '',
                    tPermission: '',
                    tDeadline: '',
                    tStatus: '',

                    validateInfo:'',
                    pwdInfo:''
                },
                rules: {
                    tId: [
                        {required:true, message:'ID不可为空',trigger:'blur'},
                    ],
                    tName: [
                        {required:true,message:'姓名不可为空',trigger:'blur'},
                        {pattern:/[\u4E00-\u9FA50-9]{1,}$/,message: '姓名为至少1位的中文字符'}],
                    tPassword: [
                        {required: true, message: '密码不可为空', trigger: 'blur'},
                        {min: 6, max: 18, message: '长度在6~18个字符', trigger: 'blur'}
                    ],
                    pwdRequire: {required:true,message:'请确认密码',trigger:'blur'},
                }
            }
        },
        methods: {
            goBack(){
                history.go(-1);
            },
            pwdRequired(){
                if (this.ruleForm.tPassword!==this.ruleForm.pwdRequire){
                    this.pwdInfo = '密码不一致';
                }else{
                    this.pwdInfo = ''
                }
            },

            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.ruleForm.tPassword!==this.ruleForm.pwdRequire){
                            this.pwdInfo = '密码不一致';
                            return ;
                        }
                        axios({
                            method: 'post',
                            url: '/teacher_modify',
                            data: this.$qs.stringify({tId: this.ruleForm.tId,tName: this.ruleForm.tName,
                                tPassword: this.ruleForm.tPassword})
                        }).then(res=>{
                            let {code,msg} = res.data;

                            if (code === 200){
                                alert(msg);
                                axios({
                                    method: 'post',
                                    url: '/update_teacher',
                                    data: this.$qs.stringify({id:this.ruleForm.tId})
                                }).then(res=>{
                                    console.log(res);
                                    localStorage.setItem('loginUser',JSON.stringify(res.data.data))
                                }).catch(err=>{
                                    console.log(err)
                                });
                                this.$router.push('/home_teacher');//页面跳转
                            }else {
                                alert(msg);
                            }
                        }).catch(err=>{
                            console.log(err)
                        });
                    } else {
                        console.log('提交失败');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
        },
        mounted() {

            this.ruleForm = JSON.parse(localStorage.getItem('loginUser'));
        },
    }
</script>

<style scoped>
    .pwdInfo {
        color: red;
        font-size: 12px;
        float: right;
        height: 10px;
        margin-top: -10px;
    }
    .validateInfo {
        color: red;
        font-size: 12px;
        float: left;
        height: 10px;
        margin-top: -10px;

    }
    .box-card {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 700px;
        margin: 0 auto;
    }
    .demo-ruleForm {
        padding-top: 40px;
        width: 600px;

    }
</style>