package com.yaf.florabasket.service;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.User;

/**
 *
 * @date 18.05.2020
 */
public interface CartService {

    Cart findByUser(User client);

    Cart getCartWithTotalPrice();

}
