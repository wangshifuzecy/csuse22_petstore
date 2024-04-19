<template>
  <h3>用户管理</h3>
  <el-table
  :data="userList.value"
  style="width: 100%"
  height="auto"
  row-key="username">
  <el-table-column
    fixed
    prop="username"
    label="用户名"
    width="120">
  </el-table-column>
  <el-table-column
    prop="password"
    label="用户密码"
    width="120">
  </el-table-column>
  <el-table-column
    prop="email"
    label="用户邮箱"
    width="120">
  </el-table-column>
  <el-table-column
    prop="realName"
    label="用户真实姓名"
    width="120">
  </el-table-column>
  <el-table-column
    prop="phone"
    label="用户电话"
    width="120">
  </el-table-column>
  <el-table-column
    prop="address"
    label="用户地址"
    width="120">
  </el-table-column>
  <el-table-column
    prop="status"
    label="用户状态"
    width="120">
  </el-table-column>
  <el-table-column
    prop="/"
    label="用户操作"
    width="120">
    <template v-slot:default="scope">
      <!-- 使用插槽获取该行数据 -->
      <el-button type="primary" :icon="Edit" circle  @click="editRow(scope.row)"/>
      <el-button type="danger" :icon="Delete" circle @click="deleteRow(scope.row)"  />
    </template>
  </el-table-column>
</el-table>

<!-- 点击修改商品信息后弹出的表单对话框 -->
<el-dialog
  v-model="dialogVisible"
  title="修改用户信息"
  width="500"
  :before-close="handleClose"
>
<el-form
  style="max-width: 600px"
  :model="selectData"
  label-width="auto"
  class="demo-ruleForm"
  status-icon
>
<!-- 踩坑：在同时使用ref和:model时二者的属性值不能同名否则会会无法输入 -->
  <el-form-item label="用户名" prop="username">
    <el-input v-model="selectData.username" />
  </el-form-item>
  <el-form-item label="用户密码" prop="password">
    <el-input v-model="selectData.password" />
  </el-form-item>
  <el-form-item label="用户邮箱" prop="email">
    <el-input v-model="selectData.email" />
  </el-form-item>
  <el-form-item label="用户姓名" prop="realName">
    <el-input v-model="selectData.realName" />
  </el-form-item>
  <el-form-item label="用户电话" prop="phone">
    <el-input v-model="selectData.phone" />
  </el-form-item>
  <el-form-item label="用户地址" prop="address">
    <el-input v-model="selectData.address" />
  </el-form-item>
  <el-form-item label="用户状态" prop="status">
    <el-radio-group v-model="selectData.status">
      <el-radio value="P">P</el-radio>
      <el-radio value="S">S</el-radio>
    </el-radio-group>
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
import { reactive, ref, computed } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import axios from 'axios'
import { onMounted, onBeforeMount} from 'vue'
import {
Delete,
Edit,
} from '@element-plus/icons-vue'
const userList = reactive([])
//弹出修改信息框中的数据
let selectData = reactive({
      username:'',
      password:'',
      email:'',
      realName:'',
      phone:'',
      address:'',
      status:'',
})
//定义发送请求的路径
const serverURLGet = 'http://localhost:8080/sellers'
const serverURLUpdate = 'http://localhost:8080/updateseller'
const serverURLDelete = 'http://localhost:8080/deleteseller'

const show = function(){
  console.log("图片加载成功");
}
const error = function(){
  tableData.img = 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg'
  tableData.previewImgList = ['https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg']
}

const dialogVisible = ref(false)
//点击修改按钮后获取当前行的数据并弹出对话框
const editRow = (row) =>{
  //不要直接赋值避免丢失响应式
    // 逐个复制属性以保持响应性  
    for (const key in row) {  
    if (row.hasOwnProperty(key) && selectData.hasOwnProperty(key)) {  
      selectData[key] = row[key];  
    }  
  }
  dialogVisible.value = true
}
//设置下拉框中数据范围
const options = Array.from({ length: 100 }).map((_, idx) => ({
value: `${idx + 1}`,
label: `${idx + 1}`,
}))
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
//在对话框中点击提交后处理提交表单
const submitModifyForm = async function (){
    axios({
      method:'post',
      url:serverURLUpdate,
      headers:{
        'Authorization': `${localStorage.getItem("token")}`, // 使用Bearer token的方式
        'Content-Type': 'application/json'
      },
      data:JSON.stringify(selectData)
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
        ElMessage({
          message: result.data.message,
          type: 'warning'
        });
    }
  }).catch(function(error){
  console.log(error);
  })
  dialogVisible.value = false
  location.reload()
}
//在对话框中点击取消后处理表单
function resetForm(){
if (selectData.value) {  
  formData.value.resetFields(); // 使用表单的 resetFields 方法清空表单  
} 
dialogVisible.value = false
}
//删除指定行
async function deleteRow(row) {
selectData.value = {...row}
ElMessageBox.confirm(
  '即将删除指定数据,是否确认删除?',
  'Warning',
  {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning',
  }
)
  .then(() => {
    axios({
      method:'post',
      url:serverURLDelete,
      headers:{
        'Authorization': `${localStorage.getItem("token")}`, // 使用Bearer token的方式
        'Content-Type': 'application/json'
      },
      data:JSON.stringify(selectData.username)
    }).then((result) => {
      console.log(result);
      //删除商品信息成功
      if(result.data.status === 0){
       //利用ElementUI信息提示组件返回登录信息
       ElMessage({
              message: result.data.message,
              type: "success",
            });
      }else{
        //删除商品信息失败
        ElMessage.error(result.data.message);
    }
  }).catch(function(error){
  console.log(error);
  })
  })
  .catch(() => {
    ElMessage({
      type: 'info',
      message: '取消删除',
    })
  })

}
//页面加载时获取用户列表
onMounted(()=>{
  axios({
    method:'get',
    url:serverURLGet,
    headers:{
        'Authorization': `${localStorage.getItem("token")}`, // 使用Bearer token的方式
        'Content-Type': 'application/json'
    },
  }).then((result)=>{
    console.log(result);
    userList.value = result.data.data
    console.log(userList);
  }).catch(()=>{
    ElMessage({
      type:'error',
      message:'加载失败'
    })
  })
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