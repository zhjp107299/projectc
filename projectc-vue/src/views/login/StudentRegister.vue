<template>
    <div>
        <el-card class="box-card">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="学生ID" prop="sId">
                    <el-input @blur="validateSID" @focus="focusSID" type="text" v-model="ruleForm.sId"></el-input><span class="validateInfo">{{validateInfo}}</span>
                </el-form-item>

                <el-form-item label="姓名" prop="sName">
                    <el-input type="text" v-model="ruleForm.sName"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sGender">
                    <el-input type="text" v-model="ruleForm.sGender"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="sPassword">
                    <el-input type="password" show-password v-model="ruleForm.sPassword"></el-input><span  class="pwdInfo">{{pwdInfo}}</span>
                </el-form-item>
                <el-form-item label="确认密码" prop="pwdRequire">
                    <el-input @blur="pwdRequired"  show-password type="password" v-model="ruleForm.pwdRequire"></el-input><span  class="pwdInfo">{{pwdInfo}}</span>
                </el-form-item>
                <el-form-item label="所属班级" prop="sClass">
                    <el-input type="text" v-model="ruleForm.sClass"></el-input>
                </el-form-item>
                <el-form-item label="所属教师" prop="sTeacher">
                    <el-input type="text" v-model="ruleForm.sTeacher"></el-input>
                </el-form-item>
                <el-form-item style="margin-left: 120px;">
                    <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                    <el-button @click="goBack">返回</el-button>
                </el-form-item>
            </el-form>
        </el-card>

    </div>
</template>

<script>
    import axios from '../../api/index'
    export default {
        props: ['validateInfo','pwdInfo'],
        data(){
            return {
                ruleForm: {
                    sId: '',
                    sName: '',
                    sGender:'',
                    sPassword: '',
                    pwdRequire: '',
                    sClass: '',
                    sTeacher: '',
                    createTime: '',
                    validateInfo:'',
                    pwdInfo:'',
                },
                rules: {
                    sId: [
                        {required:true, message:'学生ID不可为空',trigger:'blur'},
                        {pattern:/^\d{9}/,message: 'ID为9位正整数'},
                    ],
                    sName: [
                        {required:true,message:'学生姓名不可为空',trigger:'blur'},
                        {pattern:/[\u4E00-\u9FA50-9]{1,}$/,message: '姓名为至少1位的中文字符'}],
                    sPassword: [
                        {required: true, message: '密码不可为空', trigger: 'blur'},
                        {min:6,max:16,message: '密码长度在6~16个字符', trigger: 'blur'},
                    ],
                    pwdRequire: {required:true,message:'请确认密码',trigger:'blur'},
                    sGender: [
                        {required:true,message:'学生性别不可为空',trigger:'blur'},
                    ],
                    sClass: [
                        {required:true,message:'学生班级不可为空',trigger:'blur'},
                    ],
                    sTeacher: [
                        {required:true,message:'所属教师不可为空',trigger:'blur'},
                    ]
                }
            }
        },
        methods: {
            goBack(){
                this.$router.push('/')
            },
            focusSID(){
                this.validateInfo="";
            },
            pwdRequired(){
                if (this.ruleForm.sPassword==="" && this.ruleForm.pwdRequire===""){
                    this.pwdInfo = ''
                }else if (this.ruleForm.sPassword!==this.ruleForm.pwdRequire){
                    this.pwdInfo = '密码不一致';
                }else{
                    this.pwdInfo = ''
                }
            },
            validateSID(){
                if (this.ruleForm.sId.length>9){
                    this.validateInfo = '学生ID只能为9位';
                    return;
                }else {
                    this.validateInfo = ''
                }
                axios({
                    method: 'post',
                    url: '/validate_sid',
                    data: this.$qs.stringify({sId:this.ruleForm.sId})
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
                        axios({
                            method: 'post',
                            url: '/tourist_register',
                            data: this.$qs.stringify({sId: this.ruleForm.sId,
                                sName: this.ruleForm.sName, sGender:this.ruleForm.sGender,
                                sPassword: this.ruleForm.sPassword,sClass: this.ruleForm.sClass,
                                sTeacher: this.ruleForm.sTeacher,})
                        }).then(res=>{
                            let {code,msg} = res.data;

                            if (code === 200){
                                alert('注册成功');
                                this.$router.push('/')//页面跳转
                            }else {
                                alert('注册失败');
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
        margin: 100px auto;
    }
    .demo-ruleForm {
        padding-top: 40px;
        width: 600px;
    }
</style>