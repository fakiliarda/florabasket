package com.yaf.florabasket.model.order;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @date 18.05.2020
 */
public enum AcceptanceStatus {

    NOT_ASSIGNED("Not Assigned"),
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected");

    @Getter
    @Setter
    private final String text;

    AcceptanceStatus(final String text) {
        this.text = text;
    }

}
