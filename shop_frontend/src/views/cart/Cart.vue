<script setup>
import {
    Delete,
    ShoppingTrolley
} from '@element-plus/icons-vue'

import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router';
const router = useRouter()
const carts = ref([])
import {getItemByItemIdService} from '@/api/catalog.js'
import { getCartService,updateCartService,removeCartService,resetCartService} from '@/api/cart.js';
import useAccountInfoStore from '@/stores/accountInfo.js'

const getCart = async () => {
    try{
        let result = await getCartService()
        carts.value = result.data;
        for (const cart of carts.value) {
            let itemResponse = await getItemByItemIdService(cart.itemId);
            // console.log(itemResponse)
            cart.listPrice = itemResponse.data.listPrice
            cart.itemAttribute = itemResponse.data.attribute1
            cart.productImage = 'https://mypetstore-csu22.oss-cn-wuhan-lr.aliyuncs.com/product/' + itemResponse.data.productImage
            cart.productName = itemResponse.data.productName
            total.value += cart.listPrice * cart.number
            // console.log(cart);
        }
    }catch{
    }
}
getCart()

const accountInfoStore = useAccountInfoStore()
const accountInfo = ref({...accountInfoStore.info})
const total = ref(0)
const orderModel = ref({
    "totalPrice": total.value,
    "userId": accountInfo.value.username,
    "orderDate": "",
    "shipAddr1": accountInfo.value.address1,
    "shipAddr2": accountInfo.value.address2,
    "shipCity": accountInfo.value.city,
    "shipState": accountInfo.value.state,
    "shipZip": accountInfo.value.zip,
    "shipCountry": accountInfo.value.country,
    "billAddr1": accountInfo.value.address1,
    "billAddr2": accountInfo.value.address2,
    "billCity": accountInfo.value.city,
    "billState": accountInfo.value.state,
    "billZip": accountInfo.value.zip,
    "billCountry": accountInfo.value.country,
    "billToFirstName": accountInfo.value.firstName,
    "billToLastName": accountInfo.value.lastName,
    "shipToFirstName": accountInfo.value.firstName,
    "shipToLastName": accountInfo.value.lastName,
    "courier": "USF",
    "creditCard": "1234",
    "exprDate": "12/26",
    "cardType": "visa",
    "locale": "en_US"
})

const handleChange  = async(cart)=>{
    try{
        let result = await updateCartService(cart)
        total.value = 0
        carts.value.forEach(cart=>{
            total.value += cart.listPrice * cart.number
        })
    }catch{
        // router.push("/cart")
        // ElMessage.error("error")
        setTimeout(() => {
            // 刷新当前页面
            window.location.reload()
        }, 400);
    }
}

const removeCart = async function(cart){
    ElMessageBox.confirm(
        `confirm remove ${cart.productName}`,'Warning',{
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
    })
    .then(async () => {
        let result = await removeCartService(cart)
        carts.value = carts.value.filter(aCart => aCart.productId != cart.productId)
        total.value = 0
        carts.value.forEach(cart => {
            total.value += cart.listPrice * cart.number
        })
        ElMessage({
            type: 'success',
            message: 'Delete completed',
        })
    })
    .catch((err) => {
    })
}

const step = ref(1)
const dialogVisible = ref(false)
import { addOrderService } from '@/api/order';
const checkCart = ()=>{
    dialogVisible.value = true
    clearData()
    step.value = 1
}

const resetCart = async ()=>{
    let result = await resetCartService()
}

const addOrder = async()=>{
    resetCart()
    orderModel.value.orderDate = new Date().toISOString().slice(0, 19)
    let result = await addOrderService(orderModel.value)
    ElMessage.success('add order success')
    router.push('/order')
}

const clearData = ()=>{
    orderModel.value.userId = accountInfo.value.username
    orderModel.value.shipAddr1 = accountInfo.value.address1
    orderModel.value.shipAddr2 = accountInfo.value.address2
    orderModel.value.shipCity = accountInfo.value.city
    orderModel.value.shipState = accountInfo.value.state
    orderModel.value.shipZip = accountInfo.value.zip
    orderModel.value.shipCountry = accountInfo.value.country
    orderModel.value.billAddr1 = accountInfo.value.address1
    orderModel.value.billAddr2 = accountInfo.value.address2
    orderModel.value.billCity = accountInfo.value.city
    orderModel.value.billState = accountInfo.value.state
    orderModel.value.billZip = accountInfo.value.zip
    orderModel.value.billCountry = accountInfo.value.country
    orderModel.value.billToFirstName = accountInfo.value.firstName
    orderModel.value.billToLastName = accountInfo.value.lastName
    orderModel.value.shipToFirstName = accountInfo.value.firstName
    orderModel.value.shipToLastName = accountInfo.value.lastName
    orderModel.value.courier = "USF",
    orderModel.value.creditCard = "1234",
    orderModel.value.exprDate = "12/26",
    orderModel.value.cardType = "visa",
    orderModel.value.locale = "en_US"
    orderModel.value.totalPrice = total.value
}

</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div style="display: flex;justify-content: space-between; font-size:25px;" class="header" >
                <span>total price: {{ total }}</span>
                <el-button :icon="ShoppingTrolley" @click="checkCart()" :disabled="total==0">go to check</el-button>
            </div>
        </template>

        <el-table :data="carts" style="width: 100%;font-size: 20px;">
            <el-table-column label="index" width="100" type="index"> </el-table-column>
            <el-table-column label="image" prop="productImage">
                <template #default="scope">
                    <el-image style="width: 50px; height: 50px" :src="scope.row.productImage" />
                </template>
            </el-table-column>
            <el-table-column label="name" prop="productName"></el-table-column>
            <el-table-column label="detail" prop="itemAttribute"></el-table-column>
            <el-table-column label="price" prop="listPrice"></el-table-column>
            <el-table-column label="number" prop="number">
                <template #default="scope">
                    <el-input-number v-model="scope.row.number" :min="1" :max="99" :step="1" controls
                        @change="handleChange(scope.row)"></el-input-number>
                </template>
            </el-table-column>
            <el-table-column label="" width="100">
                <template #default="scope">
                    <el-button :icon="Delete" plain @click="removeCart(scope.row)">remove</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="no data" />
            </template>
        </el-table>
    </el-card>

    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible" width="500">
        <el-form  :label-position="'right'" :model="orderModel" :rules="rules" label-width="100px" style="padding-right: 30px;" class="addOrder">
            <div v-show="step === 1">
                <el-form-item label="User ID" prop="userId" >
                    <el-input v-model="orderModel.userId" disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="Ship Address1" prop="shipAddr1" required="true">
                    <el-input v-model="orderModel.shipAddr1" ></el-input>
                </el-form-item>
                <el-form-item label="Ship Address2" prop="shipAddr2"required="true">
                    <el-input v-model="orderModel.shipAddr2"></el-input>
                </el-form-item>
                <el-form-item label="Ship City" prop="shipCity"required="true">
                    <el-input v-model="orderModel.shipCity"></el-input>
                </el-form-item>
                <el-form-item label="Ship State" prop="shipState"required="true">
                    <el-input v-model="orderModel.shipState"></el-input>
                </el-form-item>
                <el-form-item label="Ship Zip" prop="shipZip"required="true">
                    <el-input v-model="orderModel.shipZip"></el-input>
                </el-form-item>
                <el-form-item label="Ship Country" prop="shipCountry"required="true">
                    <el-input v-model="orderModel.shipCountry"></el-input>
                </el-form-item>
            </div>
            <div v-show="step === 2">
                <el-form-item label="Bill Address1" prop="billAddr1"required="true">
                    <el-input v-model="orderModel.billAddr1"></el-input>
                </el-form-item>
                <el-form-item label="Bill Address2" prop="billAddr2"required="true">
                    <el-input v-model="orderModel.billAddr2"></el-input>
                </el-form-item>
                <el-form-item label="Bill City" prop="billCity"required="true">
                    <el-input v-model="orderModel.billCity"></el-input>
                </el-form-item>
                <el-form-item label="Bill State" prop="billState"required="true">
                    <el-input v-model="orderModel.billState"></el-input>
                </el-form-item>
                <el-form-item label="Bill Zip" prop="billZip"required="true">
                    <el-input v-model="orderModel.billZip"></el-input>
                </el-form-item>
                <el-form-item label="Bill Country" prop="billCountry"required="true">
                    <el-input v-model="orderModel.billCountry"></el-input>
                </el-form-item>
                <el-form-item label="Courier" prop="courier"required="true">
                    <el-input v-model="orderModel.courier"></el-input>
                </el-form-item>
            </div>
            <div v-show="step === 3">
                <el-form-item label="Total Price" prop="totalPrice"required="true">
                    <el-input v-model="orderModel.totalPrice"></el-input>
                </el-form-item>
                <el-form-item label="Bill To First Name" prop="billToFirstName"required="true">
                    <el-input v-model="orderModel.billToFirstName"></el-input>
                </el-form-item>
                <el-form-item label="Bill To Last Name" prop="billToLastName"required="true">
                    <el-input v-model="orderModel.billToLastName"></el-input>
                </el-form-item>
                <el-form-item label="Ship To First Name" prop="shipToFirstName"required="true">
                    <el-input v-model="orderModel.shipToFirstName"></el-input>
                </el-form-item>
                <el-form-item label="Ship To Last Name" prop="shipToLastName"required="true">
                    <el-input v-model="orderModel.shipToLastName"></el-input>
                </el-form-item>
                <el-form-item label="Credit Card" prop="creditCard"required="true">
                    <el-input v-model="orderModel.creditCard"></el-input>
                </el-form-item>
                <el-form-item label="Expiration Date" prop="exprDate"required="true">
                    <el-input v-model="orderModel.exprDate"></el-input>
                </el-form-item>
                <el-form-item label="Card Type" prop="cardType"required="true">
                    <el-input v-model="orderModel.cardType"></el-input>
                </el-form-item>
                <el-form-item label="Locale" prop="locale"required="true">
                    <el-input v-model="orderModel.locale"></el-input>
                </el-form-item>
            </div>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">cancel</el-button>
                <el-button v-show="step===2||step===3"  @click="step--">last step</el-button>
                <el-button v-show="step===1||step===2" type="primary" @click="step++">next step</el-button>
                <el-button v-show="step===3" type="primary" @click="addOrder()"> confirm</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<style>
.addOrder .el-form-item__label{
    font-size: 10px !important;
}
</style>