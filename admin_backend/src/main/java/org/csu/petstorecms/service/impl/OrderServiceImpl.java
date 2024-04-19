package org.csu.petstorecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.petstorecms.DAO.AdminMapper;
import org.csu.petstorecms.DAO.OrderMapper;
import org.csu.petstorecms.common.CommonResponse;
import org.csu.petstorecms.entity.Admin;
import org.csu.petstorecms.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    AdminMapper adminMapper;

    public CommonResponse<Object> getAllOrderlist()
    {
        List<Order> ordersList=orderMapper.selectList(null);
        System.out.println(ordersList);
        if(ordersList.size()==0){
            return CommonResponse.createForFailure("无订单信息");
        }
        return CommonResponse.createForSuccess(ordersList);
    }

    public CommonResponse<Object> addOrder(Order order) {
        Order order1=orderMapper.selectById(order.getOrderid());
        if(order1!=null){
            return CommonResponse.createForFailure("订单已存在");
        }
        else{
            orderMapper.insert(order);
            return CommonResponse.createForSuccessMessage("添加成功");
        }
    }

    public CommonResponse<Object> updateOrder(Order order) {
        Order order1=orderMapper.selectById(order.getOrderid());
        if(order1!=null){
            orderMapper.updateById(order);
            return CommonResponse.createForSuccessMessage("修改成功");
        }
        else{
            return CommonResponse.createForFailure("修改失败");
        }
    }

    public CommonResponse<Object> removeOrder(int orderid) {
        Order order=orderMapper.selectById(orderid);
        if(order!=null){
            orderMapper.deleteById(orderid);
            return CommonResponse.createForSuccessMessage("删除成功");
        }
        else{
            return CommonResponse.createForFailure("删除失败");
        }
    }

    public CommonResponse<Object> getSomeOrder(String username) {
        List<Order> ordersList=orderMapper.selectList(null);
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<Admin>();
        queryWrapper.eq("username",username);
        Admin admin=adminMapper.selectOne(queryWrapper);
        String manageName=admin.getRealName();
        List<Order> result=new ArrayList<>();
        for(int i=0;i<ordersList.size();i++){
            String firstName=ordersList.get(i).getBilltofirstname();
            String lastName=ordersList.get(i).getBilltolastname();
            String name=lastName+firstName;
            if(name.equals(manageName)){
                result.add(ordersList.get(i));
            }
        }
        return CommonResponse.createForSuccess(result);
    }
}
