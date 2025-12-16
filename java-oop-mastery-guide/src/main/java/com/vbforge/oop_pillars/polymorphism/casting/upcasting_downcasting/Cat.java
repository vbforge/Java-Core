package com.vbforge.oop_pillars.polymorphism.casting.upcasting_downcasting;

public class Cat extends Animal{

    @Override
    void animalMethod() {
        System.out.print("Cat method + ");
        super.animalMethod();
    }

    public void purr(){
        System.out.println("purrrrrrr");
    }
}
