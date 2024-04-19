<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom,
    ShoppingCart,
    Document,
    Search
} from '@element-plus/icons-vue'
import {ref} from 'vue'
const searchString = ref('')
import avatar from '@/assets/default.png'
import { useRouter } from 'vue-router'
const router = useRouter()
import { ElMessage, ElMessageBox } from 'element-plus';

//account info
import useAccountInfoStore from '@/stores/accountInfo.js'
import { getAccountInfoService } from '@/api/account';
const accountInfoStore = useAccountInfoStore();

import { useTokenStore } from '@/stores/token';
const tokenStore = useTokenStore()
const hadnleCommand = (command) => {
    if (command === 'logout') {
        //退出登录
        ElMessageBox.confirm(
            'Are you sure you want to log out',
            'Tips',
            {
                confirmButtonText: 'confirm',
                cancelButtonText: 'cancel',
                type: 'warning',
            })
            .then(async () => {
                //退出登录
                //1.清空pinia中存储的token以及个人信息
                tokenStore.removeToken()
                accountInfoStore.removeInfo()

                //2.跳转到登录页面
                router.push('/login')
                ElMessage({
                    type: 'success',
                    message: 'log out successfully',
                })

            })
            .catch(() => {
            })
    } else {
        router.push('/account/' + command)
    }
}

import {getAllProductNamesService} from '@/api/catalog.js'
//use ref to auto complete
// const autoComplete = ref([])
// const getAllProductNames = async()=>{
//     autoComplete.value = await getAllProductNamesService()
//     console.log(autoComplete.value)
// }
// getAllProductNames()

const searchItem = ()=>{
    router.push('/pet/search/'+searchString.value)
}

const querySearch = async(queryString)=>{
    //TODO: empty string still load for nothing
    try{
        let autoComplete = await getAllProductNamesService()
        // console.log(autoComplete)
        const transformedData = autoComplete.data.map(ele => {
            return { value: ele }; // Create an object with 'value' property initialized to an empty string
        });
        // console.log(transformedData)
        const normalizedQuery = queryString.toLowerCase(); // Normalize input to lowercase
        return queryString ? transformedData.filter(ele =>
            ele.value.toLowerCase().includes(normalizedQuery) // Check for case-insensitive match
        ) : queryString
    }catch{
    }
}
</script>

<template>
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="300px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="orange" background-color="#fff" text-color="#233233" router>
                <el-menu-item index="/pet/category" :default-active="1">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span class="menu-text">pets category</span>
                </el-menu-item>
                <el-menu-item index="/cart">
                    <el-icon>
                        <ShoppingCart />
                    </el-icon>
                    <span class="menu-text">shopping cart</span>
                </el-menu-item>
                <el-menu-item index="/order">
                    <el-icon>
                        <Document />
                    </el-icon>
                    <span class="menu-text">order details</span>
                </el-menu-item>
                <el-sub-menu v-if="accountInfoStore.info.username">
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span class="menu-text">account center</span>
                    </template>
                    <el-menu-item index="/account/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span class="menu-text">information</span>
                    </el-menu-item>
                    <el-menu-item index="/account/resetPassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span class="menu-text">reset password</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header >
                <div style="display: flex;justify-content: space-between;">
                    <!-- <el-input placeholder="enter product name" v-model="searchString" style="max-width: 600px"></el-input> -->
                    <el-autocomplete v-model="searchString" :fetch-suggestions="querySearch" placeholder="Please input"/>
                    <el-button :icon="Search" @click="searchItem()">
                    </el-button>
                </div>
                <div class="menu-text">{{ accountInfoStore.info.username ? 'user:' + accountInfoStore.info.username : ''}}</div>
                <el-dropdown placement="bottom-end" @command="hadnleCommand" v-if="accountInfoStore.info.username">
                    <span class="el-dropdown__box">
                        <el-avatar :src="avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu >
                            <el-dropdown-item command="info" :icon="User" >basic info</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen" >reset password</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton" >log out</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
                <el-button @click="router.push('/login')" v-if="!accountInfoStore.info.username">Log in</el-button>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <router-view></router-view>
            </el-main>
            <el-footer>mypetstore ©2024 Created by csu</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;
    .menu-text{
        font-size: 30px;
    }

    .el-aside {
        background-color: #fff;

        &__logo {
            height: 120px;
            background: url('@/assets/image.png') no-repeat center / 120px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 30px;
        color: #666;
    }
}
</style>