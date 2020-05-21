package com.yaf.florabasket.service;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.Orders;
import com.yaf.florabasket.model.User;

import java.util.List;

/**
 *
 * @date 18.05.2020
 */
public interface OrdersService {

    void createOrder (Orders orders);

    void updateOrderAssigned(Orders order);

    void updateOrderAccepted(Orders order);

    void updateOrderDelivered(Orders order);

    void updateOrderRejected(Orders order);

    Orders findById (Long id);

    List<Orders> findAllByCartAndPaymentCompletedFalse(Cart cart);

    List<Orders> findAllByPaymentCompletedAndSeller(User seller);

    //My orders tab
    List<Orders> findAllByPaymentCompletedAndClient(User client);

    List<Orders> findAllByCourier(User courier);

    void updateOrdersPaymentCompletedAndDeliveryStatus();

}
