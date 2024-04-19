package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Item;
import org.csu.mypetstore.api.entity.ItemInventory;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.persistence.CategoryMapper;
import org.csu.mypetstore.api.persistence.ItemInventoryMapper;
import org.csu.mypetstore.api.persistence.ItemMapper;
import org.csu.mypetstore.api.persistence.ProductMapper;
import org.csu.mypetstore.api.service.CatalogService;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper  productMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemInventoryMapper itemInventoryMapper;
    @Override
    public CommonResponse<List<Category>> getCategoryList() {
        List<Category> categoryList = categoryMapper.selectList(null);
        if(categoryList.isEmpty()){
            return CommonResponse.createForError("no category");
        }
        return CommonResponse.createForSuccess(categoryList);
    }

    @Override
    public CommonResponse<Category> getCategory(String categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if(category == null){
            return CommonResponse.createForError("no category whose categoryId is " + categoryId);
        }
        return CommonResponse.createForSuccess(category);
    }


    @Override
    public CommonResponse<List<Product>> getProductByCategory(String category) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category" , category);
        List<Product> productList = productMapper.selectList(queryWrapper);
        if(productList.isEmpty()){
            return CommonResponse.createForError("no products whose category is " + category);
        }
        return CommonResponse.createForSuccess(productList);
    }

    @Override
    public CommonResponse<Product> getProductById(String productId) {
        Product product = productMapper.selectById(productId);
        if(product == null){
            return CommonResponse.createForError("no product whose productId is " + productId);
        }
        return CommonResponse.createForSuccess(product);
    }

    @Override
    public CommonResponse<List<ItemVO>> getItemByProductId(String productId) {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productId",productId);
        List<Item> itemList = itemMapper.selectList(queryWrapper);
        if(itemList.isEmpty()){
            return CommonResponse.createForError("no item whose productId is " + productId);
        }
        Product product = productMapper.selectById(productId);
        List<ItemVO> itemVOList = new ArrayList<>();
        for(Item item : itemList){
            ItemVO itemVO = itemToItemVO(item,product);
            itemVOList.add(itemVO);
        }
        return CommonResponse.createForSuccess(itemVOList);
    }

    @Override
    public CommonResponse<ItemVO> getItemByitemId(String itemId) {
        Item item = itemMapper.selectById(itemId);
        if(item == null){
            return CommonResponse.createForError("no item whose itemId is " + itemId);
        }
        Product product = productMapper.selectById(item.getProductId());
        ItemVO itemVO = itemToItemVO(item,product);
//        System.out.println(itemVO);
        return CommonResponse.createForSuccess(itemVO);
    }


    @Override
    public CommonResponse<List<ItemVO>> getItemBySearch(String searchString) {
        searchString = searchString.replace("+", " ");
        QueryWrapper<Product> queryWrapperProduct = new QueryWrapper<>();
        //searchString ignore case and should part match
        queryWrapperProduct.like("name", "%" + searchString + "%");
        // Search by name (partial match)
        List<Product> productList = productMapper.selectList(queryWrapperProduct);
        List<String> productIds = productList.stream().map(Product::getProductId).collect(Collectors.toList());
        QueryWrapper<Item> queryWrapperItem = new QueryWrapper<>();
        queryWrapperItem.in("productid", productIds);
        // Search by productIds (can be more than one)
        List<Item> itemList = itemMapper.selectList(queryWrapperItem);

        List<ItemVO> itemVOList = new ArrayList<>();
        for(Item item : itemList){
            Product relatedProduct = productMapper.selectById(item.getProductId());
            ItemVO itemVO = itemToItemVO(item, relatedProduct);
            itemVOList.add(itemVO);
        }
        return CommonResponse.createForSuccess(itemVOList);
    }

    @Override
    public CommonResponse<List<String>> getAllProductNames() {
        List<Product> productList = productMapper.selectList(null);
        List<String> ret = new ArrayList<>();
        for(Product product : productList){
            ret.add(product.getName());
        }
        return CommonResponse.createForSuccess(ret);
    }

    private ItemVO itemToItemVO(Item item, Product product){
        ItemVO itemVO = new ItemVO();

        itemVO.setItemId(item.getItemId());
        itemVO.setStatus(item.getStatus());
        itemVO.setProductId(item.getProductId());
        itemVO.setListPrice(item.getListPrice());
        itemVO.setUnitCost(item.getUnitCost());
        itemVO.setSupplierId(item.getSupplierId());
        itemVO.setAttribute1(item.getAttribute1());
        itemVO.setAttribute2(item.getAttribute2());
        itemVO.setAttribute3(item.getAttribute3());
        itemVO.setAttribute4(item.getAttribute4());
        itemVO.setAttribute5(item.getAttribute5());

        itemVO.setCategoryId(product.getCategoryId());
        itemVO.setProductName(product.getName());
        itemVO.setProductDescription(product.getDescription());
        itemVO.setProductImage(product.getImage());

        ItemInventory itemInventory = itemInventoryMapper.selectById(item.getItemId());
        itemVO.setQuantity(itemInventory.getQuantity());

        return itemVO;
    }
}
