import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

import PetCategoryVue from '@/views/pet/PetCategory.vue'
import PetProductVue from  '@/views/pet/PetProduct.vue'
import PetItemVue from '@/views/pet/PetItem.vue'
import PetSearchVue from '@/views/pet/PetSearch.vue'
import CartVue from '@/views/cart/Cart.vue'
import AccountInfoVue from '@/views/account/AccountInfo.vue'
import AccountResetPasswordVue from '@/views/account/AccountResetPassword.vue'
import OrderVue from '@/views/order/Order.vue'

//定义路由关系
const routes = [
    { path: '/login', component: LoginVue },
    {
      path: '/', component: LayoutVue,redirect:'/pet/category', children: [
            { path: '/pet/category', component: PetCategoryVue , name: 'categories'},
            { path: '/pet/category/:id', component: PetProductVue, name: 'products'},
            { path: '/pet/product/:id', component: PetItemVue , name: 'items'},
            { path: '/pet/search/:text', component: PetSearchVue , name: 'search'},
            { path: '/cart', component: CartVue , name: 'carts'},
            { path: '/account/info', component: AccountInfoVue },
            { path: '/account/resetPassword', component: AccountResetPasswordVue },
            { path: '/order', component: OrderVue }
        ]
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

//导出路由
export default router