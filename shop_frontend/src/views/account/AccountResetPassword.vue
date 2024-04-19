<script setup>
import { ref } from 'vue'
import useAccountInfoStore from '@/stores/accountInfo.js'
const accountInfoStore = useAccountInfoStore()
const passwords = ref({
    originPassword: '',
    password: '',
    rePassword: ''
})

//check password 
const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('please check password'))
    } else if (value !== passwords.value.password) {
        callback(new Error('please keep two password same'))
    } else {
        callback()
    }
}

const rules = {
    originPassword: [{ required: true, message: 'pleaze enter origin password', trigger: 'blur' }],
    password: [{ required: true, message: 'pleaze enter new password', trigger: 'blur' }],
    rePassword: [
        {validator: checkRePassword, trigger: 'blur' }
    ]
}

//修改个人信息
import {updateAccountPasswordService,accountLoginService} from '@/api/account.js'
import {ElMessage} from 'element-plus'
const login = async (username,password)=>{
    try{
        let account = {
            username : username,
            password : password
        }
        return await accountLoginService(account)
    }catch{
    }
}
const updateAccountPassword = async ()=>{
    if(passwords.value.originPassword && passwords.value.password && passwords.value.rePassword){
        try{
            let check = await login(accountInfoStore.info.username, passwords.value.originPassword) 
            if(check.status === 0){
                let result = await updateAccountPasswordService(passwords.value.password);
                ElMessage.success(result.msg? result.msg : 'update password success');
            }
        }catch{
        }
    }else{
        ElMessage.error('pleaze enter all passwords');
    }
    //调用接口
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>basic info</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="passwords" :rules="rules" label-width="200px" size="large">
                    <el-form-item label="Origin password" prop="originPassword">
                        <el-input type="password" v-model="passwords.originPassword" ></el-input>
                    </el-form-item>
                    <el-form-item label="New password" prop="password" >
                        <el-input type="password" v-model="passwords.password"></el-input>
                    </el-form-item>
                    <el-form-item label="Confirm new password " prop="rePassword" >
                        <el-input type="password" v-model="passwords.rePassword"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateAccountPassword">update password</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>