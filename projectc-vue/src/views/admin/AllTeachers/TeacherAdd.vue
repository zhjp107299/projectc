<template>
    <div>
        <el-card class="box-card">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="教师工号" prop="tId">
                    <el-input @blur="validateTID" @focus="focusTID" type="text" v-model="ruleForm.tId"></el-input><span class="validateInfo">{{validateInfo}}</span>
                </el-form-item>

                <el-form-item label="姓名" prop="tName">
                    <el-input type="text" v-model="ruleForm.tName"></el-input>
                </el-form-item>

                <el-form-item label="密码" prop="tPassword">
                    <el-input type="password"  show-password v-model="ruleForm.tPassword"></el-input><span  class="pwdInfo">{{pwdInfo}}</span>
                </el-form-item>
                <el-form-item label="确认密码" prop="pwdRequire">
                    <el-input @blur="pwdRequired" show-password type="password" v-model="ruleForm.pwdRequire"></el-input><span  class="pwdInfo">{{pwdInfo}}</span>
                </el-form-item>


                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">添加</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-card>

    </div>
</template>

<script>
    import axios from '../../../api'
    export default {
        props: ['validateInfo','pwdInfo'],
        data(){
            return {
                ruleForm: {
                    tId: '',
                    tName: '',
                    tPassword: '',
                    pwdRequire: '',

                    validateInfo:'',
                    pwdInfo:'',
                },
                rules: {
                    tId: [
                        {required:true, message:'教师工号不可为空',trigger:'blur'},
                    ],
                    tName: [
                        {required:true,message:'教师姓名不可为空',trigger:'blur'},
                        {pattern:/[\u4E00-\u9FA50-9]{1,}$/,message: '姓名为至少1位的中文字符'}],
                    tPassword: [
                        {required: true, message: '密码不可为空', trigger: 'blur'},
                        {min:6,max:16,message: '密码长度在6~16个字符', trigger: 'blur'},
                    ],
                    pwdRequire: {required:true,message:'请确认密码',trigger:'blur'},

                }
            }
        },
        methods: {
            focusTID(){
                this.validateInfo="";
            },
            pwdRequired(){
                if (this.ruleForm.tPassword==="" && this.ruleForm.pwdRequire===""){
                    this.pwdInfo = ''
                }else if (this.ruleForm.tPassword!==this.ruleForm.pwdRequire){
                    this.pwdInfo = '密码不一致';
                }else{
                    this.pwdInfo = ''
                }
            },
            validateTID(){

                axios({
                    method: 'post',
                    url: '/validate_tid',
                    data: this.$qs.stringify({tId:this.ruleForm.tId})
                }).then(res=>{
                    console.log(res);
                    let {msg,code} = res.data;
                    if (msg==="NotNull"){
                        this.validateInfo = '该用户已存在';
                    }else{
                        this.validateInfo = ''
                    }

                }).catch(err=>{
                    console.log(err);
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.validateInfo==='该用户已存在'){
                            return ;
                        }
                        if (this.ruleForm.tPassword!==this.ruleForm.pwdRequire){
                            this.pwdInfo = '密码不一致';
                            return ;
                        }
                        axios({
                            method: 'post',
                            url: '/teacher_add',
                            data: this.$qs.stringify({tId: this.ruleForm.tId,
                                tName: this.ruleForm.tName, tPassword: this.ruleForm.tPassword})
                        }).then(res=>{
                            let {code,msg} = res.data;

                            if (code === 200){
                                alert(msg);
                                this.$router.push('/admin')//页面跳转
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