<template>
    <div>
        <h1>Excel判断题库导入</h1>
        <el-upload
                class="upload-demo"
                name="file"
                ref="upload"
                action=""
                :http-request="handleUpload"
                limit="1"
                :headers="headers"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :file-list="fileList"
                :auto-upload="false"
                accept=".xls,.xlsx">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
            <el-button @click="downloadTemplate()" size="small" type="warning">判断题库模板下载</el-button>
            <div slot="tip" class="el-upload__tip" >只能上传.xls/.xlsx文件,限制一次只能上传一个文件</div>
        </el-upload>

    </div>
</template>

<script>
    import axios from '../../../api';
    export default {
        data() {
            return {
                headers: {'Access-Control-Allow-Origin':'*'},
                fileList: [],
            };
        },
        methods: {
            downloadTemplate() {
                let baseUrl = 'http://localhost:8082';
                location.href = baseUrl + '/135799download?type=judge';
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
                    }else if (code===200){
                        axios({
                            method: 'post',
                            url: '/import_judge',
                            data: this.$qs.stringify({fileType:uploadFile.file.name,uploadFilePath:res.data.data})
                        }).then(res=>{
                            console.log(res);
                            let {code,msg} = res.data;
                            if (code===200){
                                alert(msg);
                            }else {
                                alert(msg);
                            }
                        }).catch(err=>{
                            console.log(err);
                        });
                    }
                }).catch(err=>{
                    console.log(err)
                });
            },
            submitUpload() {
                this.$refs.upload.submit();
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePreview(file) {
                console.log(file);
            }
        }
    }
</script>
<style>

</style>
