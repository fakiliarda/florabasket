package com.yaf.florabasket.service.iml;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.Orders;
import com.yaf.florabasket.model.User;
import com.yaf.florabasket.repository.CartRepository;
import com.yaf.florabasket.repository.OrdersRepository;
import com.yaf.florabasket.repository.UserRepository;
import com.yaf.florabasket.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ardafakili
 * @date 18.05.2020
 */

@Service("ordersService")
public class OrdersServiceIml implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrdersServiceIml(@Qualifier("ordersRepository") OrdersRepository ordersRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void createOrder(Orders orders) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        orders.setCart(cartRepository.findByClient(user));
        orders.setClient(user);
        ordersRepository.save(orders);
    }

    @Override
    public List<Orders> findAllByCart(Cart cart) {
        return ordersRepository.findAllByCart(cart);
    }
}
