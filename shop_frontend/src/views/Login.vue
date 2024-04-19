<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
const  registerData = ref({
  username:'',
  password:'',
  rePassword:''
})

//check password 
const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('please check password'))
    } else if (value !== registerData.value.password) {
        callback(new Error('please keep two password same'))
    } else {
        callback()
    }
}

//定义函数,清空数据模型的数据
const clearRegisterData = ()=>{
    registerData.value={
        username:'',
        password:'',
        rePassword:''
    }
}

//form input check
const rules={
  username:[
    {required:true, message:'please enter username', trigger:'blur'},
    {min:1, max:15, message: 'length should be 1-15', trigger:'blur'}
  ],
  password:[
    {required:true, message:'please enter password', trigger:'blur'},
    {min:1, max:15, message: 'length should be 1-15', trigger:'blur'}
  ],
  rePassword:[
    {validator: checkRePassword, trigger: 'blur' }
  ]
}

import {useTokenStore} from '@/stores/token.js'
import {useRouter} from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore()
import {accountRegisterService,accountLoginService } from '@/api/account.js'
//register api use
const register = async ()=>{
  try{
    let result = await accountRegisterService(registerData.value)
    ElMessage.success('register success')
  }catch{
  }
}
//login api
const login = async ()=>{
  try{
    let result = await accountLoginService(registerData.value)
    ElMessage.success('login success')
    tokenStore.setToken(result.data)
    getAccountInfo()
    router.push('/')
  }catch{
  }
}

import useAccountInfoStore from '@/stores/accountInfo.js'
import { getAccountInfoService } from '@/api/account';
const getAccountInfo = async () => {
    try{
        const accountInfoStore = useAccountInfoStore();
        let result = await getAccountInfoService();
        console.log(result)
        accountInfoStore.setInfo(result.data);
    }catch(error){
    }
}
</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 注册表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
        <el-form-item>
          <h1>Register</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="please enter username" v-model="registerData.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" type="password" placeholder="please enter password" v-model="registerData.password"></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input :prefix-icon="Lock" type="password" placeholder="please enter password again" v-model="registerData.rePassword"></el-input>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="register">
            register
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info"  style="color:blue" @click="isRegister = false;clearRegisterData()">
            ← return
          </el-link>
        </el-form-item>
      </el-form>
      <!-- 登录表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules">
        <el-form-item>
          <h1>Login</h1>
        </el-form-item>
        <el-form-item>
          <el-input :prefix-icon="User" placeholder="please enter username" v-model="registerData.username"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input name="password" :prefix-icon="Lock" type="password" placeholder="please enter password"  v-model="registerData.password"></el-input>
        </el-form-item>
        <!-- <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>remember me</el-checkbox>
            <el-link type="primary" :underline="false">forget password?</el-link>
          </div>
        </el-form-item> -->
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="login">login</el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" @click="isRegister = true;clearRegisterData()" style="color:blue;text-decoration: underline;" >
            register →
          </el-link>
        </el-form-item>
        <el-from-item>
          <el-link type="info"  @click="router.push('/')" style="color:blue;text-decoration: underline;">
            take a look without account
          </el-link>
        </el-from-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: 
    // url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
      url('@/assets/login_bg.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>