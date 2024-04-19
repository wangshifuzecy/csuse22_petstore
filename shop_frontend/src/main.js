import './assets/main.scss'
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import App from './App.vue'
import 'element-plus/dist/index.css'
import router from '@/router'
import {createPinia} from 'pinia'
import {createPersistedState} from 'pinia-persistedstate-plugin'

const pinia = createPinia()
const app = createApp(App);
const persist = createPersistedState()

pinia.use(persist)
app.use(router)
app.use(ElementPlus)
app.use(pinia)
app.mount('#app')