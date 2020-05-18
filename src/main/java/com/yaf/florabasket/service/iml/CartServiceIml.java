package com.yaf.florabasket.service.iml;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.User;
import com.yaf.florabasket.repository.CartRepository;
import com.yaf.florabasket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ardafakili
 * @date 18.05.2020
 */
@Service
public class CartServiceIml implements CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartServiceIml(@Qualifier("cartRepository") CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart findByUser(User client) {
        return cartRepository.findByClient(client);
    }
}
