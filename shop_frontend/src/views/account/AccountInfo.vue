<script setup>
import { ref } from 'vue'
import useAccountInfoStore from '@/stores/accountInfo.js'
const accountInfoStore = useAccountInfoStore();

const accountInfo = ref({...accountInfoStore.info})

const rules = {
    email: [
        { required: true, message: 'pleaze enter email', trigger: 'blur' },
        { type: 'email', message: 'pleaze enter valid email', trigger: 'blur' }
    ],
    firstName: [{ required: true, message: 'pleaze enter first name', trigger: 'blur' }],
    lastName: [{ required: true, message: 'pleaze enter last name', trigger: 'blur' }]
}

//修改个人信息
import {updateAccountInfoService} from '@/api/account.js'
import {ElMessage} from 'element-plus'
const updateAccountInfo = async ()=>{
    try{
        //调用接口
        let result = await updateAccountInfoService(accountInfo.value);
        ElMessage.success(result.msg? result.msg : 'update information success');
        
        //修改pinia中的个人信息
        accountInfoStore.setInfo(accountInfo.value)
    }catch{
    }
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
                <el-form :model="accountInfo" :rules="rules" label-width="120px" size="large">
                    <el-form-item label="User ID">
                        <el-input v-model="accountInfo.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="Email" prop="email">
                        <el-input v-model="accountInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item label="First Name" prop="firstName">
                        <el-input v-model="accountInfo.firstName"></el-input>
                    </el-form-item>
                    <el-form-item label="Last Name" prop="lastName">
                        <el-input v-model="accountInfo.lastName"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateAccountInfo">update information</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>