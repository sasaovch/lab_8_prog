package com.ut.common.util;


import com.ut.common.data.User;

public interface UserManagerInt {
    boolean checkIn(User user);
    User register(User user);
    ResultStatusWorkWithColl authenticate(User user);
}
