<script setup>
import {
    CirclePlusFilled
} from '@element-plus/icons-vue'
import { ref,watch } from 'vue'
import { useRouter,useRoute } from 'vue-router'
import {getItemsBySearchService,getItemsByProductIdService} from '@/api/catalog.js'
import { addCartService } from '@/api/cart';
import {} from '@/api/catalog'

const router = useRouter()
const route = useRoute()
const items = ref([])
let searchString = decodeURIComponent(router.currentRoute.value.fullPath.split('/').pop())

watch(() => decodeURIComponent(router.currentRoute.value.fullPath.split('/').pop()), (newSearchString) => {
  searchString = newSearchString;
  getItemsBySearch();
});

const getItemsBySearch = async () => {
    try{
        let result = await getItemsBySearchService(searchString)
        items.value = result.data;
        items.value.forEach(item =>{
            item.productImage = 'https://mypetstore-csu22.oss-cn-wuhan-lr.aliyuncs.com/product/' + item.productImage
        })
    }catch{
    }
}
getItemsBySearch()

const addCart = async (item)=>{
    try{
        let result = await addCartService(item)
        router.push({name: 'carts'})
    }catch{
    }
}
</script>

<template>
    <el-card class="page-container" style="font-size:25px;">
        <template #header>
            <div class="header">
                <span>pet search</span>
            </div>
        </template>
        <el-table :data="items" style="width: 100%;font-size:20px;">
            <el-table-column label="index" width="100" type="index"> </el-table-column>
            <el-table-column label="image" prop="productImage">
                <template #default="scope">
                    <el-image style="width: 100px; height: 100px" :src="scope.row.productImage" />
                </template>
            </el-table-column>
            <el-table-column label="name" prop="productName"></el-table-column>
            <el-table-column label="item Id" prop="itemId"></el-table-column>
            <el-table-column label="price" prop="listPrice"></el-table-column>
            <el-table-column label="detail" prop="attribute1"></el-table-column>
            <!-- 使用按钮跳转 -->
            <el-table-column label="" width="100">
                <template #default="scope">
                    <el-button :icon="CirclePlusFilled" plain type="primary" @click="addCart(scope.row)"></el-button>
                </template> 
            </el-table-column>
            <template #empty>
                <el-empty description="no data" />
            </template>
        </el-table>
    </el-card>
</template>