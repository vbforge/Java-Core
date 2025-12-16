package com.vbforge.oop_pillars.polymorphism.binding.early_binding;

public class Insurance {
    public static final int LOW = 100;
    public int getPremium() {
        return LOW;
    }
    public static String getCategory() {
        return "Insurance";
    }
}
