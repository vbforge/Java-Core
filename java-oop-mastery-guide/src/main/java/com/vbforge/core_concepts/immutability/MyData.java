package com.vbforge.core_concepts.immutability;

public final class MyData {

    private final String name;

    public MyData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
