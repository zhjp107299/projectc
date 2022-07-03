<template>
    <div>
        <div class="aside">
        <el-aside width="200px">
            <el-menu :default-active="$route.path" exact
                     class="el-menu-vertical-demo" router
                     style="width: 100%"
                     @open="handleOpen"
                     @close="handleClose"
                     background-color="#545c64"
                     text-color="#fff"
                     active-text-color="#33ccff">
                <NavItem v-for="v in items" :key="v.url" :item="v" :basePath="v.url"/>

            </el-menu>
        </el-aside>
        </div>
    </div>
</template>

<script>
    import NavItem from "./NavItem";

    export default {
        name: "navMenu",
        components: {NavItem},
        data() {
            return {
                items: [
                    {title:'首页',url:'/ht/index',icon:'el-icon-s-home'},
                    {title:'自主命题',url:'/ht/indepropo',icon: 'el-icon-edit'},
                    {
                        title:'Excel题库导入',
                        icon: 'el-icon-upload',
                        url:'/ht/excelimpo',
                        child:[{
                            title:'选择题库导入',
                            icon: 'el-icon-upload2',
                            url:'/ht/excelimpo/choice_bank'
                        },
                        {
                            title:'填空题库导入',
                            icon: 'el-icon-upload2',
                            url:'/ht/excelimpo/gapfilling_bank'
                        },
                        {
                            title:'判断题库导入',
                            icon: 'el-icon-upload2',
                            url:'/ht/excelimpo/judge_bank'
                        },
                        {
                            title:'编程题库导入',
                            icon: 'el-icon-upload2',
                            url:'/ht/excelimpo/programme_bank'
                        },
                        ]
                    },
                    {title:'查看学生',url:'/ht/sstatus',icon: 'el-icon-view'}
                ]
            }
        },
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        },
        comments: {NavItem}
    }
</script>

<style type="scss" scoped>
    .aside{
        height: 100%;
    }
    .el-aside {
        background-color: #545c64;
        height: 100%;
        overflow: hidden;
    }
</style>
