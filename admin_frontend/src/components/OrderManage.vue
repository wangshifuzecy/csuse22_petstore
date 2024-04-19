<template>
    <h3>订单管理</h3>
    <el-table
    :data="orderList"
    style="width: 100%"
    max-height="90vh"
    height="auto">
    <el-table-column
      fixed
      prop="orderid"
      label="订单编号"
      width="150">
    </el-table-column>
    <el-table-column
      prop="userid"
      label="下单用户"
      width="120">
    </el-table-column>
    <el-table-column
      prop="orderdate"
      label="下单日期"
      width="120">
    </el-table-column>
    <el-table-column
      prop="shipcountry"
      label="发货国家"
      width="120">
    </el-table-column>
    <el-table-column
      prop="shipfromstate"
      label="发货省份"
      width="120">
    </el-table-column>
    <el-table-column
      prop="shipfromcity"
      label="发货城市"
      width="120">
    </el-table-column>
    <el-table-column
      prop="shipfromadd1"
      label="发货详细地址"
      width="120">
    </el-table-column>
    <el-table-column
      prop="shipfromadd2"
      label="发货备用地址"
      width="120">
    </el-table-column>
    <el-table-column
      prop="shipfromzip"
      label="发货地址邮编"
      width="120">
    </el-table-column>
    <el-table-column
      prop="courier"
      label="派送快递"
      width="120">
    </el-table-column>
    <el-table-column
      prop="billcountry"
      label="送达国家"
      width="120">
    </el-table-column>
    <el-table-column
      prop="billstate"
      label="送达省份"
      width="120">
    </el-table-column>
    <el-table-column
      prop="billcity"
      label="送达城市"
      width="120">
    </el-table-column>
    <el-table-column
      prop="billaddr1"
      label="送达详细地址"
      width="120">
    </el-table-column>
    <el-table-column
      prop="billaddr2"
      label="送达备用地址"
      width="120">
    </el-table-column>
    <el-table-column
      prop="billzip"
      label="送达地址邮编"
      width="120">
    </el-table-column>
    <el-table-column
      prop="exprdate"
      label="送达时间"
      width="120">
    </el-table-column>
    <el-table-column
      prop="billtofirstname"
      label="买家名"
      width="120">
    </el-table-column>
    <el-table-column
      prop="billtolastname"
      label="买家姓"
      width="120">
    </el-table-column>
    <el-table-column
      prop="cardtype"
      label="买家银行卡类型"
      width="130">
    </el-table-column>
    <el-table-column
      prop="creditcard"
      label="买家银行卡号"
      width="120">
    </el-table-column>
    <el-table-column
      prop="totalprice"
      label="总价"
      width="120">
    </el-table-column>
    <el-table-column
      prop="/"
      label="订单操作"
      width="120">
      <template v-slot:default="scope">
        <!-- 使用插槽获取该行数据 -->
        <el-button type="primary" :icon="Edit" circle  @click="editOrderRow(scope.row)"/>
        <el-button type="danger" :icon="Delete" circle @click="deleteOrderRow(scope.row)"  />
      </template>
      </el-table-column>
  </el-table>

  <!-- 点击修改商品信息后弹出的表单对话框 -->
  <el-dialog
    v-model="dialogVisible"
    title="修改订单信息"
    width="500"
    :before-close="handleClose"
  >
  <el-form
    style="max-width: 600px"
    ref="orderFormData"
    :model="selectData"
    label-width="auto"
    class="demo-ruleForm"
    status-icon
  >
    <el-form-item label="订单编号" prop="orderid">
      <el-input v-model="selectData.orderid" />
    </el-form-item>

    <el-form-item label="下单用户" prop="userid">
      <el-input v-model="selectData.userid" />
    </el-form-item>

    <el-form-item label="下单日期" required>
      <el-col :span="11">
        <el-form-item prop="orderdate">
          <el-date-picker
            v-model="selectData.orderdate"
            type="date"
            label="选择下单日期"
            placeholder="选择下单日期"
            style="width: 100%"
          />
        </el-form-item>
      </el-col>
    </el-form-item>

    <el-form-item label="发货国家" prop="shipcountry">
      <el-input v-model="selectData.shipcountry" />
    </el-form-item>

    <el-form-item label="发货省份" prop="shipfromstate">
      <el-input v-model="selectData.shipfromstate" />
    </el-form-item>

    <el-form-item label="发货城市" prop="shipfromcity">
      <el-input v-model="selectData.shipfromcity" />
    </el-form-item>

    <el-form-item label="发货详细地址" prop="shipfromadd1">
      <el-input v-model="selectData.shipfromadd1" />
    </el-form-item>

    <el-form-item label="发货备用地址" prop="shipfromadd2">
      <el-input v-model="selectData.shipfromadd2" />
    </el-form-item>

    <el-form-item label="发货地址邮编" prop="shipfromzip">
      <el-input v-model="selectData.shipfromzip" />
    </el-form-item>

    <el-form-item label="派送快递" prop="courier">
      <el-select v-model="selectData.courier" placeholder="派送快递">
        <el-option label="中国邮政" value="中国邮政" />
        <el-option label="顺丰快递" value="顺丰快递" />
        <el-option label="韵达快递" value="韵达快递" />
        <el-option label="极兔快递" value="极兔快递" />
        <el-option label="中通快递" value="中通快递" />
        <el-option label="申通快递" value="申通快递" />
      </el-select>
    </el-form-item>

    <el-form-item label="送达国家" prop="billcountry">
      <el-input v-model="selectData.billcountry" />
    </el-form-item>

    <el-form-item label="送达省份" prop="billstate">
      <el-input v-model="selectData.billstate" />
    </el-form-item>

    <el-form-item label="送达城市" prop="billcity">
      <el-input v-model="selectData.billcity" />
    </el-form-item>

    <el-form-item label="送达详细地址" prop="billaddr1">
      <el-input v-model="selectData.billaddr1" />
    </el-form-item>

    <el-form-item label="送达备用地址" prop="billaddr2">
      <el-input v-model="selectData.billaddr2" />
    </el-form-item>

    <el-form-item label="送达地址邮编" prop="billzip">
      <el-input v-model="selectData.billzip" />
    </el-form-item>

    <el-form-item label="送达日期" required>
      <el-col :span="11">
        <el-form-item prop="exprdate">
          <el-date-picker
            v-model="selectData.exprdate"
            type="date"
            label="选择下单日期"
            placeholder="选择下单日期"
            style="width: 100%"
          />
        </el-form-item>
      </el-col>
    </el-form-item>

    <el-form-item label="买家名" prop="billtofirstname">
      <el-input v-model="selectData.billtofirstname" />
    </el-form-item>

    <el-form-item label="买家姓" prop="billtolastname">
      <el-input v-model="selectData.billtolastname" />
    </el-form-item>

    <el-form-item label="买家银行卡号" prop="creditcard">
      <el-input v-model="selectData.creditcard" />
    </el-form-item>

    <el-form-item label="买家银行卡类型" prop="cardtype">
      <el-input v-model="selectData.cardtype" />
    </el-form-item>

    <el-form-item label="总价" prop="totalprice">
      <el-input v-model="selectData.totalprice" />
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
  import {
  Delete,
  Edit,
} from '@element-plus/icons-vue'
import { ref, reactive } from 'vue'
import axios from 'axios'
import { useOrderStore } from '../store/orderstore'
import { storeToRefs } from 'pinia'
import { onMounted, computed, onBeforeMount} from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
const useOderList = useOrderStore()
const { currentOrderList } = storeToRefs(useOderList)
const orderList = computed(()=> useOderList.currentOrderList)

//选中某行的订单信息
let selectData = reactive({
    orderid:'',
    userid:'',
    orderdate:'',
    shipcountry:'',
    shipfromstate:'',
    shipfromcity:'',
    shipfromadd1:'',
    shipfromadd2:'',
    shipfromzip:'',
    courier:'',
    billcountry:'',
    billstate:'',
    billcity:'',
    billaddr1:'',
    billaddr2:'',
    billzip:'',
    exprdate:'',
    billtofirstname:'',
    billtolastname:'',
    creditcard:'',
    cardtype:'',
    totalprice:''
})
//定义请求路径
const serverURLUpdate = 'http://localhost:8080/updateorder'
const serverURLDelete = 'http://localhost:8080/removeorder'
//对话框是否tanchu
const dialogVisible = ref(false)

//点击修改按钮后获取当前行的数据并弹出对话框
const editOrderRow = (row) =>{
      // 逐个复制属性以保持响应性  
  for (const key in row) {  
    if (row.hasOwnProperty(key) && selectData.hasOwnProperty(key)) {  
      selectData[key] = row[key];  
    }  
  }
    dialogVisible.value = true
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
              location.reload()
        }else{
          //修改商品信息失败
          ElMessage.error(result.data.message);
      }
    }).catch(function(error){
    console.log(error);
    })
    dialogVisible.value = false
  }

  //在对话框中点击取消后处理表单
function resetForm(){
  if (selectData.value) {  
    formData.value.resetFields(); // 使用表单的 resetFields 方法清空表单  
  } 
  dialogVisible.value = false
}

//弹出确认框后再删除
function deleteOrderRow(row){
  selectData.value = {...row}
  ElMessageBox.confirm(
    '即将删除指定数据,是否确认删除?',
    'Warning',
    {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
  const params = JSON.stringify({orderid:selectData.orderid})
  console.log(params)
  axios({
        method:'post',
        url:serverURLDelete,
        headers:{
          'Authorization': `${localStorage.getItem("token")}`, // 使用Bearer token的方式
          'Content-Type': 'application/json'
        },
        data:params
      }).then((result) => {
        console.log(result);
        //删除订单信息成功
        if(result.data.status === 0){
         //利用ElementUI信息提示组件返回登录信息
         ElMessage({
                message: result.data.message,
                type: "success",
              });
              useOderList.getOrderList()
              location.reload()
        }else{
          //修改商品信息失败
          ElMessage.error(result.data.message);
      }
    }).catch(function(error){
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
  console.log("111111");
  useOderList.getOrderList()
})

onBeforeMount(()=>{
  console.log("222222222222");
  useOderList.getOrderList()
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