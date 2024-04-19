package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.vo.ItemVO;

import java.util.List;

public interface CatalogService {
    CommonResponse<List<Category>> getCategoryList();
    CommonResponse<Category> getCategory(String categoryId);
    CommonResponse<List<Product>> getProductByCategory(String categoryId);
    CommonResponse<Product> getProductById(String productId);
    CommonResponse<List<ItemVO>> getItemByProductId(String productId);
    CommonResponse<ItemVO> getItemByitemId(String itemId);
    CommonResponse<List<ItemVO>> getItemBySearch(String searchString);
    CommonResponse<List<String>> getAllProductNames();
}