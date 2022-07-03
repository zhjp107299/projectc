<template>
    <div class="options">
        <el-button type="primary" plain style="float: left;margin-right: 20px;margin-top: -2px;" @click="studentAdd">添加学生<i class="el-icon-document-add el-icon--right" ></i></el-button>
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
        <el-button type="primary" plain @click="importStudents">批量添加学生<i class="el-icon-upload2 el-icon--right" ></i></el-button>
        </el-upload>
        <el-button @click="downloadTemplate()" plain style="float: left;margin-left: 20px;margin-top: -2px;" type="warning">批量添加学生模板下载</el-button>
        <el-table  stripe :data="tableData" style="width: 100%;">
            <el-table-column width="160px" sortable
                    prop="sId"
                    label="学号">
            </el-table-column>
            <el-table-column width="160px" sortable
                    prop="sName"
                    label="姓名">
            </el-table-column>
            <el-table-column width="140px"
                    prop="sGender"
                    label="性别">
            </el-table-column>
            <el-table-column  width="160px" sortable
                    prop="sClass"
                    label="班级">
            </el-table-column>
            <el-table-column width="160px"
                    prop="sTeacher"
                    label="所属教师">
            </el-table-column>
            <el-table-column sortable
                    prop="updateTime"
                    label="更新日期">
            </el-table-column>
            <el-table-column sortable
                     prop="createTime"
                     label="创建日期">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="handleQuery(scope.$index, scope.row)">查看
                    </el-button>
                    <el-button
                            size="mini"
                            @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button
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
                location.href = baseUrl + '/135799download?type=student';
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
                    if (code===200){
                        axios({
                            method: 'post',
                            url: '/import_students',
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

                    }else {
                        alert("文件上传失败");
                        location.reload();
                    }
                }).catch(err=>{
                    console.log(err)
                });
            },
            studentAdd(){
                this.$router.push('/ht/sstatus/add');
            },
            importStudents(){
                this.$refs.upload.submit();
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
                this.getTableData();
            },
            getStudentsCount(){
                console.log(JSON.parse(localStorage.getItem('loginUser')).tName)
                axios({
                    method: 'post',
                    url: '/get_students_count',
                    data: this.$qs.stringify({tName:JSON.parse(localStorage.getItem('loginUser')).tName})
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
                    url: '/student_info',
                    data: this.$qs.stringify({tName:JSON.parse(localStorage.getItem('loginUser')).tName,currentPage: this.currentPage,pageSize: this.pageSize})
                }).then(res=>{
                    console.log(res);
                    this.tableData = res.data.data;
                }).catch(err=>{
                    console.log(err);
                })
            },
            handleEdit(index, row) {
                console.log(index, row);
                this.$router.push({
                    name: 'edit',
                    path: '/ht/sstatus/edit',
                    params: {
                      data: row,
                    },
                });
            },
            handleDelete(index, row) {
                console.log(index, row);
                this.$confirm('是否删除用户该用户？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios({
                        method: 'post',
                        url: '/student_delete',
                        data: this.$qs.stringify({deleId: row.sId})
                    }).then(res=>{
                        console.log(res);
                        let {code,msg} = res.data;
                        if (code === 200) {
                            alert(msg);
                            location.reload();
                        }else {
                            alert(msg);
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
            handleQuery(index, row) {
                console.log(index, row);
                this.$router.push({
                    name: 'query',
                    url: '/ht/sstatus/query',
                    params: {
                        data: row,
                    },
                });
            }
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
</style>