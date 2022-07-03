<template>
    <!--无子级-->
    <el-menu-item :index="basePath" v-if="!item.child">
        <i :class="item.icon"></i>
        <span class="title" slot="title">{{item.title}}</span>
    </el-menu-item>
    <!--有子级-->
    <el-submenu :index="basePath" v-else>
        <template slot="title"><i :class="item.icon"></i>{{item.title}}</template>
        <NavItem v-for="sub in item.child" :key="sub.url" :item="sub" :basePath="sub.url"/>
    </el-submenu>
</template>

<script>
    export default {
        name: "NavItem",
        props: ['item','basePath']
    }
</script>

<style type="scss" scoped>


</style>