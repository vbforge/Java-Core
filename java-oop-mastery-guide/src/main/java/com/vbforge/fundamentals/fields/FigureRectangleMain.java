package com.vbforge.fundamentals.fields;

public class FigureRectangleMain {
    public static void main(String[] args) {

        FigureRectangle rectangle = new FigureRectangle(2,5);
        int calcArea = rectangle.calcArea();
        System.out.println("calcArea = " + calcArea); //10

        FigureRectangle rectangle2 = new FigureRectangle(2,5);
        rectangle2.setWidth(-2);
        int calcArea2 = rectangle2.calcArea();
        System.out.println("calcArea2 = " + calcArea2); //5

    }
}
