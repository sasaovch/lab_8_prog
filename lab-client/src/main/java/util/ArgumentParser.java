package util;



import java.util.function.Predicate;


public class ArgumentParser {
    // TODO Change exception handling
    public <T> T parseArgFromString(String readLine, Predicate<T> predicate, StringConverter<T> stringConverter) {
            T t;
            if ("".equals(readLine) || "NULL".equals(readLine)) {
                t = null;
            } else {
                try {
                    t = stringConverter.convert(readLine);
                } catch (IllegalArgumentException e) {
                    throw new NullPointerException("Неверные аргументы");
                }
            }
            if (predicate.test(t)) {
                return t;
            } else {
                throw new NullPointerException("Неверные аргументы");
            }

    }
    public boolean checkIfTheArgsEmpty(String readLine) {
        return "".equals(readLine);
    }





}
