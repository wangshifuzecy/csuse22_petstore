<template>
    <el-descriptions
    class="margin-top"
    title="用户信息"
    :column="3"
    :size="size"
    border
  >
    <template #extra>
      <el-button type="danger" @click="exitUser()">退出登录</el-button>
      <el-button type="primary" @click="editUserInfo()">修改个人信息</el-button>
    </template>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <user />
          </el-icon>
          用户名
        </div>
      </template>
      {{userInfo.username}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <Message />
          </el-icon>
          邮箱
        </div>
      </template>
      {{userInfo.email}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <location />
          </el-icon>
          地址
        </div>
      </template>
      {{userInfo.address}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <Lock />
          </el-icon>
          密码
        </div>
      </template>
      {{userInfo.password}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <Iphone />
          </el-icon>
          电话号码
        </div>
      </template>
      {{ userInfo.phone }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <ChatDotRound />
          </el-icon>
          真实姓名
        </div>
      </template>
     {{ userInfo.realName }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <Bell />
          </el-icon>
          用户状态
        </div>
      </template>
      {{ userInfo.status }}
    </el-descriptions-item>
  </el-descriptions>

<!-- 点击修改商品信息后弹出的表单对话框 -->
<el-dialog
  v-model="dialogVisible"
  title="修改商品信息"
  width="500"
  :before-close="handleClose"
>
<el-form
    style="max-width: 600px"
    ref="formData"
    :model="userInfo"
    label-width="auto"
    class="demo-ruleForm"
    status-icon
  >
  <!-- 踩坑：在同时使用ref和:model时二者的属性值不能同名否则会会无法输入 -->
    <el-form-item label="用户名" prop="username">
      <el-input v-model="userInfo.username" />
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="userInfo.password" />
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="userInfo.email" />
    </el-form-item>
    <el-form-item label="真实姓名" prop="realName">
      <el-input v-model="userInfo.realName" />
    </el-form-item>
    <el-form-item label="电话" prop="phone">
      <el-input v-model="userInfo.phone" />
    </el-form-item>
    <el-form-item label="地址" prop="address">
      <el-input v-model="userInfo.address" />
    </el-form-item>
  </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="resetForm()">取消</el-button>
        <el-button type="primary" @click="submitModifyForm()">
          提交
        </el-button>
      </div>
    </template>
</el-dialog>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import axios from 'axios'
import {
  User,
  Message,
  Location,
  Lock,
  Iphone,
  ChatDotRound,
  Bell
} from '@element-plus/icons-vue'
import { useUserStore } from '../store/userstore';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
const router = useRouter();
const useUser = useUserStore()
const { currentUserInfo } = storeToRefs(useUser)

const userInfo = computed(()=> useUser.currentUserInfo)

const dialogVisible = ref(false)

 //定义发送请求的路径
const serverURLUpdate = 'http://localhost:8080/userinfo'

const editUserInfo = function () {
    console.log("用户信息:"+userInfo)
    dialogVisible.value = true
}

function resetForm() {
  dialogVisible.value = false
}

  //在对话框中点击提交后处理提交表单
  const submitModifyForm = async function (){
      axios({
        method:'post',
        url:'http://localhost:8080/newuserinfo',
        headers:{
          'Authorization': `${localStorage.getItem("token")}`, // 使用Bearer token的方式
          'Content-Type': 'application/json'
        },
        data:JSON.stringify(userInfo.value)
      }).then((result) => {
        console.log(result);
        //修改商品信息成功
        if(result.data.status === 0){
         //利用ElementUI信息提示组件返回登录信息
         ElMessage({
                message: result.data.message,
                type: "success",
              });
        }else{
          //修改商品信息失败
          ElMessage.error(result.data.message);
      }
    }).catch(function(error){
    console.log(error);
    })
    dialogVisible.value = false
    location.reload()
  }
 //处理关闭对话框
 function handleClose(done) {  
  ElMessageBox.confirm('是否确认退出?表单数据未保存')  
    .then(function() {  
      done();  
    })  
    .catch(function() {  
      // catch error  
    });  
  }
  
  const size = ref('default')
  const iconStyle = computed(() => {
  const marginMap = {
    large: '8px',
    default: '6px',
    small: '4px',
  }
  return {
    marginRight: marginMap[size.value] || marginMap.default,
  }

})

function exitUser(){
  router.push('/userlogin')
  localStorage.clear()
}

router.beforeEach((to, from, next) => {  
  const isAuthenticated = localStorage.getItem("token")  
  if (to.path !== '/userlogin' && isAuthenticated == null) {  
    next('/userlogin'); // 如果用户未认证且不是登录页面，则重定向到登录页面  
    return false
  } else {  
    next(); // 否则继续导航  
  }  
});
onMounted(()=>{
  console.log(userInfo.value);
  useUser.getUserInfo()
})
</script>

<style>
.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}

</style>