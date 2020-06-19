package com.ndsl.db.bun133.db;

import com.ndsl.db.bun133.util.SmartCast;

public class DBObject<T> {
    private T t_value;
    public boolean isLocked=false;

    public DBObject(T t){
        set(t);
    }

    public DBObject(T t,boolean lockState){
        set(t);
        setLock(lockState);
    }

    public DBObject set(T t){
        this.t_value = t;
        return this;
    }

    public T get(){
        return t_value;
    }

    public DBObject setLock(boolean lockState){
        if(!isLocked){
            isLocked=lockState;
        }
        return this;
    }

    @Override
    public String toString() {
        return SmartCast.toString(t_value);
    }

    public String getAsString(){
        return toString();
    }
}
