package com.yaf.florabasket.repository;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @date 18.05.2020
 */

@Repository("cartRepository")
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByClient(User user);

}
