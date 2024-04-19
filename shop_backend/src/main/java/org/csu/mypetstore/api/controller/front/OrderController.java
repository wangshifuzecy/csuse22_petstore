package org.csu.mypetstore.api.controller.front;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Order;
import org.csu.mypetstore.api.service.OrderService;
import org.csu.mypetstore.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public CommonResponse addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping
    public CommonResponse getOrdersByName(@RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return orderService.getOrders(username);
    }
}
