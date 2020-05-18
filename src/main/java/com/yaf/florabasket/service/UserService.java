package com.yaf.florabasket.service;

import com.yaf.florabasket.model.User;

import java.util.List;

/**
 * @author ardafakili
 * @date 15.05.2020
 */
public interface UserService {

    List<User> listAll();

    User getById(Long id);

    User findByEmail(String email);

    void saveClientUser (User user);

    void saveSellerUser (User user);

    void saveCourierUser (User user);

    void delete(Long id);

}
