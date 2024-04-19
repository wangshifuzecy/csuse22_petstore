package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Cart;
import org.csu.mypetstore.api.entity.ItemInventory;
import org.csu.mypetstore.api.persistence.CartMapper;
import org.csu.mypetstore.api.persistence.ItemInventoryMapper;
import org.csu.mypetstore.api.service.CartSerivce;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartSerivce {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ItemInventoryMapper itemInventoryMapper;
    @Override
    public CommonResponse<List<Cart>> getCartByUsername(String username) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", username);
        List<Cart> carts = cartMapper.selectList(wrapper);
        if(carts == null){
            return CommonResponse.createForError("no cart whose username is " + username);
        }
        return CommonResponse.createForSuccess(carts);
    }

    @Override
    public CommonResponse addCart(ItemVO itemVO, String username) {
        ItemInventory itemInventory = itemInventoryMapper.selectById(itemVO.getItemId());
        System.out.println(itemInventory);
        if(itemInventory.getQuantity() == 0){
            return CommonResponse.createForError(itemVO.getItemId()+" inventory sell out");
        }else{
            itemInventory.setQuantity(itemInventory.getQuantity() - 1);
            itemInventoryMapper.updateById(itemInventory);
        }
//        System.out.println(itemVO);
        Cart cart = new Cart();
        // 重要信息
        cart.setNumber(1);
        cart.setUserId(username);
        cart.setProductId(itemVO.getProductId());
        cart.setItemId(itemVO.getItemId());
        // 次要内容
//        cart.setListPrice(itemVO.getListPrice());
//        cart.setItemAttribute(itemVO.getAttribute1());
//        cart.setProductName(itemVO.getProductName());
//        cart.setProductImage(itemVO.getProductImage());
//        System.out.println(cart);
        int result = cartMapper.insert(cart);
        if(result == 1){
            return CommonResponse.createForSuccessMessage("insert success");
        }else{
            return CommonResponse.createForError("insert error");
        }
    }

    @Override
    public CommonResponse updateCart(Cart cart) {
        ItemInventory itemInventory = itemInventoryMapper.selectById(cart.getItemId());
        if(itemInventory.getQuantity() == 0){
            return CommonResponse.createForError(cart.getItemId()+" inventory sell out");
        }else{
            itemInventory.setQuantity(itemInventory.getQuantity() - 1);
            itemInventoryMapper.updateById(itemInventory);
        }
        int result = cartMapper.updateById(cart);
        if(result == 1){
            return CommonResponse.createForSuccessMessage("update cart success");
        }else{
            return CommonResponse.createForError("update cart error");
        }
    }

    @Override
    public CommonResponse removeCart(Cart cart){
        int result = cartMapper.deleteById(cart);
        ItemInventory itemInventory = itemInventoryMapper.selectById(cart.getItemId());
        itemInventory.setQuantity(itemInventory.getQuantity() + cart.getNumber());
        itemInventoryMapper.updateById(itemInventory);
        if(result == 0){
            return CommonResponse.createForError("delete cart error");
        }else{
            return CommonResponse.createForSuccessMessage("delete cart success");
        }
    }

    @Override
    public CommonResponse resetCart(String username) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", username);
        int result = cartMapper.delete(wrapper);
//        if(result == 0){
//            return CommonResponse.createForError("no cart whose username is " + username);
//        }
        return CommonResponse.createForSuccess("reset cart ok");
    }

    @Override
    public CommonResponse<Boolean> checkCartByUsername(String itemId, String username) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", username);
        wrapper.eq("itemid", itemId);
        return  CommonResponse.createForSuccess(cartMapper.selectList(wrapper).isEmpty());
    }
}
