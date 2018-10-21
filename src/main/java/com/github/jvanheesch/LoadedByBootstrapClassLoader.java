package com.github.jvanheesch;

public class LoadedByBootstrapClassLoader {
    LoadedByBootstrapClassLoader() {
        System.out.println("Only classes in same package can access this constructor!");
    }
}
