package com.vbforge.oop_pillars.inheritance.advanced;

public class CovariantMain {
    public static void main(String[] args) {

        Worker worker = new Worker();
        A a = worker.work();
        worker = new SimpleWorker();
        A work = worker.work(); //can`t take B
        System.out.println(work);

    }
}
