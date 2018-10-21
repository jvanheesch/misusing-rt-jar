package com.github.jvanheesch;

import org.junit.jupiter.api.Test;
import qj.util.lang.DynamicClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoadedByBootstrapClassLoaderTest {

    /**
     * If we add '-Xbootclasspath/a:/home/joris/projects/examples/misusingrtjar/target/classes/' to VM options,
     * then this test will pass.
     */
    @Test
    void class_reloading_example() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        assertThat(Accessor.class.getPackage())
                .withFailMessage("Accessor should be in same package as this class - loaded by same class loader!")
                .isEqualTo(this.getClass().getPackage());

        Class<LoadedByBootstrapClassLoader> originalClass = LoadedByBootstrapClassLoader.class;

        assertThat(Accessor.class.getPackage())
                .withFailMessage("Accessor should not be in same package as originalClass: " +
                        "originalClass is loaded by BootstrapClassLoader!")
                .isNotEqualTo(originalClass.getPackage());

        assertThatThrownBy(() -> new Accessor().access(originalClass))
                .withFailMessage("Accessor should not be in same package as originalClass, therefore can't use package-access contructor!")
                .isInstanceOf(IllegalAccessException.class);

        DynamicClassLoader dynamicClassLoader = new DynamicClassLoader("target/classes", "target/test-classes");
        Class<?> reloadedClass = dynamicClassLoader
                .load("com.github.jvanheesch.LoadedByBootstrapClassLoader");

        assertThat(reloadedClass)
                .withFailMessage("Different ClassLoaders => different classes!")
                .isNotEqualTo(originalClass);

        assertThatThrownBy(() -> new Accessor().access(reloadedClass))
                .withFailMessage("Accessor should not be in same package as reloadedClass (different ClassLoaders!), therefore can't use package-access contructor!")
                .isInstanceOf(IllegalAccessException.class);

        Class<?> reloadedAccessorClass = dynamicClassLoader
                .load("com.github.jvanheesch.Accessor");

        // Can't cast to Accessor: different ClassLoaders => different classes!
        Object reloadedAccessor = reloadedAccessorClass.newInstance();

        Method accessMethod = Arrays.stream(reloadedAccessorClass.getDeclaredMethods())
                .filter(method -> method.getName().contains("access"))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Method 'access' not found!"));

        Object constructed = accessMethod.invoke(reloadedAccessor, reloadedClass);

        assertThat(constructed)
                .isNotInstanceOf(originalClass)
                .isInstanceOf(reloadedClass);
    }
}
