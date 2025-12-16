package com.vbforge.fundamentals.fields;

public class FigureRectangle {

    private int width;
    private int height;

    public FigureRectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width <= 0) {
            this.width = 1;
        } else {
            this.width = width;
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int calcArea() {
        return this.width * this.height; //params of this object
    }

}



