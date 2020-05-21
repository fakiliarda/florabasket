package com.yaf.florabasket.repository;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.Orders;
import com.yaf.florabasket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @date 18.05.2020
 */

@Repository("ordersRepository")
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByCartAndPaymentCompletedFalse(Cart cart);

    List<Orders> findAllBySellerAndPaymentCompletedTrue(User seller);

    List<Orders> findAllByClientAndPaymentCompletedTrue(User client);

    List<Orders> findAllByCourier(User courier);

}
