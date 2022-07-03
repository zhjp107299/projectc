<template>
    <div>
        <div>
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
            </el-upload>
            <el-table  stripe :data="tableData" style="width: 100%;">
                <el-table-column sortable
                                 prop="indepropoName"
                                 label="自主命题文件名">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="handleTest(scope.$index, scope.row)">测试
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
            handleChange(val) {
                console.log(val);
            },
            getTableData(){
                axios({
                    method: 'post',
                    url: '/indepropo_name',
                    data: this.$qs.stringify({teacher:JSON.parse(localStorage.getItem('loginUser')).sTeacher})
                }).then(res=>{
                    console.log(res);
                    this.tableData = res.data.data;
                }).catch(err=>{
                    console.log(err);
                })
            },
            handleTest(index, row) {
                console.log(index, row);
                this.$global.testName = row.indepropoName;
                axios({
                    method: 'post',
                    url: '/indepropo_status',
                    data: this.$qs.stringify({indeName:row.indepropoName})
                }).then(res=>{
                    console.log(res);
                    if (res.data.data==='on'){

                        this.$router.push({
                            name: 'indepropo_test',
                            url: '/hs/indepropo_test',
                            params: {
                                data: row,
                            },
                        });
                    }else {
                        alert("教师暂未开放该测试")
                    }
                }).catch(err=>{
                    console.log(err);
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