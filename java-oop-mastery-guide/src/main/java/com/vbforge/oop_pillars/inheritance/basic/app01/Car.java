package com.vbforge.oop_pillars.inheritance.basic.app01;

public class Car {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void show(){
        System.out.println("Car name: " + name);
    }
}
