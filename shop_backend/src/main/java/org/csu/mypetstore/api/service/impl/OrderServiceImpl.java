package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Order;
import org.csu.mypetstore.api.persistence.OrderMapper;
import org.csu.mypetstore.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public CommonResponse addOrder(Order order) {
        int result = orderMapper.insert(order);
        if(result == 1){
            return CommonResponse.createForSuccessMessage("insert successfully");
        }else{
            return CommonResponse.createForError("insert error");
        }
    }

    @Override
    public CommonResponse<List<Order>> getOrders(String username) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", username);
        List<Order> orders = orderMapper.selectList(wrapper);
        if(orders == null){
            return CommonResponse.createForError("no order whose username is " + username);
        }
        return CommonResponse.createForSuccess(orders);
    }
}
