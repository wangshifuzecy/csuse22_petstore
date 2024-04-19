import request from '@/utils/request.js'

export const getCartService = () => {
    return request.get('/cart');
}

export const addCartService = (itemVo) =>{
    return request.post('/cart',itemVo)
}

export const updateCartService = (cart) =>{
    return request.post('/cart/update',cart)
}

export const removeCartService = (cart) =>{
    return request.post('/cart/remove',cart)
}

export const resetCartService = (cart) =>{
    return request.post('/cart/reset')
}