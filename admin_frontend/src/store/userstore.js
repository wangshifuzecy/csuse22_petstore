import { defineStore } from "pinia";
import { ref, reactive } from "vue";
import axios from 'axios'
export const useUserStore = defineStore('currentUser', () => {
  //声明数据
  const currentUserInfo = ref({})
  const username = ''
  const password = ''
  //声明操作数据的方法
  const setUserInfo = (userInfo) =>{
    currentUserInfo.value = userInfo
  }

  const getUserInfo = async () => {
    await axios({
      method:'get',
      url:'http://localhost:8080/userinfo',
      headers:{
        'Authorization': `${localStorage.getItem("token")}`,
        'Content-Type': 'application/json'
      },
    }).then((result)=>{
      if(result.data.status === 0){
        currentUserInfo.value = result.data.data 
      }
    })
  }

  //声明getters相关
  return {
    currentUserInfo,
    setUserInfo,
    getUserInfo
  }
})