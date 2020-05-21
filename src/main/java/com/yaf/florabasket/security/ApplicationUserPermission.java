package com.yaf.florabasket.security;

import lombok.Getter;

/**
 *
 * @date 11.05.2020
 */
public enum ApplicationUserPermission {
    CLIENT_READ("client:read"),
    CLIENT_WRITE("client:write"),
    SELLER_READ("seller:read"),
    SELLER_WRITE("seller:write"),
    COURIER_READ("courier:read"),
    COURIER_WRITE("courier:write");

    @Getter
    private final String permision;

    ApplicationUserPermission(String permision) {
        this.permision = permision;
    }
}
