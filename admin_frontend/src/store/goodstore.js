import { defineStore } from "pinia";
import { ref, reactive } from "vue";
import axios from 'axios'
export const useGoodStore = defineStore('currentGoodsList', () => {
  //声明数据
  const currentGoodsList = ref([])
  //声明操作数据的方法
  //异步方法
  const serverURLUpdate = 'http://localhost:8080/itemlist'
  const getGoodsList = async () => {
    console.log("发送商品请求");
     await axios({
      method:'get',
      url:serverURLUpdate,
      headers:{
        'Authorization': `${localStorage.getItem("token")}`, // 使用Bearer token的方式
        'Content-Type': 'application/json'
      },
    }).then((result) => {
      console.log(result);
      //请求商品列表成功
      if(result.data.status === 0){
        console.log("请求成功");
        for(let receiveData of result.data.data){
          receiveData.img = `https://mypetstore-csu22.oss-cn-wuhan-lr.aliyuncs.com/product/${receiveData.img}`
        }
        currentGoodsList.value = result.data.data
      }else{
        //请求商品列表失败
        console.log("请求失败");
    }
  }).catch(function(error){
  console.log(error);
  })
  }
  //声明getters相关
  return {
    currentGoodsList,
    getGoodsList
  }
})