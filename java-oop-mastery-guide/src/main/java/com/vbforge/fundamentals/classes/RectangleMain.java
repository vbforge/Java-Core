package com.vbforge.fundamentals.classes;

public class RectangleMain {
    public static void main(String[] args) {

        Rectangle rectangle = new Rectangle(10,5);
        int calcArea = rectangle.calcArea();
        System.out.println("calcArea = " + calcArea);

    }
}
