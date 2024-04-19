<template>
  <h3>商品管理</h3>
  <el-table :data="tableData" style="width: 100%" max-height="90vh" :stripe="true" row-key="itemid">
    <el-table-column fixed prop="itemid" label="商品名称" width="120">
    </el-table-column>
    <el-table-column prop="productid" label="商品类别" width="120">
    </el-table-column>
    <el-table-column prop="categoryid" label="商品大类" width="120">
    </el-table-column>
    <el-table-column prop="img" label="商品图片" width="120">
      <!-- <el-image
      style="width: 100px; height: 100px"
      fit="fill"
      :src="tableData.img"
      @load="show()"
      @error="error()"    
    /> -->
      <template #default="scope">
        <el-image style="width: 100px; height: 100px" :src="scope.row.img" />
        <!-- 显示图片 -->
      </template>
    </el-table-column>
    <el-table-column prop="listprice" label="商品售价" width="120">
    </el-table-column>
    <el-table-column prop="unitcost" label="商品进价" width="120">
    </el-table-column>
    <el-table-column prop="supplier" label="商品供应商" width="120">
    </el-table-column>
    <el-table-column prop="status" label="商品状态" width="120">
    </el-table-column>
    <el-table-column prop="attr1" label="商品描述" width="120">
    </el-table-column>
    <el-table-column prop="quantity" label="商品数量" width="120">
    </el-table-column>
    <el-table-column prop="descn" label="商品信息" width="120">
    </el-table-column>
    <el-table-column prop="/" label="商品操作" width="120">
      <template v-slot:default="scope">
        <!-- 使用插槽获取该行数据 -->
        <el-button type="primary" :icon="Edit" circle @click="editRow(scope.row)" />
        <el-button type="danger" :icon="Delete" circle @click="deleteRow(scope.row)" />
      </template>
    </el-table-column>
  </el-table>

  <!-- 点击修改商品信息后弹出的表单对话框 -->
  <el-dialog v-model="dialogVisible" title="修改商品信息" width="600" :before-close="handleClose">
    <el-form style="max-width: 600px" ref="formData" :model="selectData" label-width="auto" class="demo-ruleForm"
      status-icon>
      <!-- 踩坑：在同时使用ref和:model时二者的属性值不能同名否则会会无法输入 -->
      <el-form-item label="商品名称" prop="itemid">
        <el-input v-model="selectData.itemid" />
      </el-form-item>
      <el-form-item label="商品大类" prop="categoryid">
        <el-select v-model="selectData.categoryid" placeholder="商品大类">
          <el-option label="BIRDS" value="Birds" />
          <el-option label="CATS" value="Cats" />
          <el-option label="DOGS" value="Dogs" />
          <el-option label="FISH" value="Fish" />
          <el-option label="REPTILES" value="Reptiles" />
        </el-select>
      </el-form-item>
      <el-form-item label="商品类别" prop="productid">
        <el-input v-model="selectData.productid" />
      </el-form-item>
      <!-- upload image -->
      <el-form-item label="商品图片" prop="img">
          <el-input disabled v-model="selectData.img" />
          <el-upload
            action="http://localhost:8080/file/product"
            multiple
            :limit="1"
            :on-success="handleUploadSuccess"
          >
            <el-button type="primary">Click to upload</el-button>
            <template #tip>
              <div class="el-upload__tip">
                更改图片需添加图片至oss
              </div>
            </template>
          </el-upload>
      </el-form-item>
      <el-form-item label="商品售价" prop="listprice">
        <el-input v-model="selectData.listprice" />
      </el-form-item>
      <el-form-item label="商品进价" prop="unitcost">
        <el-input v-model="selectData.unitcost" />
      </el-form-item>
      <el-form-item label="商品供应商" prop="supplier">
        <el-input v-model="selectData.supplier" />
      </el-form-item>
      <el-form-item label="商品状态" prop="status">
        <el-radio-group v-model="selectData.status">
          <el-radio value="P">P</el-radio>
          <el-radio value="S">S</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="商品数量" prop="quantity">
        <el-select-v2 v-model="selectData.quantity" placeholder="商品数量" :options="options" />
      </el-form-item>
      <el-form-item label="商品描述" prop="attr1">
        <el-input v-model="selectData.attr1" />
      </el-form-item>
      <el-form-item label="类别描述" prop="descn">
        <el-input v-model="selectData.descn" type="textarea" />
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
import { useGoodStore } from '../store/goodstore'
import { storeToRefs } from 'pinia'
import {
  Delete,
  Edit,
  Plus
} from '@element-plus/icons-vue'

const useGoodsList = useGoodStore()
const { currentGoodsList } = storeToRefs(useGoodsList)
const tableData = computed(() => useGoodsList.currentGoodsList)

//弹出修改信息框中的数据
let selectData = reactive({
  itemid: '',
  productid: '',
  categoryid: '',
  img: '',
  listprice: '',
  unitcost: '',
  supplier: '',
  status: '',
  attr1: '',
  quantity: '',
  descn: ''
})
//定义发送请求的路径
const serverURLUpdate = 'http://localhost:8080/updateitem'
const serverURLDelete = 'http://localhost:8080/removeitem'

const show = function () {
  console.log("图片加载成功");
}
const error = function () {
  console.log(tableData.value);
  console.log("图片加载失败");
}

const dialogVisible = ref(false)
//点击修改按钮后获取当前行的数据并弹出对话框
const editRow = (row) => {
  console.log(selectData);
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
    .then(function () {
      done();
    })
    .catch(function () {
      // catch error  
    });
}
//上传图片文件
const handleUploadSuccess = (response, file)=>{
  if(response && response.status== 0){
    selectData.img = response.data
    // console.log("select data img:", selectData.img)
    ElMessage.success('上传到oss成功')
  }
}
//在对话框中点击提交后处理提交表单
const submitModifyForm = async function () {
  //这里image url的值是完整的，截取最后的文件名保留到数据库 
  const uploadData = selectData
  uploadData.img= uploadData.img.match(/[^\/]+$/)[0]
  axios({
    method: 'post',
    url: serverURLUpdate,
    headers: {
      'Authorization': `${localStorage.getItem("token")}`, // 使用Bearer token的方式
      'Content-Type': 'application/json'
    },
    data: JSON.stringify(uploadData)
  }).then((result) => {
    console.log("submit good result", result);
    //修改商品信息成功
    if (result.data.status === 0) {
      //利用ElementUI信息提示组件返回登录信息
      ElMessage({
        message: result.data.message,
        type: "success",
      });
      location.reload()
    } else {
      //修改商品信息失败
      ElMessage.error(result.data.message);
    }
  }).catch(function (error) {
    console.log(error);
  })
  dialogVisible.value = false
  location.reload()
}
//在对话框中点击取消后处理表单
function resetForm() {
  if (selectData.value) {
    selectData.value.resetFields(); // 使用表单的 resetFields 方法清空表单  
  }
  dialogVisible.value = false
}
//删除指定行
async function deleteRow(row) {
  selectData.value = { ...row }
  console.log(selectData.value)
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
        method: 'post',
        url: serverURLDelete,
        headers: {
          'Authorization': `${localStorage.getItem("token")}`, // 使用Bearer token的方式
          'Content-Type': 'application/json'
        },
        data: JSON.stringify({ itemid: selectData.value.itemid })
      }).then((result) => {
        console.log(result);
        //删除商品信息成功
        if (result.data.status === 0) {
          //利用ElementUI信息提示组件返回登录信息
          ElMessage({
            message: result.data.message,
            type: "success",
          });
          location.reload()
        } else {
          //删除商品信息失败
          ElMessage.error(result.data.message);
        }
      }).catch(function (error) {
        console.log(error);
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: 'Delete canceled',
      })
    })
}

onMounted(()=>{
  console.log("onMounted");
  useGoodsList.getGoodsList()
  // tableData.value = currentGoodsList.value
})

onBeforeMount(()=>{
  useGoodsList.getGoodsList()
  // tableData.value = currentGoodsList.value
})
</script>

<style>
.demo-image .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  display: inline-block;
  width: 20%;
  box-sizing: border-box;
  vertical-align: top;
}
.demo-image .block:last-child {
  border-right: none;
}
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>