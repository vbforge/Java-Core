package com.vbforge.design_principles.patterns.constructor_chaines.constructor_chain03;

public class Vehicle {
    public Vehicle(){
        System.out.println("Vehicle default constructor");
    }

    public Vehicle(String name){
        System.out.println("Vehicle constructor name: " + name);
    }
}
