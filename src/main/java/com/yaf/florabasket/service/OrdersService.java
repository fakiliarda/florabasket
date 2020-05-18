package com.yaf.florabasket.service;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.Orders;

import java.util.List;

/**
 * @author ardafakili
 * @date 18.05.2020
 */
public interface OrdersService {

    void createOrder (Orders orders);

    List<Orders> findAllByCart(Cart cart);

}
