//引入App
import App from './App.vue'
//引入路由器
import router from './router'
//引入ElementUI-plus组件
import ElementPlus from 'element-plus'
//引入样式
import 'element-plus/dist/index.css'

import { createApp } from 'vue';
import { createPinia } from 'pinia'
const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
app.use(ElementPlus)
app.use(router).mount('#app')
