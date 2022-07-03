<template>
    <div class="options">

        <el-table  stripe :data="tableData" style="width: 100%;">
            <el-table-column width="140px" sortable
                             prop="sId"
                             label="学号">
            </el-table-column>
            <el-table-column width="140px" sortable
                             prop="sName"
                             label="姓名">
            </el-table-column>
            <el-table-column  width="140px" sortable
                              prop="sClass"
                              label="班级">
            </el-table-column>
            <el-table-column width="140px"
                             prop="sTeacher"
                             label="所属教师">
            </el-table-column>
            <el-table-column sortable width="140px"
                             prop="sDeadline"
                             label="账号到期">
            </el-table-column>
            <el-table-column sortable width="160px"
                             prop="sPermission"
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
                            <el-dropdown-item command="4">4</el-dropdown-item>
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
            handleCommandTime(command,param) {
                axios({
                    method: 'post',
                    url: '/change_time_admin',
                    data: this.$qs.stringify({id:param.sId,plusNumber:command})
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
                    url: '/change_permission_admin',
                    data: this.$qs.stringify({id:param.sId,permission:command})
                }).then(res=>{
                    alert(res.data.msg);
                    location.reload();
                }).catch(err=>{
                    console.log(err);
                })
            },

            switchChange(val,param){
                console.log(val);
                console.log(param);

                axios({
                    method: 'post',
                    url: '/change_status_admin',
                    data: this.$qs.stringify({status:val,id:param.sId})
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

            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
                this.getTableData();
            },
            getStudentsCount(){
                axios({
                    method: 'post',
                    url: '/get_students_count_admin',
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
                    url: '/student_info_admin',
                    data: this.$qs.stringify({currentPage: this.currentPage,pageSize: this.pageSize})
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
                this.$confirm('是否删除?', '提示', {
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
    .el-dropdown-link {
        cursor: pointer;
        color: #409EFF;
    }
    .el-icon-arrow-down {
        font-size: 12px;
    }
</style>