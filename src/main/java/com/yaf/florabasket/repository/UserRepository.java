package com.yaf.florabasket.repository;

import com.yaf.florabasket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ardafakili
 * @date 15.05.2020
 */

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
