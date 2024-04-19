<script setup>
import {
    CirclePlusFilled
} from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router';
const router = useRouter()
const products = ref([])
import {getProductsByCategoryIdService} from '@/api/catalog.js'
const categoryId = router.currentRoute.value.fullPath.split('/').pop()
const getProductsByCategoryId = async () => {
    try{
        let result = await getProductsByCategoryIdService(categoryId)
        products.value = result.data;
        products.value.forEach(product =>{
            product.image = 'https://mypetstore-csu22.oss-cn-wuhan-lr.aliyuncs.com/product/' + product.image
        })
        console.log(products.value)
    }catch{
    }
}
getProductsByCategoryId()
</script>

<template>
    <el-card class="page-container" style="font-size:25px;"> 
        <template #header>
            <div class="header">
                <span>pet product</span>
            </div>
        </template>

        <el-table :data="products" style="width: 100%;font-size:20px;">
            <el-table-column label="index" width="100" type="index"> </el-table-column>
            <el-table-column label="image" prop="image">
                <template #default="scope">
                    <el-image style="width: 100px; height: 100px" :src="scope.row.image" />
                </template>
            </el-table-column>
            <!-- 使用点击蓝链跳转 -->
            <el-table-column label="product Id">
                <template #default="{row}">
                    <router-link  :to="'/pet/product/' + row.productId">{{ row.productId }}</router-link>
                </template>
            </el-table-column>
            <el-table-column label="name" prop="name"></el-table-column>
            <el-table-column label="information" prop="description"></el-table-column>
            <!-- 使用按钮跳转 -->
            <!-- <el-table-column label="see details" width="100">
                <template #default="{ row }">
                    <el-button :icon="CirclePlusFilled" plain type="primary" @click="jump(row.productId)"></el-button>
                </template> 
            </el-table-column> -->
            <template #empty>
                <el-empty description="no data" />
            </template>
        </el-table>
    </el-card>
</template>

<style scoped>
</style>