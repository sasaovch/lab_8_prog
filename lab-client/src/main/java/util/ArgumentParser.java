package util;

import java.util.function.Predicate;

import exeptions.IllegalValueException;

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
    public boolean checkIfTheArgsEmpty(String readLine) {
        return "".equals(readLine);
    }
}
