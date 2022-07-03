import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/login/Login";
import global_passSwitch from '../global/Global'
import {nextDate} from "element-ui/src/utils/date-util";



Vue.use(VueRouter);


const routes = [
  {
    path: '/',
    redirect: '/login' //重定向
  },
  {
    name: 'login',
    path: '/login',  //登录页
    component: Login
  },
  {
    name: 'register',
    path: '/register',
    component: () => import('../views/login/StudentRegister'),
    meta:{title: '学生注册页'}

  },
    //答题模块
    //自主命题
  {
    name: 'indepropo_test',
    path: '/hs/indepropo_test',
    meta: {title: '自主命题测试'},
    component: ()=>import('../components/test-indepropo/IndePropoTest')
  },
    //模拟C测试页
  {
    name: 'c_simple',
    path: '/hs/test_c/c_simple',
    meta: {title: '模拟C等级简单'},
    component: ()=>import('../components/test-c/CCommon')
  },
  {
    name: 'c_middle',
    path: '/hs/test_c/c_middle',
    meta: {title: '模拟C等级中等'},
    component: ()=>import('../components/test-c/CCommon')
  },
  {
    name: 'c_hard',
    path: '/hs/test_c/c_hard',
    meta: {title: '模拟C等级困难'},
    component: ()=>import('../components/test-c/CCommon')
  },
    //题库练习模块
  {
    name: 'choice_10',
    path: '/hs/test_exercise/bank_choice/choice_10',
    meta: {title: '选择题_10'},
    component: ()=> import('../components/test-exercise/bank-choice/BankChoice_10')
  },
  {
    name: 'choice_25',
    path: '/hs/test_exercise/bank_choice/choice_25',
    meta: {title: '选择题_25'},
    component: ()=> import('../components/test-exercise/bank-choice/BankChoice_25')
  },
  {
    name: 'choice_50',
    path: '/hs/test_exercise/bank_choice/choice_50',
    meta: {title: '选择题_50'},
    component: ()=> import('../components/test-exercise/bank-choice/BankChoice_50')
  },
  {
    name: 'gapfilling_10',
    path: '/hs/test_exercise/bank_gapfilling/gapfilling_10',
    meta: {title: '填空题_10'},
    component: ()=> import('../components/test-exercise/bank-gapfilling/BankGapFilling_10')
  },
  {
    name: 'gapfilling_10',
    path: '/hs/test_exercise/bank_gapfillinggapfilling_25',
    meta: {title: '填空题_25'},
    component: ()=> import('../components/test-exercise/bank-gapfilling/BankGapFilling_25')
  },
  {
    name: 'gapfilling_10',
    path: '/hs/test_exercise/bank_gapfilling/gapfilling_50',
    meta: {title: '填空题_50'},
    component: ()=> import('../components/test-exercise/bank-gapfilling/BankGapFilling_50')
  },
  {
    name: 'judge_10',
    path: '/hs/test_exercise/bank_judge/judge_10',
    meta: {title: '判断题_10'},
    component: ()=> import('../components/test-exercise/bank-judge/BankJudge_10')
  },
  {
    name: 'judge_25',
    path: '/hs/test_exercise/bank_judge/judge_25',
    meta: {title: '判断题_25'},
    component: ()=> import('../components/test-exercise/bank-judge/BankJudge_25')
  },
  {
    name: 'judge_50',
    path: '/hs/test_exercise/bank_judge/judge_50',
    meta: {title: '判断题_50'},
    component: ()=> import('../components/test-exercise/bank-judge/BankJudge_50')
  },
  {
    name: 'programme_5',
    path: '/hs/test_exercise/bank_programme/programme_5',
    meta: {title: '编程题_5'},
    component: ()=> import('../components/test-exercise/bank-programme/BankProgramme_5')
  },
  {
    name: 'programme_10',
    path: '/hs/test_exercise/bank_programme/programme_10',
    meta: {title: '编程题_10'},
    component: ()=> import('../components/test-exercise/bank-programme/BankProgramme_10')
  },
  {
    name: 'programme_20',
    path: '/hs/test_exercise/bank_programme/programme_20',
    meta: {title: '编程题_20'},
    component: ()=> import('../components/test-exercise/bank-programme/BankProgramme_20')
  },
    //章节测试
  {
    name: 'chapter1',
    path: '/hs/test_chapter/chapter1',
    meta: {title: '章节1测试'},
    component: ()=>import('../components/test-chapter/Chapter1')
  },
  {
    name: 'chapter2',
    path: '/hs/test_chapter/chapter2',
    meta: {title: '章节2测试'},
    component: ()=>import('../components/test-chapter/Chapter2')
  },
  {
    name: 'chapter3',
    path: '/hs/test_chapter/chapter3',
    meta: {title: '章节3测试'},
    component: ()=>import('../components/test-chapter/Chapter3')
  },
  {
    name: 'chapter4',
    path: '/hs/test_chapter/chapter4',
    meta: {title: '章节4测试'},
    component: ()=>import('../components/test-chapter/Chapter4')
  },
  {
    name: 'chapter5',
    path: '/hs/test_chapter/chapter5',
    meta: {title: '章节5测试'},
    component: ()=>import('../components/test-chapter/Chapter5')
  },
  {
    name: 'chapter6',
    path: '/hs/test_chapter/chapter6',
    meta: {title: '章节6测试'},
    component: ()=>import('../components/test-chapter/Chapter6')
  },
  {
    name: 'chapter7',
    path: '/hs/test_chapter/chapter7',
    meta: {title: '章节7测试'},
    component: ()=>import('../components/test-chapter/Chapter7')
  },
  {
    name: 'chapter8',
    path: '/hs/test_chapter/chapter8',
    meta: {title: '章节8测试'},
    component: ()=>import('../components/test-chapter/Chapter8')
  },
  {
    name: 'chapter9',
    path: '/hs/test_chapter/chapter9',
    meta: {title: '章节9测试'},
    component: ()=>import('../components/test-chapter/Chapter9')
  },
  { //学生模块路由
    name: 'home_student',
    path: '/home_student',
    meta: {title:'首页'},
    component: () => import('../views/home_student/index.vue'),//作为布局
    redirect: '/hs/index',
    children:[
      {
        name: 'score_query',
        path: '/hs/score_query',
        meta: {title: '成绩查看页'},
        component: ()=>import('../views/home_student/score_query/index')
      },
      {
        name: 'indepropo_query',
        path: '/hs/indepropo_query',
        meta: {title: '自主命题查看页'},
        component: ()=>import('../views/home_student/test-indepropo/index')
      },
      {
        name: 'page-intermediate-indeproop',
        path: '/hs/intermediate_indepropo',
        meta: {title: '自主命题测试说明'},
        component: () => import('../page-for/IntermediateIndepropo')
      },
      {
        name: 'page-intermediate-c',
        path: '/hs/intermediate_c',
        meta: {title: '模拟C测试说明'},
        component: () => import('../page-for/IntermediateC')
      },
      {
        name: 'page-intermediate-bank',
        path: '/hs/intermediate_bank',
        meta: {title: '题库练习说明'},
        component: () => import('../page-for/IntermediateBank')
      },
      {
        name: 'page-intermediate-chapter',
        path: '/hs/intermediate_chapter',
        meta: {title: '章节测试说明'},
        component: () => import('../page-for/intermediate')
      },
      {
        name: 'page-scored-indepropo',
        path: '/hs/scored_indepropo',
        meta: {title: '自主命题成绩评分页'},
        component: () => import('../page-for/ScoredPageIndepropo')
      },
      {
        name: 'page-scored-c',
        path: '/hs/scored_c',
        meta: {title: '模拟C成绩评分页'},
        component: () => import('../page-for/ScoredPageC')
      },
      {
        name: 'page-scored-bank',
        path: '/hs/scored_bank',
        meta: {title: '题库练习成绩评分页'},
        component: () => import('../page-for/ScoredPageBank')
      },
      {
        name: 'page-scored-chapter',
        path: '/hs/scored',
        meta: {title: '章节测试成绩评分页'},
        component: () => import('../page-for/ScoredPage')
      },
      {
        name: 'set-personals',
        path: '/hs/set',
        meta: {title: '个人信息查看/修改页'},
        component: ()=> import('../views/home_student/set-personal/index')
      },
      {
        name: 'hs_index',
        path: '/hs/index',  //首页
        meta: {title:'首页'},
        component: ()=> import('../views/home_student/index/index')
      },
      {
        name: 'hs_chapter',
        path: '/hs/test_chapter',  //章节练习页
        meta: {title: '章节测试页'},
        component: () => import('../views/home_student/test-chapter/index'),
        children: [

        ]
      },
      {
        name: 'hs_exercise',
        path: '/hs/test_exercise',  //题库练习页
        meta: {title: '题库练习页'},
        component: () => import('../views/home_student/test-exercise/index'),
        children:[
          {
            name: 'bank_choice',
            path: '/hs/test_exercise/bank_choice',
            meta: {title: '选择题库练习页'},
            component: ()=>import('../views/home_student/test-exercise/index'),
            children: [

            ]
          },
          {
            name: 'bank_gap_filling',
            path: '/hs/test_exercise/bank_gap_filling',
            meta: {title: '填空题库练习页'},
            component: ()=>import('../views/home_student/test-exercise/index'),
            children: [

            ]
          },
          {
            name: 'bank_judge',
            path: '/hs/test_exercise/bank_judge',
            meta: {title: '判断题库练习页'},
            component: ()=>import('../views/home_student/test-exercise/index'),
            children: [

            ]
          },
          {
            name: 'bank_programme',
            path: '/hs/test_exercise/bank_programme',
            meta: {title: '编程题库练习页'},
            component: ()=>import('../views/home_student/test-exercise/index'),
            children: [

            ]
          },
        ]
      },
      {
        name: 'hs_c',
        path: '/hs/test_c',  //模拟C页
        meta: {title: '模拟C测试页'},
        component: () => import('../views/home_student/test-c/index'),
        children:[

        ]
      },
    ]
  },
  { //admin模块路由
    name: 'admin',
    path: '/admin',
    meta: {title: '首页'},
    component: () => import('../views/admin/index'),//作为布局
    redirect: '/admin/index',
    children: [
      {
        name: 'set_personal_admin',
        path: '/admin/set',
        meta: {title: '个人信息查看/修改页'},
        component: ()=> import('../views/admin/set-personal/index')
      },
      {
        name: 'admin_index',
        path: '/admin/index',  //首页
        meta: {title:'首页'},
        component: () => import('../views/admin/index/index')
      },
      {
        name: 'admin_add',
        path: '/admin/admin_add',
        meta: {title: '管理员添加页'},
        component: () => import('../views/admin/AllTeachers/AdminAdd'),

      },
      {
        name: 'teacher_add',
        path: '/admin/teacher_add',
        component: () => import('../views/admin/AllTeachers/TeacherAdd'),
        meta: {title: '教师添加页'},
      },
      {
        name: 'all_teachers',
        path: '/admin/all_teachers',  //
        meta: {title:'查看所有教师'},
        component: () => import('../views/admin/AllTeachers/index'),
      },
      {
        name: 'all_students',
        path: '/admin/all_students',  //
        meta: {title:'查看所有学生'},
        component: () => import('../views/admin/AllStudents/index')
      },
    ]
  },
  { //教师模块路由
    name: 'home_teacher',
    path: '/home_teacher',
    meta: {title:'首页'},
    component: ()=>import('../views/home_teacher/index.vue'),//作为布局
    redirect: '/ht/index',
    children: [
      {
        name: 'set_personal_teacher',
        path: '/ht/set',
        meta: {title: '个人信息查看/修改页'},
        component: ()=> import('../views/home_teacher/set-personal/index')
      },
      {
        name: 'ht_index',
        path: '/ht/index',  //首页
        meta: {title:'首页'},
        component: () => import('../views/home_teacher/index/index')
      },
      {
        name: 'ht_independent_proposition',
        path: '/ht/indepropo',   //自主命题页
        meta: {title:'自主命题页'},
        component: () => import('../views/home_teacher/indepropo/index'),
      },
      {
        name: 'indepropoQuery',
        path: '/ht/indepropo/query',
        component: () => import('../views/home_teacher/indepropo/IndePropoQuery'),
        meta: {title: '自主命题查看页'}
      },
      {
        name: 'indepropoAdd',
        path: '/ht/indepropo/add',
        component: ()=>import('../views/home_teacher/indepropo/ImportPropo'),
        meta: {title: '自主命题添加页'},
      },
      {
        name: 'ht_excel_import',
        path: '/ht/excelimpo',   //Excel题库导入页
        meta: {title:'Excel题库导入页'},
        component: () => import('../views/home_teacher/excel-import/index'),
        redirect: '/excelimpo/choice_bank',
        children: [{
          name: 'impo_choice_bank',
          path: '/ht/excelimpo/choice_bank',   //选择题库导入页
          meta: {title:'Excel选择题库导入页'},
          component: ()=> import('../views/home_teacher/excel-import/ImportChoiceBank')
        },
          {
            name: 'impo_gapfilling_bank',
            path: '/ht/excelimpo/gapfilling_bank',   //填空题库导入页
            meta: {title:'Excel填空题库导入页'},
            component: ()=> import('../views/home_teacher/excel-import/ImportGapFillingBank')
        },
          {
            name: 'impo_judge_bank',
            path: '/ht/excelimpo/judge_bank',    //判断题库导入页
            meta: {title:'Excel判断题库导入页'},
            component: ()=> import('../views/home_teacher/excel-import/ImportJudgeBank')
          },{
            name: 'impo_programme_bank',
            path: '/ht/excelimpo/programme_bank',    //编程题库导入页
            meta: {title:'Excel编程题库导入页'},
            component: ()=> import('../views/home_teacher/excel-import/ImportProgrammeBank')
          }]
      },
      {
        name: 'ht_sstatus',
        path: '/ht/sstatus',   //查看学生情况页
        meta: {title: '查看学生情况页'},
        component: () => import('../views/home_teacher/sstatus/index'),
      },
      {
        name: 'add',
        path: '/ht/sstatus/add',
        meta: {title:'学生添加页'},
        component: () => import('../views/home_teacher/student-crud/StudentAdd')
      },
      {
            name: 'edit',
            path: '/ht/sstatus/edit',
            meta: {title:'学生信息查看/修改页'},
            component: () => import('../views/home_teacher/student-crud/StudentModify')
        },
      {
        name: 'query',
        path: '/ht/sstatus/query',
        meta: {title:'学生测试情况查看页'},
        component: () => import('../views/home_teacher/student-crud/StudentQuery')
      },
    ]
  },
];

const router = new VueRouter({
  routes
});

//全局router钩子函数
router.beforeEach((to,from,next)=>{
  //实现未登录前端拦截
    if (window.location.href.startsWith('http://localhost:8888/#/') && !window.location.href.startsWith('http://localhost:8888/#/login') && to.path!=='/login' && localStorage.getStorageWithAge('token')===undefined){
      alert("登录失效，请先登录！");
      next('/login');
    }else {
      next();
    }

    //实现不同角色之间的拦截
  if (((localStorage.getItem('role')==='student' || localStorage.getItem('role')==='teacher' || localStorage.getItem('role')==='tourist') && (window.location.href.startsWith('http://localhost:8888/#/admin')))){
    alert('无权限访问！');
    next(false);
  }else {
    next();
  }

    if ((localStorage.getItem('role')==='student' || localStorage.getItem('role')==='tourist') && (window.location.href.startsWith('http://localhost:8888/#/ht') || window.location.href.startsWith('http://localhost:8888/#/home_teacher'))){
      alert('无权限访问！');
      next(false);
    }else {
      next();
    }
  if ((localStorage.getItem('role')==='teacher' || localStorage.getItem('role')==='tourist') && (window.location.href.startsWith('http://localhost:8888/#/hs') || window.location.href.startsWith('http://localhost:8888/#/home_student'))){
    alert('无权限访问！');
    next(false);
  }else {
    next();
  }


//学生模块权限npm
  if (to.path.startsWith('/hs/intermediate_chapter') && JSON.parse(localStorage.getItem('loginUser')).sPermission<1){
    alert('暂无权限，请联系后台管理员！');
    next('/home_student');
  }else if(to.path.startsWith('/hs/intermediate_bank') && JSON.parse(localStorage.getItem('loginUser')).sPermission<2){
    alert('暂无权限，请联系后台管理员！');
    next('/home_student');
  }else if(to.path.startsWith('/hs/intermediate_c') && JSON.parse(localStorage.getItem('loginUser')).sPermission<3){
    alert('暂无权限，请联系后台管理员！');
    next('/home_student');
  }else if(to.path.startsWith('/hs/indepropo_query') && JSON.parse(localStorage.getItem('loginUser')).sPermission<4){
    alert('暂无权限，请联系后台管理员！');
    next('/home_student');
  }else {
    next();
  }

//教师模块权限
  if (to.path.startsWith('/ht/sstatus') && JSON.parse(localStorage.getItem('loginUser')).tPermission<1){
    alert('暂无权限，请联系后台管理员！');
    next('/home_teacher');
  }else if(to.path.startsWith('/ht/excelimpo') && JSON.parse(localStorage.getItem('loginUser')).tPermission<2){
    alert('暂无权限，请联系后台管理员！');
    next('/home_teacher');
  }else if(to.path.startsWith('/ht/indepropo') && JSON.parse(localStorage.getItem('loginUser')).tPermission<3){
    alert('暂无权限，请联系后台管理员！');
    next('/home_teacher');
  }else {
    next();
  }



  if ((to.path==='/hs/test_chapter/chapter1' || to.path==='/hs/test_chapter/chapter2') && from.path !== '/hs/intermediate_chapter'){
    next({
      path: '/hs/intermediate_chapter',        //章节测试截取页面
      query: {
        tag: to.meta.title,
      }
    });

  }else if((to.path==='/hs/test_chapter/chapter1' || to.path==='/hs/test_chapter/chapter2') && from.path === '/hs/intermediate_chapter'){
    if (from.query.tag!==to.meta.title){
      next({
        path: '/hs/intermediate_chapter',
        query: {
          tag: to.meta.title,
        }
      });
      location.reload();
    } else {
      if (global_passSwitch.passSwitch===false){
        console.log('1111:'+global_passSwitch.passSwitch);
        next({
          path: '/hs/intermediate_chapter',
          query: {
            tag: to.meta.title,
          }
        });
        }else {
        next();
      }
    }
  }else if((to.path==='/hs/test_exercise/bank_choice/choice_10' || to.path==='/hs/test_exercise/bank_choice/choice_25') && from.path !== '/hs/intermediate_bank'){
    next({
      path: '/hs/intermediate_bank',        //题库练习截取页面
      query: {
        tag: to.meta.title,
      }
    });
  }else if((to.path==='/hs/test_exercise/bank_choice/choice_10' || to.path==='/hs/test_exercise/bank_choice/choice_25')&& from.path === '/hs/intermediate_bank'){
    if (from.query.tag!==to.meta.title){
      next({
        path: '/hs/intermediate_bank',
        query: {
          tag: to.meta.title,
        }
      });
      location.reload();
    }else {
      if (global_passSwitch.passSwitch===false){
        console.log('1111:'+global_passSwitch.passSwitch);
        next({
          path: '/hs/intermediate_bank',
          query: {
            tag: to.meta.title,
          }
        });
      }else {
        next();
      }
    }
  }else if(to.path==='/hs/test_exercise/bank_gapfilling/gapfilling_10' && from.path !== '/hs/intermediate_bank'){
    next({
      path: '/hs/intermediate_bank',        //题库练习截取页面
      query: {
        tag: to.meta.title,
      }
    });
  }else if(to.path==='/hs/test_exercise/bank_gapfilling/gapfilling_10' && from.path === '/hs/intermediate_bank'){
    if (from.query.tag!==to.meta.title){
      next({
        path: '/hs/intermediate_bank',
        query: {
          tag: to.meta.title,
        }
      });
      location.reload();
    }else {
      if (global_passSwitch.passSwitch===false){
        console.log('1111:'+global_passSwitch.passSwitch);
        next({
          path: '/hs/intermediate_bank',
          query: {
            tag: to.meta.title,
          }
        });
      }else {
        next();
      }
    }

  }else if(to.path==='/hs/test_exercise/bank_judge/judge_10' && from.path !== '/hs/intermediate_bank'){
    next({
      path: '/hs/intermediate_bank',        //题库练习截取页面
      query: {
        tag: to.meta.title,
      }
    });
  }else if(to.path==='/hs/test_exercise/bank_judge/judge_10' && from.path === '/hs/intermediate_bank'){
    if (from.query.tag!==to.meta.title){
      next({
        path: '/hs/intermediate_bank',
        query: {
          tag: to.meta.title,
        }
      });
      location.reload();
    }else {
      if (global_passSwitch.passSwitch===false){
        console.log('1111:'+global_passSwitch.passSwitch);
        next({
          path: '/hs/intermediate_bank',
          query: {
            tag: to.meta.title,
          }
        });
      }else {
        next();
      }
    }
  }else if(to.path==='/hs/test_exercise/bank_programme/programme_5' && from.path !== '/hs/intermediate_bank'){
    next({
      path: '/hs/intermediate_bank',        //题库练习截取页面
      query: {
        tag: to.meta.title,
      }
    });
  }else if(to.path==='/hs/test_exercise/bank_programme/programme_5' && from.path === '/hs/intermediate_bank'){
    if (from.query.tag!==to.meta.title){
      next({
        path: '/hs/intermediate_bank',
        query: {
          tag: to.meta.title,
        }
      });
      location.reload();
    }else {
      if (global_passSwitch.passSwitch===false){
        console.log('1111:'+global_passSwitch.passSwitch);
        next({
          path: '/hs/intermediate_bank',
          query: {
            tag: to.meta.title,
          }
        });
      }else {
        next();
      }
    }
  }else if((to.path==='/hs/test_c/c_simple' || to.path==='/hs/test_c/c_middle' || to.path==='/hs/test_c/c_hard') && from.path !== '/hs/intermediate_c'){
    next({
      path: '/hs/intermediate_c',        //c测试截取页面
      query: {
        tag: to.meta.title,
      }
    });
  }else if((to.path==='/hs/test_c/c_simple' || to.path==='/hs/test_c/c_middle' || to.path==='/hs/test_c/c_hard') && from.path === '/hs/intermediate_c'){
    if (from.query.tag!==to.meta.title){
      next({
        path: '/hs/intermediate_c',
        query: {
          tag: to.meta.title,
        }
      });
      location.reload();
    }else {
      if (global_passSwitch.passSwitch===false){
        console.log('1111:'+global_passSwitch.passSwitch);
        next({
          path: '/hs/intermediate_c',
          query: {
            tag: to.meta.title,
          }
        });
      }else {
        next();
      }
    }
  }else if(to.path==='/hs/indepropo_test' && from.path !== '/hs/intermediate_indepropo'){
    next({
      path: '/hs/intermediate_indepropo',        //自主命题测试截取页面
      query: {
        tag: to.meta.title,
      }
    });
  }else if(to.path==='/hs/indepropo_test' && from.path === '/hs/intermediate_indepropo'){
    if (from.query.tag!==to.meta.title){
      next({
        path: '/hs/intermediate_indepropo',
        query: {
          tag: to.meta.title,
        }
      });
      location.reload();
    }else {
      if (global_passSwitch.passSwitch===false){
        console.log('1111:'+global_passSwitch.passSwitch);
        next({
          path: '/hs/intermediate_indepropo',
          query: {
            tag: to.meta.title,
          }
        });
      }else {
        next();
      }
    }
  } else {
    next();
  }
});



export default router
