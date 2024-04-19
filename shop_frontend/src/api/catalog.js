import request from '@/utils/request.js'

export const getAllCategoryService = ()=>{
    return request.get('/catalog/categories');
}

export const getProductsByCategoryIdService = (categoryId)=>{
    return request.get(`/catalog/categories/${categoryId}/products`)
}

export const getItemsByProductIdService = (productId)=>{
    return request.get(`/catalog/products/${productId}/items`)
}

export const getItemsBySearchService = (searchString)=>{
    return request.post('/catalog/search',searchString)
}

export const getAllProductNamesService = ()=>{
    return request.get('/catalog/products/names')
}

//return itemVO
export const getItemByItemIdService = (itemId)=>{
    return request.get('/catalog/items/' + itemId)
}