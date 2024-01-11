package com.billenius.VisibleObjects;

public enum Side {
    LEFT(0), RIGHT(1);

    private final int value;

    Side(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }

}
