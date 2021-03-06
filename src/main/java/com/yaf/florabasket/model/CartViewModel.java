package com.yaf.florabasket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ardafakili
 * @date 18.05.2020
 */

@Data
@AllArgsConstructor
public class CartViewModel {

    private Flower flower;
    private Orders order;
    
}
