package com.vbforge.design_principles.patterns.constructor_chaines.constructor_chain04;

public class Player {
    public Player(String name) {
        System.out.println("Player constructor name: " + name);
    }

    //not defined default constructor.
    public Player() {
    }
}
