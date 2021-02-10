package com.zsw.reflecet;

import lombok.SneakyThrows;

/**
 * @author ZhangShaowei on 2021/2/10 10:08
 */
public class ReflectiveTests {

    @SneakyThrows
    public static void main(String[] args) {
        testClass();

    }


    public static void testClass() {
        Integer[] arr = new Integer[3];

        System.out.println(arr.getClass().getName());

        System.out.println(new int[0].getClass().getName());


    }


}
