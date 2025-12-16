package com.vbforge.oop_pillars.inheritance.advanced;

public class SimpleWorker extends Worker{

    @Override
    public B work(){
        return new B();
    }

}
