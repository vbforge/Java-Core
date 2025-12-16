package com.vbforge.oop_pillars.polymorphism.casting.upcasting_downcasting;

public class BritishCat extends Cat{

    @Override
    void animalMethod() {
        System.out.print("BritishCat method + ");
        super.animalMethod();
    }

    @Override
    public void purr() {
        System.out.print("meow + ");
        super.purr();
    }
}
