//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus'
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})

import {useTokenStore} from '@/stores/token.js'
import router from '@/router'
//添加请求拦截器
instance.interceptors.request.use(
    (config)=>{
        //请求前的回调
        //添加token
        const tokenStore = useTokenStore();
        //判断有没有token
        if(tokenStore.token){
            config.headers.Authorization = tokenStore.token
        }
        return config;
    },
    (err)=>{
        //请求错误的回调
        Promise.reject(err)
    }
)

//添加响应拦截器
instance.interceptors.response.use(
    result=>{
        // http status code: 2xx
        if(result.data.status===0){
            return result.data;
        }
        else{
            ElMessage.error(result.data.msg || 'service error')
            return Promise.reject(result.data)
        }
    },
    err=>{
        // console.log(err.message);
        // console.log(err.message==='Request failed with status code 401');
        if(err.message==='Request failed with status code 401'){
            // console.log('hello')
            ElMessage.warning('please log in')
            router.push('/login')
        }else{
            ElMessage.error('service error')
        }
        // not 2xx
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;