package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Order;

import java.util.List;

public interface OrderService {
    CommonResponse addOrder(Order order);
    CommonResponse<List<Order>> getOrders(String usernme);
}
