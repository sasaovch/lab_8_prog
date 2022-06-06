package com.ut.util;

import java.util.function.Predicate;

import com.ut.exeptions.IllegalValueException;

public class ArgumentParser {

    public <T> T parseArgFromString(String readLine, Predicate<T> predicate, StringConverter<T> stringConverter) throws IllegalValueException {
            T t;
                try {
                    t = stringConverter.convert(readLine);
                } catch (IllegalArgumentException e) {
                    throw new IllegalValueException();
                }
            if (predicate.test(t)) {
                return t;
            } else {
                throw new IllegalValueException();
            }
    }
}
