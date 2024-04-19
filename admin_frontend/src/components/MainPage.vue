<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <TopBanner :username=currentUserInfo.username />
      </el-header>

      <el-container>
        <el-aside width="200px">
          <!-- 侧边栏导航 -->
          <el-row class="tac">
            <el-col :span="20">
              <h1 class="mb-2">宠物商店</h1>
              <el-menu
                default-active="1-userManage"
                class="el-menu-vertical-demo"
                active-text-color="#ffd04b"
                background-color="#545c64"
                text-color="#fff"
                :unique-opened="true"
              >
              <!-- 用户 -->
                <el-sub-menu index="1">
                  <template #title>
                    <el-icon><Avatar /></el-icon>
                    <span>用户管理</span>
                  </template>
                  <el-menu-item-group title="Group One">
                    <el-menu-item index="1-userManage">
                      <!-- 在页面中写router-link的to路径时需要加上父路径 -->
                      <router-link to="/mainPage/userManage">
                        个人信息
                      </router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group title="Group Two">
                    <el-menu-item index="1-addUser" :disabled="currentUserInfo.admin==true">
                      <router-link to="/mainPage/userList">
                        用户列表
                      </router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                </el-sub-menu>
                <!-- 商品 -->
                <el-sub-menu index="2">
                  <template #title>
                    <el-icon><GoodsFilled /></el-icon>
                    <span>商品管理</span>
                  </template>
                  <el-menu-item-group title="Group One">
                    <el-menu-item index="2-goodsManage">
                      <router-link to="/mainPage/goodsManage">
                        商品列表
                      </router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group title="Group Two">
                    <el-menu-item index="2-addGood">
                      <router-link to="/mainPage/addGood">
                        新增商品
                      </router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                </el-sub-menu>
                <!-- 订单 -->
                <el-sub-menu index="3">
                  <template #title>
                    <el-icon><List /></el-icon>
                    <span>订单管理</span>
                  </template>
                  <el-menu-item-group title="Group One">
                    <el-menu-item index="3-orderManage">
                      <router-link to="/mainPage/orderManage">
                        订单列表
                      </router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group title="Group Two">
                    <el-menu-item index="3-addOrder">
                      <router-link to="/mainPage/addOrder">
                        新增订单
                      </router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                </el-sub-menu>
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <el-main>
          <!-- 指定组件呈现位置 -->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import axios from 'axios'
import TopBanner from "../components/TopBanner.vue";
import {
  List,
  GoodsFilled,
  Location,
  Avatar
} from '@element-plus/icons-vue'
import { onMounted } from 'vue'
import { useUserStore } from '../store/userstore';
import { storeToRefs } from 'pinia';
const userStore = useUserStore()
//  用户信息
// const userInfo = {
//   username:'',
//   password:'',
//   email:'',
//   real_name:'',
//   phone:'',
//   address:'',
//   status:'',
// }
const { currentUserInfo } = storeToRefs(userStore)

</script>

<style>
.router-link-active {
  text-decoration: none;
  color: green;
}
a {
  text-decoration: none;
  color: black;
}
.el-aside {  
  height: 100vh; /* 设置高度为视口高度 */  
  overflow-y: auto; /* 允许垂直滚动 */  
  /* 其他样式 */  
}

.tac {
  height: 100vh; /* 设置行的高度为视口高度 */  
}

.el-menu-vertical-demo {
  height: 90vh; /* 设置行的高度为视口高度 */  
  width: 200px;
}
</style>
