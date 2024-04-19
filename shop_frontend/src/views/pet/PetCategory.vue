<script setup>
import { ref } from 'vue'
const categories = ref([])
import {getAllCategoryService} from '@/api/catalog.js'
const getAllCategory = async () => {
    try{
        let result = await getAllCategoryService()
        categories.value = result.data;
        categories.value.forEach(category => {
            category.url = `https://mypetstore-csu22.oss-cn-wuhan-lr.aliyuncs.com/category/${category.categoryId}.gif`
        })
        //   ElMessage.success('register success')
    }catch{
    }
}
getAllCategory()
</script>

<template>
    <el-card class="page-container" style="font-size:25px;">
        <template #header>
            <div class="header">
                <span>pet category</span>
            </div>
        </template>
        <div class="category">
            <div v-for="category in categories" :key="category" class="block">
                <!-- <span index="/pet/category/" class="demonstration">{{ category.categoryId }}</span> -->
                <router-link class="demonstration" style="font-size:25px;" :to="'/pet/category/' + category.categoryId">{{ category.categoryId }}</router-link>
                <el-image style="width: 100px; height: 100px" :src="category.url"/>
            </div>
        </div>
    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

.category .block {
  margin-top: 30px;
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  display: inline-block;
  width: 20%;
  box-sizing: border-box;
  vertical-align: top;
}
.category .block:last-child {
  border-right: none;
}
.category .demonstration {
  display: block;
  color: blue;
  font-size: 14px;
  margin-bottom: 20px;
}

</style>