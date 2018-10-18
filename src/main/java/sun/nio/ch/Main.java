package sun.nio.ch;

import java.io.FileDescriptor;

/**
 * https://stackoverflow.com/questions/20331018/subclassing-sun-class-in-same-package-gives-illegalaccesserror
 */
public class Main {
    // java -Xbootclasspath/a:/home/joris/projects/examples/misusingrtjar/target/classes/ sun.nio.ch.Main
    public static void main(String[] args) throws ClassNotFoundException {
        new MySourceChannel(null, FileDescriptor.in);
    }
}
