package com.ut.util;

@FunctionalInterface
public interface StringConverter<T> {
    T convert(String argument);
}
