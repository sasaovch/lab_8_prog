package com.ut.common.util;

public interface ConvertVR<R> {
    R convert(String t, R defaultValue);
}
