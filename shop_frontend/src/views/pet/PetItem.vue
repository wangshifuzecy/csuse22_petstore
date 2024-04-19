<script setup>
import {
    CirclePlusFilled
} from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router';
import {getItemsByProductIdService} from '@/api/catalog.js'
import { addCartService } from '@/api/cart';

const router = useRouter()
const items = ref([])
const productId = router.currentRoute.value.fullPath.split('/').pop()
const getItemsByProductId = async () => {
    try{
        let result = await getItemsByProductIdService(productId)
        items.value = result.data;
        items.value.forEach(item =>{
            item.productImage = 'https://mypetstore-csu22.oss-cn-wuhan-lr.aliyuncs.com/product/' + item.productImage
        })//添加图标网址
    }catch{
    }
}
getItemsByProductId()

const addCart = async (item)=>{
    try{
        let result = await addCartService(item)
        router.push({name: 'carts'})
    }catch{
    }
}
</script>

<template>
    <el-card class="page-container" style="font-size:20px;">
        <template #header>
            <div class="header">
                <span>pet item</span>
            </div>
        </template>

        <el-table :data="items" style="width: 100%;font-size:25px;">
            <el-table-column label="index" width="100" type="index"> </el-table-column>
            <el-table-column label="image" prop="productImage">
                <template #default="scope">
                    <el-image style="width: 100px; height: 100px" :src="scope.row.productImage" />
                    <!-- 显示图片 -->
                </template>
            </el-table-column>
            <el-table-column label="name" prop="productName"></el-table-column>
            <el-table-column label="item Id" prop="itemId"></el-table-column>
            <el-table-column label="price" prop="listPrice"></el-table-column>
            <el-table-column label="detail" prop="attribute1"></el-table-column>
            <el-table-column label="inventory" prop="quantity"></el-table-column>
            <el-table-column label="" width="100">
                <template #default="scope">
                    <el-button :icon="CirclePlusFilled" plain type="primary" @click="addCart(scope.row)" :disabled="!(scope.row.quantity > 0)"></el-button>
                </template> 
            </el-table-column>
            <template #empty>
                <el-empty description="no data" />
            </template>
        </el-table>
    </el-card>
</template>