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
                    {title: '首页', url: '/admin/index', icon: 'el-icon-s-home'},
                    {title: '查看所有教师', url: '/admin/all_teachers', icon: 'el-icon-edit'},
                    {title: '查看所有学生', url: '/admin/all_students', icon: 'el-icon-edit'},
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
