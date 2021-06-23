package com.yw.webflux.example.function;

import org.junit.Test;

import java.util.function.Supplier;

/**
 * @author yangwei
 */
public class SupplierTest {
    @Test
    public void test01() {
        Supplier<String> supplier = () -> "Supplier lambda";
        System.out.println(supplier.get()); // Supplier lambda
    }
}
