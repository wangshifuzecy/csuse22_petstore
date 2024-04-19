package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Cart;
import org.csu.mypetstore.api.vo.ItemVO;

import java.util.List;

public interface CartSerivce {
    CommonResponse<List<Cart>> getCartByUsername(String username);
    CommonResponse addCart(ItemVO itemVO, String username);
    CommonResponse updateCart(Cart cart);
    CommonResponse removeCart(Cart cart);
    CommonResponse resetCart(String username);
    CommonResponse<Boolean> checkCartByUsername(String itemId, String username);
}
