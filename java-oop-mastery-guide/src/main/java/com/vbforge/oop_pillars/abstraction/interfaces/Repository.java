package com.vbforge.oop_pillars.abstraction.interfaces;

public interface Repository<T> {

    T create(T obj);
    T read();
    T update(T candidate, T old);
    void delete();
}
