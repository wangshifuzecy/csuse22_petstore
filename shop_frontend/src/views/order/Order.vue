<script setup>
import { ref } from 'vue'
const orders = ref([])
import {getAllOrderService} from '@/api/order.js'
const getAllOrder = async () => {
    try{
        let result = await getAllOrderService()
        orders.value = result.data;
        //   ElMessage.success('register success')
    }catch{
    }
}
getAllOrder()

</script>

<template>
    <el-card class="page-container" style="font-size:25px;">
        <template #header>
            <div class="header">
                <span>pet order</span>
            </div>
        </template>
        <div>
            <el-table :data="orders" style="width: 100%; font-size: 12px"
                :row-style="{height:'20px'}"
            >
                <el-table-column label="Order ID" prop="orderId"></el-table-column>
                <el-table-column label="User ID" prop="userId"></el-table-column>
                <el-table-column label="Order Date" prop="orderDate"></el-table-column>
                <el-table-column label="Shipping Address" prop="shipAddr1"></el-table-column>
                <el-table-column label="Shipping Address 2" prop="shipAddr2"></el-table-column>
                <el-table-column label="Shipping City" prop="shipCity"></el-table-column>
                <el-table-column label="Shipping State" prop="shipState"></el-table-column>
                <el-table-column label="Shipping Zip" prop="shipZip"></el-table-column>
                <el-table-column label="Shipping Country" prop="shipCountry"></el-table-column>
                <el-table-column label="Billing Address" prop="billAddr1"></el-table-column>
                <el-table-column label="Billing Address 2" prop="billAddr2"></el-table-column>
                <el-table-column label="Billing City" prop="billCity"></el-table-column>
                <el-table-column label="Billing State" prop="billState"></el-table-column>
                <el-table-column label="Billing Zip" prop="billZip"></el-table-column>
                <el-table-column label="Billing Country" prop="billCountry"></el-table-column>
                <el-table-column label="Courier" prop="courier"></el-table-column>
                <el-table-column label="Total Price" prop="totalPrice" :formatter="formatCurrency"></el-table-column>
                <el-table-column label="Billing First Name" prop="billToFirstName"></el-table-column>
                <el-table-column label="Billing Last Name" prop="billToLastName"></el-table-column>
                <el-table-column label="Shipping First Name" prop="shipToFirstName"></el-table-column>
                <el-table-column label="Shipping Last Name" prop="shipToLastName"></el-table-column>
                <el-table-column label="Credit Card" prop="creditCard"></el-table-column>
                <el-table-column label="Expiry Date" prop="exprDate"></el-table-column>
                <el-table-column label="Card Type" prop="cardType"></el-table-column>
                <el-table-column label="Locale" prop="locale"></el-table-column>
            </el-table>
        </div>
    </el-card>
</template>