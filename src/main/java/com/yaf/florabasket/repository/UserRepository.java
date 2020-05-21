package com.yaf.florabasket.repository;

import com.yaf.florabasket.model.Flower;
import com.yaf.florabasket.model.Role;
import com.yaf.florabasket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @date 15.05.2020
 */

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findAllByFlowers(Flower flower);

    List<User> findAllByRoles(Role role);

}
