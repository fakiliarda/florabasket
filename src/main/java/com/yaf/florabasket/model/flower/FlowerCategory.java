package com.yaf.florabasket.model.flower;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ardafakili
 * @date 20.05.2020
 */
public enum FlowerCategory {

    BONSAI("Bonsai"),
    BOUQUET("Bouquet"),
    GIFT_BOX("Gift Box"),
    HOME_DECO("Home Deco"),
    PLANTS_WITH_POT("Plants With Pot");

    @Getter
    @Setter
    private final String text;

    FlowerCategory(String text) {
        this.text = text;
    }
}
