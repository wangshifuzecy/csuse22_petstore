import request from '@/utils/request.js'

export const accountRegisterService = (registerData)=>{
    //借助于UrlSearchParams完成传递
    const params = new URLSearchParams()
    for(let key in registerData){
        params.append(key,registerData[key]);
    }
    return request.post('/account/register',params);
}

//提供调用登录接口的函数
export const accountLoginService = (loginData)=>{
    const params = new URLSearchParams();
    for(let key in loginData){
        params.append(key,loginData[key])
    }
    return request.post('/account/login',params)
}

export const getAccountInfoService = ()=>{
    return request.get('/account/info')
}

export const updateAccountInfoService = (newInfo)=>{
    return request.post('/account/info',newInfo)
}

export const updateAccountPasswordService = (password)=>{
    return request.post('/account/password',password)
}
