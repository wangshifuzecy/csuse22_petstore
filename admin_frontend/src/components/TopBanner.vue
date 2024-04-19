<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header" style="text-align: center;">
      <h2>{{ hourMsg }},尊敬的{{ username }}</h2>
    </div>
    
  </div>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue'
const time = new Date()
const hour = time.getHours()
const hourMsg = ref('早上好')
const setHourMsg = function() {
  if(hour >= 0 && hour < 12){
    hourMsg.value = '早上好'
  }else if(hour >= 12 && hour <= 18){
    hourMsg.value = '下午好'
  }else{
    hourMsg.value = '晚上好'
  }
}

//接收父组件的数据
const props = defineProps({
  username:String,
})
const username = localStorage.getItem("username")
onBeforeMount(() => {
  setHourMsg()
})


</script>

<style>
</style>