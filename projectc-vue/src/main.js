import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import qs from 'querystring';

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import 'echarts';
import ECharts from 'vue-echarts';

import global from './global/Global'

// 全局注册组件（也可以使用局部注册）
Vue.prototype.$global=global;
Vue.component('e-chart', ECharts);
Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(qs);
Vue.prototype.$qs = qs;

//扩展Storage原型方法
Storage.prototype.setStorageWithAge = (key,value,age)=>{
  const obj = {
    data: value,
    time: Date.now(),
    maxAge: age,
  };
  localStorage.setItem(key,JSON.stringify(obj));
};
Storage.prototype.getStorageWithAge = key=>{
  if (JSON.parse(localStorage.getItem(key))===null){
    return undefined;
  }
  const {data,time,maxAge} = JSON.parse(localStorage.getItem(key));
  if (time + maxAge < Date.now()){
    localStorage.removeItem(key);
    localStorage.removeItem('role');
    localStorage.removeItem('loginUser');
    return undefined;
  }
  return data;
};

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
