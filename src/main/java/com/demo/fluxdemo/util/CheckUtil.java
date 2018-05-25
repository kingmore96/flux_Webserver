package com.demo.fluxdemo.util;

import com.demo.fluxdemo.exception.CheckException;

import java.util.stream.Stream;

public class CheckUtil {
    /**
     * 校验名称，如果是admin或guanliyuan则抛出异常
     * @param value
     */
    public static void checkName(String value) {
        String[] strs = {"admin","guanliyuan"};

        Stream.of(strs).filter(name -> name.equalsIgnoreCase(value))
                .findAny().ifPresent(name -> {
                    throw new CheckException("name",value);
        });
    }
}
