<template>
    <div>
        <div class="aside">
        <el-aside width="260px">
            <el-menu :default-active="$route.path" exact
                     class="el-menu-vertical-demo" router
                     style="width: 100%;"
                     @open="handleOpen"
                     @close="handleClose"
                     @select="handleSelect"
                     background-color="#545c64"
                     text-color="#fff"
                     active-text-color="#33ccff">
                <NavItemS v-for="v in items" :key="v.title" :item="v" :basePath="v.url"/>

            </el-menu>
        </el-aside>
        </div>
    </div>
</template>

<script>
    import NavItemS from "./NavItemS";

    export default {
        name: "NavMenuS",
        components: {NavItemS},
        data() {
            return {
                items: [
                    {title:'首页',url:'/hs/index',icon:'el-icon-s-home'},
                    {
                        title:'章节测试',
                        url:'/hs/test_chapter',
                        icon: 'el-icon-edit',
                        child: [
                            {
                                title: '章节1测试',
                                url: '/hs/test_chapter/chapter1',
                                icon: 'el-icon-edit-outline',
                            },
                            {
                                title: '章节2测试',
                                url: '/hs/test_chapter/chapter2',
                                icon: 'el-icon-edit-outline',
                            },
                            {
                                title: '章节3测试',
                                url: '/hs/test_chapter/chapter3',
                                icon: 'el-icon-edit-outline',
                            },
                            {
                                title: '章节4测试',
                                url: '/hs/test_chapter/chapter4',
                                icon: 'el-icon-edit-outline',
                            },
                            {
                                title: '章节5测试',
                                url: '/hs/test_chapter/chapter5',
                                icon: 'el-icon-edit-outline',
                            },
                            {
                                title: '章节6测试',
                                url: '/hs/test_chapter/chapter6',
                                icon: 'el-icon-edit-outline',
                            },
                            {
                                title: '章节7测试',
                                url: '/hs/test_chapter/chapter7',
                                icon: 'el-icon-edit-outline',
                            },
                            {
                                title: '章节8测试',
                                url: '/hs/test_chapter/chapter8',
                                icon: 'el-icon-edit-outline',
                            },
                            {
                                title: '章节9测试',
                                url: '/hs/test_chapter/chapter9',
                                icon: 'el-icon-edit-outline',
                            },
                        ]
                    },
                    {
                        title:'题库练习',
                        url:'/hs/test_exercise',
                        icon: 'el-icon-cloudy-and-sunny',

                        child: [
                            {
                                title: '选择题库练习',
                                icon: 'el-icon-cloudy',
                                url: '/hs/test_exercise/bank_choice',
                                child: [
                                    {
                                        title: '选择题练习-10题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_choice/choice_10'
                                    },
                                    {
                                        title: '选择题练习-25题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_choice/choice_25'
                                    },
                                    {
                                        title: '选择题练习-50题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_choice/choice_50'
                                    },
                                ]
                            },
                            {
                                title: '填空题库练习',
                                icon: 'el-icon-cloudy',
                                url: '/hs/test_exercise/bank_gap_filling',
                                child: [
                                    {
                                        title: '填空题练习-10题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_gapfilling/gapfilling_10'
                                    },
                                    {
                                        title: '填空题练习-25题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_gapfilling/gapfilling_25'
                                    },
                                    {
                                        title: '填空题练习-50题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_gapfilling/gapfilling_50'
                                    },
                                ]
                            },
                            {
                                title: '判断题库练习',
                                icon: 'el-icon-cloudy',
                                url: '/hs/test_exercise/bank_judge',
                                child: [
                                    {
                                        title: '判断题练习-10题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_judge/judge_10'
                                    },
                                    {
                                        title: '判断题练习-25题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_judge/judge_25'
                                    },
                                    {
                                        title: '判断题练习-50题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_judge/judge_50'
                                    },
                                ]
                            },
                            {
                                title: '编程题库练习',
                                icon: 'el-icon-cloudy',
                                url: '/hs/test_exercise/bank_programme',
                                child: [
                                    {
                                        title: '编程题练习-5题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_programme/programme_5'
                                    },
                                    {
                                        title: '编程题练习-10题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_programme/programme_10'
                                    },
                                    {
                                        title: '编程题练习-20题' ,
                                        icon: 'el-icon-partly-cloudy',
                                        url: '/hs/test_exercise/bank_programme/programme_20'
                                    },
                                ]
                            },
                        ]

                    },
                    {
                        title:'模拟二级C',
                        icon: 'el-icon-document',
                        url:'/hs/test_c',
                        child:[{
                            title:'简单',
                            icon: 'el-icon-tickets',
                            url:'/hs/test_c/c_simple'
                        },
                        {
                            title:'中等',
                            icon: 'el-icon-tickets',
                            url:'/hs/test_c/c_middle'
                        },
                        {
                            title:'困难',
                            icon: 'el-icon-tickets',
                            url:'/hs/test_c/c_hard'
                        },
                        ]
                    },
                    {title:'自主命题测试',url:'/hs/indepropo_query',icon:'el-icon-user'},
                    {title:'查看成绩',url:'/hs/score_query',icon:'el-icon-view'},
                ]
            }
        },
        methods: {
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            },
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        },
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
