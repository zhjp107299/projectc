<template>
    <div class="breadcrumb">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item v-for="v in lists" :key="v.path">
                <router-link class="bread-title" :to="v.path">{{v.meta.title}}</router-link>
            </el-breadcrumb-item>
        </el-breadcrumb>
    </div>
</template>

<script>
    export default {
        name: "BreadcrumbS",
        data() {
            return {
                lists:[]
            }
        },
        watch: {
          $route(to,from){
                this.getBreadcrumb(to.matched);//监听路由变化，lists才能跟随变化
            }
        },
        methods: {
            getBreadcrumb(matched){
                if (matched.length && matched[1].name==='hs_index'){
                    matched = [{path:'/home_student',name:'home_student',meta:{title:'首页'}}];
                }
                this.lists = matched;
            }
        },
        mounted() {
            this.getBreadcrumb(this.$route.matched);
        }
    }
</script>

<style scoped>
    .breadcrumb {
        float: left;
    }
    .bread-title {
        line-height: 60px;
    }
</style>