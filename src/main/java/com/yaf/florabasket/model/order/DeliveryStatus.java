package com.yaf.florabasket.model.order;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @date 18.05.2020
 */
public enum DeliveryStatus {

    WAITING("WAITING"),
    ON_ROAD("ON ROAD"),
    DELIVERED("DELIVERED");

    @Getter
    @Setter
    private final String text;

    DeliveryStatus(final String text) {
        this.text = text;
    }

}
