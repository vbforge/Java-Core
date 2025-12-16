package com.vbforge.oop_pillars.inheritance.basic.app02;

public class Analyst extends Employee{

    public Analyst(int id, String name, double salary, int age) {
        super(id, name, salary, age);
    }

    public double getAnnualBonus(){
//        return this.salary * .5;
        return super.salary * .5;
    }
}
