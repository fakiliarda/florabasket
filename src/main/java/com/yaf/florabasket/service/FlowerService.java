package com.yaf.florabasket.service;

import com.yaf.florabasket.model.Flower;
import com.yaf.florabasket.model.User;

import java.util.List;

/**
 *
 * @date 17.05.2020
 */
public interface FlowerService {

    List<Flower> listAll();

    List<Flower> listAllBySeller(User seller);

    Flower findById(Long id);

    List<Flower> findAllByColor(String color);

    List<Flower> findAllByCategory(String category);

    void addNewSeller(Flower flower);

    List<Flower> findAllByName(String value);
}
