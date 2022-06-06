package com.ut.client;

import com.ut.gui.MainJFrame;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws Exception {
        MainJFrame.main(args);
    }
}
