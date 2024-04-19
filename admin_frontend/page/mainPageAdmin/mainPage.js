//引入根组件
import mainPage from '../src/page/mainPage/mainPage.vue'
//引入路由器
import router from '../../src/router'
//引入ElementUI-plus组件
import ElementPlus from 'element-plus'
//引入样式
import 'element-plus/dist/index.css'

import { createApp } from 'vue';
import { createPinia } from 'pinia'
const app = createApp(mainPage)
const pinia = createPinia()
app.use(ElementPlus)
app.use(pinia)
app.use(router).mount('#app')
