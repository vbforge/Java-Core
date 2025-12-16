package com.vbforge.oop_pillars.abstraction.abstract_classes.app03.simple_examples;

public abstract class MyAbstract {

    public abstract void printToConsole();

    public void doAction(){
        for (int i = 1; i <= 10; i++) {
            printToConsole();
        }
    }

}
