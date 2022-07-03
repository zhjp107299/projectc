<template>
    <div>
        <div>
            <el-button type="primary" plain style="float: left;margin-bottom: 20px;" @click="indepropoAdd">添加自主命题<i class="el-icon-edit-outline el-icon--right" ></i></el-button>
            <el-upload
                    style="float: left;margin-top: -60px;margin-bottom: -40px"
                    class="upload-demo"
                    name="file"
                    ref="upload"
                    action=""
                    limit="1"
                    :show-file-list="false"
                    :auto-upload="true"
                    :on-change="handleChange"
                    accept=".xls,.xlsx">
            </el-upload>
            <el-button @click="downloadTemplate()" plain style="float: left;margin-left: 20px;margin-top: -2px;" type="warning">添加自主命题模板下载</el-button>
            <el-table  stripe :data="tableData" style="width: 100%;">
                <el-table-column sortable
                                 prop="indepropoName"
                                 label="自主命题文件名">
                </el-table-column>

                    <el-table-column label="状态" prop="status">
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
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="handleQuery(scope.$index, scope.row)">查看
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    import axios from "../../../api";

    export default {
        data() {
            return {

              tableData:[]
            };
        },
        methods: {
            downloadTemplate() {
                let baseUrl = 'http://localhost:8082';
                location.href = baseUrl + '/135799download?type=indepropo';
            },
            switchChange(val,param){
                console.log(val);
                console.log(param);

                axios({
                    method: 'post',
                    url: '/switch_change',
                    data: this.$qs.stringify({status:val,indeName:param.indepropoName})
                }).then(res=>{
                    console.log(res);

                }).catch(err=>{
                    console.log(err);
                });
                this.$message.success('试卷状态已调整');
            },
            indepropoAdd(){
                this.$router.push('/ht/indepropo/add');
            },
            handleChange(val) {
                console.log(val);
            },
            getTableData(){
                axios({
                    method: 'post',
                    url: '/indepropo_name_status',
                    data: this.$qs.stringify({teacher:JSON.parse(localStorage.getItem('loginUser')).tName})
                }).then(res=>{
                    console.log(res);
                    this.tableData = res.data.data;
                }).catch(err=>{
                    console.log(err);
                });

            },
            handleQuery(index, row) {
                console.log(index, row);
                this.$global.testName = row.indepropoName;
                this.$router.push({
                    name: 'indepropoQuery',
                    url: '/ht/indepropo/query',
                    params: {
                        data: row,
                    },
                });
            }
        },
        mounted() {
            this.getTableData();
        }
    }
</script>

<style scoped>

</style>