package com.yaf.florabasket.repository;

import com.yaf.florabasket.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @date 15.05.2020
 */

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {


    Role findByRole(String role);

}
