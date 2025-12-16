package com.vbforge.oop_pillars.polymorphism.override.app02;

public class MainBaseDeriveApp {
    public static void main(String[] args) {
        Base base = new Base();
        Base derive = new Derive();

        base.show();
        derive.show();

        //output:
        /*Base
        Derive class*/

        Base derivedPartially = new DerivedPartially();
        derivedPartially.show();

        //output (superclass method show() is explicitly called in subclass show() method):
        /*Base
        DerivedPartially class*/
    }
}
