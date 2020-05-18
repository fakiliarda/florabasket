package com.yaf.florabasket.repository;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ardafakili
 * @date 18.05.2020
 */

@Repository("ordersRepository")
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByCart(Cart cart);

}
