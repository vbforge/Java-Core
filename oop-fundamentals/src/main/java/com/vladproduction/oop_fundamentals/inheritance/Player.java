package com.vladproduction.oop_fundamentals.inheritance;

import com.vladproduction.oop_fundamentals.encapsulation.Person;

public class Player extends Person {

    private String team;
    private int number;

    public Player(String name, int age, String team, int number) {
        super(name, age);
        this.team = team;
        this.number = number;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format(super.toString(), team, number);
    }
}
