package com.vbforge.design_principles.patterns.constructor_chaines.constructor_chain02;

public class Employee extends Person{
    Employee(String name){
        super(name);
        System.out.println("Employee constructor name: " + name);
    }
}
