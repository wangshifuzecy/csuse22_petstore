package org.csu.petstorecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.petstorecms.DAO.*;
import org.csu.petstorecms.common.CommonResponse;
import org.csu.petstorecms.entity.*;
import org.csu.petstorecms.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ItemServiceImpl {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private AdminMapper adminMapper;

    public List<ItemVO> getItemList(){
        List<Item> itemList=itemMapper.selectList(null);
        List<ItemVO> itemVOList=new ArrayList<>();

        for(int i=0;i<itemList.size();i++){
            ItemVO itemVO=new ItemVO();


            Product product=productMapper.selectById(itemList.get(i).getProductid());


            Inventory inventory=inventoryMapper.selectById(itemList.get(i).getItemid());

            setOneVo(itemVO,itemList.get(i),product,inventory);


            itemVOList.add(itemVO);
        }
        return itemVOList;
    }
    public void setOneVo(ItemVO itemVO,Item item,Product product,Inventory inventory){
        itemVO.setItemid(item.getItemid());
        itemVO.setProductid(item.getProductid());
        itemVO.setListprice(item.getListprice());
        itemVO.setUnitcost(item.getUnitcost());
        itemVO.setSupplier(item.getSupplier());
        itemVO.setStatus(item.getStatus());
        itemVO.setAttr1(item.getAttr1());

        itemVO.setCategoryid(product.getCategory());
        itemVO.setImg(product.getImg());
        itemVO.setDescn(product.getDescn());

        itemVO.setQuantity(inventory.getQty());
    }
    public Item VoToItem(ItemVO itemVO){
        Item item=new Item();

        item.setItemid(itemVO.getItemid());
        item.setProductid(itemVO.getProductid());
        item.setListprice(itemVO.getListprice());
        item.setUnitcost(itemVO.getUnitcost());
        item.setSupplier(itemVO.getSupplier());
        item.setStatus(itemVO.getStatus());
        item.setAttr1(itemVO.getAttr1());
        return item;
    }
    public Product VoToProduct(ItemVO itemVO){
        Product product=new Product();
        product.setDescn(itemVO.getDescn());
        product.setImg(itemVO.getImg());
        product.setCategory(itemVO.getCategoryid());
        product.setProductid(itemVO.getProductid());
        return product;
    }
    public Inventory VoToInventory(ItemVO itemVO){
        Inventory inventory=new Inventory();
        inventory.setItemid(itemVO.getItemid());
        inventory.setQty(itemVO.getQuantity());
        return inventory;
    }
    public boolean addItem(ItemVO itemVO){
        Item item =itemMapper.selectById(itemVO.getItemid());
        if(item!=null) return false;
        else {
            Item item1=VoToItem(itemVO);
            Product product=VoToProduct(itemVO);
            Inventory inventory=VoToInventory(itemVO);
            itemMapper.insert(item1);
//            if(productMapper.selectById(itemVO.getProductid())==null){
//                productMapper.insert(product);
//            }
            inventoryMapper.insert(inventory);
            return true;
        }

    }
    public CommonResponse<Object> removeItemById(String itemid){
        Item item=itemMapper.selectById(itemid);
        if( item!=null){
            itemMapper.deleteById(itemid);
            inventoryMapper.deleteById(itemid);
            return CommonResponse.createForSuccessMessage("删除成功");
        }
        else{
            return CommonResponse.createForFailure("删除失败");
        }
    }
    public CommonResponse<Object> updateItem(ItemVO itemVO){
        if(itemMapper.selectById(itemVO.getItemid())!=null){
            Item item=VoToItem(itemVO);
            Product product=VoToProduct(itemVO);
            Inventory inventory=VoToInventory(itemVO);
            itemMapper.updateById(item);
            product.setName(productMapper.selectById(itemVO.getProductid()).getName());
            productMapper.updateById(product);
            inventoryMapper.updateById(inventory);
            return CommonResponse.createForSuccessMessage("修改成功");
        }


        else{
            return CommonResponse.createForFailure("无该商品内容");
        }


    }

    public List<ItemVO> getSomeItem(String username) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<Admin>();
        queryWrapper.eq("username",username);
        Admin admin=adminMapper.selectOne(queryWrapper);
        int adminId=admin.getId();
        QueryWrapper<Item> queryWrapper1=new QueryWrapper<Item>();
        queryWrapper1.eq("supplier",adminId);
        List<Item> itemList=itemMapper.selectList(queryWrapper1);

        List<ItemVO> itemVOList=new ArrayList<>();

        for(int i=0;i<itemList.size();i++){
            ItemVO itemVO=new ItemVO();


            Product product=productMapper.selectById(itemList.get(i).getProductid());


            Inventory inventory=inventoryMapper.selectById(itemList.get(i).getItemid());

            setOneVo(itemVO,itemList.get(i),product,inventory);


            itemVOList.add(itemVO);
        }
        return itemVOList;

    }
}
