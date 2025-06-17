package com.vladproduction.oop_fundamentals.polymorphism;

public class Operations {

    public void operation(int a, int b){
        int res = a + b;
        System.out.println("operation (int)" + res);
    }

    public void operation(double a, double b){
        double res = a + b;
        System.out.println("operation (double)" + res);
    }

}
