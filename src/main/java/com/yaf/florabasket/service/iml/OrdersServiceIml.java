package com.yaf.florabasket.service.iml;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.Orders;
import com.yaf.florabasket.model.User;
import com.yaf.florabasket.model.order.AcceptanceStatus;
import com.yaf.florabasket.model.order.DeliveryStatus;
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
 *
 * @date 18.05.2020
 */

@Service("ordersService")
public class OrdersServiceIml implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrdersServiceIml(@Qualifier("ordersRepository") OrdersRepository ordersRepository, @Qualifier("userRepository") UserRepository userRepository, @Qualifier("cartRepository") CartRepository cartRepository) {
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
        orders.setPaymentCompleted(false);
        ordersRepository.save(orders);
    }

    @Override
    public void updateOrderAssigned(Orders order) {
        order.setStatus(AcceptanceStatus.PENDING.toString());
        ordersRepository.save(order);
    }

    @Override
    public void updateOrderAccepted(Orders order) {
        order.setStatus(AcceptanceStatus.ACCEPTED.toString());
        order.setDeliveryStatus(DeliveryStatus.ON_ROAD.getText());
        ordersRepository.save(order);
    }

    @Override
    public void updateOrderDelivered(Orders order) {
        order.setStatus(AcceptanceStatus.ACCEPTED.toString());
        order.setDeliveryStatus(DeliveryStatus.DELIVERED.toString());
        ordersRepository.save(order);
    }

    @Override
    public void updateOrderRejected(Orders order) {
        order.setStatus(AcceptanceStatus.REJECTED.toString());
        ordersRepository.save(order);
    }

    @Override
    public Orders findById(Long id) {
        return ordersRepository.findById(id).get();
    }

    @Override
    public List<Orders> findAllByCartAndPaymentCompletedFalse(Cart cart) {
        return ordersRepository.findAllByCartAndPaymentCompletedFalse(cart);
    }

    @Override
    public List<Orders> findAllByPaymentCompletedAndSeller(User seller) {
        return ordersRepository.findAllBySellerAndPaymentCompletedTrue(seller);
    }

    @Override
    public List<Orders> findAllByPaymentCompletedAndClient(User client) {
        return ordersRepository.findAllByClientAndPaymentCompletedTrue(client);
    }

    @Override
    public List<Orders> findAllByCourier(User courier) {
        return ordersRepository.findAllByCourier(courier);
    }

    @Override
    public void updateOrdersPaymentCompletedAndDeliveryStatus() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Cart cart = cartRepository.findByClient(user);
        List<Orders> ordersList = findAllByCartAndPaymentCompletedFalse(cart);

        for (Orders order : ordersList) {
            order.setPaymentCompleted(true);
            order.setDeliveryStatus(DeliveryStatus.WAITING.toString());
            order.setStatus(AcceptanceStatus.NOT_ASSIGNED.getText());
            ordersRepository.save(order);
        }

    }
}
