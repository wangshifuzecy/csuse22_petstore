<template>
  <el-form style="max-width: 599px" ref="formData" :model="ruleForm" :rules="rules" label-width="auto"
    class="demo-ruleForm" status-icon>
    <!-- 踩坑：在同时使用ref和:model时二者的属性值不能同名否则会会无法输入 -->
    <el-form-item label="商品名称" prop="itemid">
      <el-input v-model="ruleForm.itemid" />
    </el-form-item>
    <el-form-item label="商品大类" prop="categoryid">
      <el-select v-model="ruleForm.categoryid" placeholder="请选择商品大类">
        <el-option label="BIRDS" value="Birds" />
        <el-option label="CATS" value="Cats" />
        <el-option label="DOGS" value="Dogs" />
        <el-option label="FISH" value="Fish" />
        <el-option label="REPTILES" value="Reptiles" />
      </el-select>
    </el-form-item>
    <el-form-item label="商品类别" prop="productid">
      <el-select v-model="ruleForm.productid" placeholder="请选择商品类别">
        <el-option v-for="id in productIds" :key="id" :label="id" :value="id" />
      </el-select>
    </el-form-item>
    <el-form-item label="商品图片" prop="img">
      <el-input disabled v-model="ruleForm.img" placeholder="在上传图片至云端后可显示图片在oss处地址"></el-input>
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
      <el-input-number v-model="ruleForm.listprice" :min="1" :max="10000" :precision="2"/>
    </el-form-item>
    <el-form-item label="商品进价" prop="unitcost">
      <el-input-number v-model="ruleForm.unitcost" :min="1" :max="10000" :precision="2"/>
    </el-form-item>
<!--  TODO: 应该读取当前用户信息， 还应该保存当前用户信息到一个store中方便共用 -->
    <el-form-item label="商品供应商" prop="supplier">
      <el-input disabled v-model="ruleForm.supplier">
      <template #prepend>当前管理员ID</template>
      </el-input>
    </el-form-item>
    <el-form-item label="商品状态" prop="status">
      <el-radio-group v-model="ruleForm.status">
        <el-radio value="P">P</el-radio>
        <el-radio value="S">S</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="商品数量" prop="quantity">
      <el-input-number v-model="ruleForm.quantity" :min="1" :max="10000" step-strictly/>
    </el-form-item>
    <el-form-item label="商品描述" prop="attr1">
      <el-input v-model="ruleForm.attr1" />
    </el-form-item>
    <el-form-item label="类别描述" prop="descn">
      <el-input v-model="ruleForm.descn" type="textarea" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm()">
        Create
      </el-button>
      <el-button @click="resetForm()">Reset</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive, ref } from "vue"
import axios from 'axios'
import { ElMessageBox, ElMessage } from 'element-plus'
import { ArrowDown, ArrowUp, Minus, Plus } from '@element-plus/icons-vue'
const formData = ref(null)
const ruleForm = reactive({
  itemid:'',
  productid:'',
  categoryid:'',
  img:'',
  listprice:10,
  unitcost:5,
  supplier:1,
  status:'P',
  attr1:'',
  quantity:10,
  descn:'',
})
//product id list
const productIds = [
      'AV-CB-01',
      'AV-SB-02',
      'FI-FW-01',
      'FI-FW-02',
      'FI-SW-01',
      'FI-SW-02',
      'FL-DLH-02',
      'FL-DSH-01',
      'K9-BD-01',
      'K9-CW-01',
      'K9-DL-01',
      'K9-PO-02',
      'K9-RT-01',
      'K9-RT-02',
      'RP-LI-02',
      'RP-SN-01'
]
const rules = reactive({
  itemid: [
    { required: true, message: '请输入商品名称', trigger: 'blur' },
    { min: 3, max: 10, message: '商品名称长度应为3-10', trigger: 'blur' },
  ],
  productid: [
    {
      required: true,
      message: '请选择商品大类',
      trigger: 'change',
    },
  ],
  categoryid: [
    {
      required: true,
      message: '请输入商品类别',
      trigger: 'blur',
    },
  ],
  listprice: [
    {
      type: 'string',
      required: true,
      message: '请输入商品售价',
      trigger: 'blur',
    },
    {
      min: 0, message: '商品售价不能小于0', trigger: 'blur'
    }
  ],
  unitcost: [
    {
      type: 'string',
      required: true,
      message: '请输入商品进价',
      trigger: 'blur',
    },
    {
      min: 0, message: '商品售价不能小于0', trigger: 'blur'
    }
  ],
  supplier: [
    {
      type: 'string',
      required: true,
      message: '请输入商品供应商',
      trigger: 'blur',
    },
  ],
  status:[
    {
      required: true,
      message: '请选择商品状态',
      trigger: 'change',
    }
  ],
  attr1: [
    {
      required: true,
      message: '请输入商品描述',
      trigger: 'blur',
    },
  ],
  quantity:[
    {
      required: true,
      message: '请选择商品数量',
      trigger: 'change',
    }
  ],
  descn: [
    { required: true, message: '请输入详细描述', trigger: 'blur' },
  ],
})
const options = Array.from({ length: 100 }).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))

//上传图片文件
const handleUploadSuccess = (response, file)=>{
  if(response && response.status== 0){
    ruleForm.img = response.data
    // console.log("select data img:", selectData.img)
    ElMessage.success('上传到oss成功')
  }
}
const serverURL = 'http://localhost:8080/newitem'
const token = localStorage.getItem("token")
// 发送表单数据
const submitForm = async function(){
  //这里image url的值是完整的，截取最后的文件名保留到数据库 
  const uploadData = ruleForm
  if(ruleForm.img){
    uploadData.img= uploadData.img.match(/[^\/]+$/)[0]
    console.log(uploadData)
  }else{
    ElMessage.warning('图片未上传')
    return
  }
  axios({
   method:'post',
   url: serverURL,
   headers:{
    'Authorization': `${localStorage.getItem("token")}`, // 使用Bearer token的方式
    'Content-Type': 'application/json'  
   },
   data:JSON.stringify(uploadData)
  }).then((result) => {
    console.log(result);
    //新增商品成功
    if(result.data.status === 0){
      //利用ElementUI信息提示组件返回登录信息
      ElMessage({
                message: result.data.message,
                type: "success",
              });
      resetForm()
    }else{
      //新增商品失败
      ElMessage.error(result.data.message);
    }
  }).catch(function(error){
    console.log(error);
  })
}

const resetForm = function(){
  if (formData.value) {  
    formData.value.resetFields(); // 使用表单的 resetFields 方法清空表单  
  } 
}

</script>

<style>

</style>