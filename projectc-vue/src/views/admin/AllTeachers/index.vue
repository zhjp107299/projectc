<template>
    <div class="options">
        <el-button type="primary" plain style="float: left;margin-right: 20px;margin-top: -2px;" @click="teacherAdd">添加教师<i class="el-icon-document-add el-icon--right" ></i></el-button>
        <el-upload
                style="float: left;margin-top: -60px;margin-bottom: -40px"
                class="upload-demo"
                name="file"
                ref="upload"
                action=""
                :http-request="handleUpload"
                limit="1"
                :show-file-list="false"
                :headers="headers"
                :auto-upload="true"
                :on-change="handleChange"
                accept=".xls,.xlsx">
            <el-button type="primary" plain @click="importTeachers">批量添加教师<i class="el-icon-upload2 el-icon--right" ></i></el-button>

        </el-upload>
        <el-button @click="downloadTemplate()" plain style="float: left;margin-left: 20px;margin-top: -2px;" type="warning">批量添加教师模板下载</el-button>
        <el-button type="primary" plain style="float: right;" @click="adminAdd">添加管理员<i class="el-icon-document-add el-icon--right" ></i></el-button>
        <el-table  stripe :data="tableData" style="width: 100%;">
            <el-table-column width="200px" sortable
                             prop="tId"
                             label="工号">
            </el-table-column>
            <el-table-column width="200px" sortable
                             prop="tName"
                             label="姓名">
            </el-table-column>
            <el-table-column sortable width="200px"
                             prop="tDeadline"
                             label="账号到期">
            </el-table-column>
            <el-table-column sortable width="200px"
                             prop="tPermission"
                             label="权限等级">
            </el-table-column>
            <el-table-column label="激活状态" prop="status">
                <template slot-scope="scope">
                    <el-switch
                            @change="((val)=>{switchChange(val,scope.row)})"
                            v-model="scope.row.status"
                            active-text="开启"
                            inactive-text="关闭">
                    </el-switch>
                </template>
            </el-table-column>

            <el-table-column label="操作">

                <!--                type="primary" size="small" split-button-->
                <template slot-scope="scope">
                    <el-dropdown trigger="click"  @command="((command)=>{handleCommand(command,scope.row)})">
                        <el-button type="primary">
                            权限<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="0">0</el-dropdown-item>
                            <el-dropdown-item command="1">1</el-dropdown-item>
                            <el-dropdown-item command="2">2</el-dropdown-item>
                            <el-dropdown-item command="3">3</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <el-dropdown  trigger="click" @command="((command)=>{handleCommandTime(command,scope.row)})">
                        <el-button type="primary" style="margin-left: 10px;">
                            延期<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="1">一个月</el-dropdown-item>
                            <el-dropdown-item command="3">三个月</el-dropdown-item>
                            <el-dropdown-item command="6">半年</el-dropdown-item>
                            <el-dropdown-item command="12">一年</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <el-button
                            style="margin-left: 10px;"
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination
                    @current-change="handleCurrentChange"
                    :current-page.sync="currentPage"
                    :page-size="pageSize"
                    layout="total, prev, pager, next"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import axios from '../../../api';

    export default {
        data() {
            return {
                fileList: [],
                headers: {'Access-Control-Allow-Origin':'*'},
                tableData: [],
                pageSize:10,
                total:'',
                currentPage: 1,
            }
        },
        methods: {
            downloadTemplate() {
                let baseUrl = 'http://localhost:8082';
                location.href = baseUrl + '/135799download?type=teacher';
            },
            adminAdd(){
                this.$router.push('/admin/admin_add');
            },
            teacherAdd(){
                this.$router.push('/admin/teacher_add');
            },
            importTeachers(){
                this.$refs.upload.submit();
            },



            handleCommandTime(command,param) {
                axios({
                    method: 'post',
                    url: '/change_teacher_time_admin',
                    data: this.$qs.stringify({id:param.tId,plusNumber:command})
                }).then(res=>{
                    alert(res.data.msg);
                    location.reload();
                }).catch(err=>{
                    console.log(err);
                })
            },

            handleCommand(command,param) {
                axios({
                    method: 'post',
                    url: '/change_teacher_permission_admin',
                    data: this.$qs.stringify({id:param.tId,permission:command})
                }).then(res=>{
                    alert(res.data.msg);
                    location.reload();
                }).catch(err=>{
                    console.log(res);
                })
            },

            switchChange(val,param){
                console.log(val);
                console.log(param);

                axios({
                    method: 'post',
                    url: '/change_teacher_status_admin',
                    data: this.$qs.stringify({status:val,id:param.tId})
                }).then(res=>{
                    this.$message({
                        message: res.data.msg,
                        type: 'success'
                    });

                }).catch(err=>{
                    console.log(err);
                })
            },
            handleChange(file, fileList){
                this.fileList = fileList.slice(-3);
            },
            handleUpload(uploadFile){
                let formData = new FormData();
                formData.set('file',uploadFile.file);
                axios({
                    method: 'post',
                    url: '/upload',
                    headers:{'Content-Type':'multipart/form-data'},
                    data: formData,
                }).then(res=>{
                    console.log(res);
                    let {code,msg} = res.data.info;
                    if (code===400){
                        alert(msg);
                        location.reload();
                    }else if (code===200){
                        axios({
                            method: 'post',
                            url: '/import_teachers',
                            data: this.$qs.stringify({fileType:uploadFile.file.name,uploadFilePath:res.data.data})
                        }).then(res=>{
                            console.log(res);
                            let {code,msg} = res.data;
                            if (code===200){
                                alert(msg);
                                location.reload();
                            }else {
                                alert(msg);
                                location.reload();
                            }
                        }).catch(err=>{
                            console.log(err);
                        });

                    }
                }).catch(err=>{
                    console.log(err)
                });
            },

            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
                this.getTableData();
            },
            getStudentsCount(){
                axios({
                    method: 'post',
                    url: '/get_teachers_count_admin',
                }).then(res=>{
                    console.log(res);
                    this.total = res.data;
                }).catch(err=>{
                    console.log(err);
                })
            },
            getTableData(){
                axios({
                    method: 'post',
                    url: '/teacher_info_admin',
                    data: this.$qs.stringify({currentPage: this.currentPage,pageSize: this.pageSize})
                }).then(res=>{
                    console.log(res);
                    this.tableData = res.data.data;
                }).catch(err=>{
                    console.log(err);
                })
            },

            handleDelete(index, row) {
                this.$confirm('是否删除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios({
                        method: 'post',
                        url: '/teacher_delete',
                        data: this.$qs.stringify({deleId: row.tId})
                    }).then(res=>{

                        let {code,msg} = res.data;
                        if (code === 200) {
                            alert(msg);
                            location.reload();
                        }else {
                            this.$message({
                                type: 'info',
                                message: msg
                            });
                        }
                    }).catch(err=>{
                        console.log(err);
                    });


                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            },
        },
        mounted() {
            this.getTableData();
            this.getStudentsCount();
        }
    }
</script>

<style type="scss" scoped>

    .options >>> .el-button--mini, .el-button--mini.is-round {
        padding: 12px 12px;
    }
    .el-table {
        margin: 0;
        padding: 0;
    }
    .block {
        margin-top: 40px;
    }
    .el-dropdown-link {
        cursor: pointer;
        color: #409EFF;
    }
    .el-icon-arrow-down {
        font-size: 12px;
    }
</style>