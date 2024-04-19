package org.csu.mypetstore.api.controller.front;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Cart;
import org.csu.mypetstore.api.service.CartSerivce;
import org.csu.mypetstore.api.util.JwtUtil;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartSerivce cartSerivce;

    @GetMapping
    public CommonResponse<List<Cart>> getCartByUsername(@RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return cartSerivce.getCartByUsername(username);
    }

    //if item exist in cart
    @PostMapping("/check")
    public CommonResponse<Boolean> checkCart(@RequestBody ItemVO itemVO, @RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        String itemId = itemVO.getItemId();
        return cartSerivce.checkCartByUsername(itemId,username);
    }

    @PostMapping
    public CommonResponse addCart(@RequestBody ItemVO itemVO, @RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return cartSerivce.addCart(itemVO, username);
    }

    @PostMapping("/update")
    public CommonResponse updateCart(@RequestBody Cart cart){
        return cartSerivce.updateCart(cart);
    }

    @PostMapping("/remove")
    public CommonResponse removeCart(@RequestBody Cart cart){
        return cartSerivce.removeCart(cart);
    }

    @PostMapping("/reset")
    public CommonResponse resetCart(@RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return cartSerivce.resetCart(username);
    }
}
