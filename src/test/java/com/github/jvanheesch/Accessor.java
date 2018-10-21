package com.github.jvanheesch;

public class Accessor {
    public <T> T access(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }
}
