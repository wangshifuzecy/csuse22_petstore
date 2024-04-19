import request from '@/utils/request.js'

export const getAllOrderService = ()=>{
    return request.get('/order');
}

export const addOrderService = (order)=>{
    return request.post('/order',order);
}