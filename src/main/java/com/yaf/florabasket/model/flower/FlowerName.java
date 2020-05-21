package com.yaf.florabasket.model.flower;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @date 20.05.2020
 */
public enum FlowerName {

    ORKID("Orkid"),
    ACER_BOMSAI("Acer"),
    KRIZANTEM("Krizantem"),
    DAISY("Daisy"),
    GUZMANIA("Guzmania"),
    BAYKUS("Baykus"),
    GLASSELLA("Glassella"),
    TERRARIUM("Terrarium"),
    LILIUM("Lilium"),
    LITTLE_ITALY("Little Italy"),
    KALENCHO("Kalencho"),
    FLOWER_ARRANGEMENT("Flower Arrangement"),
    PORTO("Gift Box"),
    ROSE("Red Rose");

    @Getter
    @Setter
    private final String text;

    FlowerName(final String text) {
        this.text = text;
    }

}
