package org.csu.mypetstore.api.controller.front;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.service.CatalogService;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/categories")
    @ResponseBody
    public CommonResponse<List<Category>> getCategoryList(){
        return catalogService.getCategoryList();
    }

    @GetMapping("/categories/{id}")
    @ResponseBody
    public CommonResponse<Category> getCategory(@PathVariable("id")String categoryId){
        //input: CATS, DOGS
        return catalogService.getCategory(categoryId);
    }

    @GetMapping("/categories/{id}/products")
    @ResponseBody
    public CommonResponse<List<Product>> getProductListByCategory(@PathVariable("id")String category){
        //input: CATS,DOGS
        return catalogService.getProductByCategory(category);
    }
    @GetMapping("/products/{id}")
    @ResponseBody
    public CommonResponse<Product> getProductById(@PathVariable("id")String productId){
        //input: AV-CB-01, FL-DSH-01
        return catalogService.getProductById(productId);
    }

    @GetMapping("/products/{id}/items")
    @ResponseBody
    public CommonResponse<List<ItemVO>> getItemListByProductId(@PathVariable("id")String productId){
        //input: AV-CB-01, FL-DSH-01
        return catalogService.getItemByProductId(productId);
    }

    @GetMapping("/items/{id}")
    @ResponseBody
    public CommonResponse<ItemVO> getItemByItemId(@PathVariable("id")String itemId){
        return catalogService.getItemByitemId(itemId);
    }

    @PostMapping("/search")
    @ResponseBody
    public CommonResponse<List<ItemVO>> getItemListBySearch(@RequestBody String searchString){
        return catalogService.getItemBySearch(searchString);
    }

    @GetMapping("/products/names")
    @ResponseBody
    public CommonResponse<List<String>> getProductNameList(){
        return catalogService.getAllProductNames();
    }
}
