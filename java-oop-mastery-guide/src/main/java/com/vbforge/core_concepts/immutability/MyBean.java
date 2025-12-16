package com.vbforge.core_concepts.immutability;

public final class MyBean {

    private final String[] data;

    public MyBean(String[] data) {
        this.data = data;
    }

    public String[] getData() {
        return data;
    }
}
