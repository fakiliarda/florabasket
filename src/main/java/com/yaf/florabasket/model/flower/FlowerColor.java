package com.yaf.florabasket.model.flower;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @date 20.05.2020
 */
public enum FlowerColor {

    RED("Red"),
    PURPLE("Purple"),
    WHITE("White"),
    YELLOW("Yellow"),
    BLACK("Black"),
    GRAY("Gray"),
    ORANGE("Orange"),
    BROWN("Brown"),
    BLUE("Brown");

    @Getter
    @Setter
    private final String text;

    FlowerColor(String text) {
        this.text = text;
    }
}
