package com.yaf.florabasket.service;

import com.yaf.florabasket.model.Flower;

import java.util.List;

/**
 * @author ardafakili
 * @date 17.05.2020
 */
public interface FlowerService {

    List<Flower> listAll();

    Flower findById(Long id);

}
