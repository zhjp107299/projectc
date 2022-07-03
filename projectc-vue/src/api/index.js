import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8082';
axios.withCredentials = true;//添加...许可
//添加拦截请求器
//在发送请求之前做些什么
axios.interceptors.request.use(config=>{
    //把token添加到请求头中
    config.headers.Authorization = localStorage.getStorageWithAge('token');
    console.log(localStorage.getStorageWithAge('token'));
    //返回config
    return config;
});
//添加响应拦截器
axios.interceptors.response.use(response=>{
    //对响应数据做点什么
    return response;
},err=>{
    //对响应错误做点什么
    return Promise.reject(err);
});

export default axios;