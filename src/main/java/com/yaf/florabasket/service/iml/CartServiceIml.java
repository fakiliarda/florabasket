package com.yaf.florabasket.service.iml;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.Orders;
import com.yaf.florabasket.model.User;
import com.yaf.florabasket.repository.CartRepository;
import com.yaf.florabasket.repository.OrdersRepository;
import com.yaf.florabasket.repository.UserRepository;
import com.yaf.florabasket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @date 18.05.2020
 */
@Service
public class CartServiceIml implements CartService {

    private final CartRepository cartRepository;
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartServiceIml(@Qualifier("cartRepository") CartRepository cartRepository, @Qualifier("ordersRepository") OrdersRepository ordersRepository, @Qualifier("userRepository") UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart findByUser(User client) {
        return cartRepository.findByClient(client);
    }

    @Override
    public Cart getCartWithTotalPrice() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Cart cart = findByUser(user);
        List<Orders> ordersList = ordersRepository.findAllByCartAndPaymentCompletedFalse(cart);

        Integer total = 0;
        for (Orders order: ordersList ) {
            total += (order.getQuantity() * Integer.parseInt(order.getFlower().getPrice().substring(1)));
        }
        cart.setTotal(total);
        return cart;
    }
}
