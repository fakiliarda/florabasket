package com.yaf.florabasket.security;

public enum Roles {

    CLIENT("CLIENT"),
    SELLER("SELLER"),
    COURIER("COURIER");

    private final String text;

    /**
     * @param text
     */
    Roles(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
