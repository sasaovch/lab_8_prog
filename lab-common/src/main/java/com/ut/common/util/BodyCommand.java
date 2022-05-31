package com.ut.common.util;

import java.io.Serializable;

public class BodyCommand implements Serializable {
    private static final long serialVersionUID = 8097101921261015809L;
    private Object data;

    public BodyCommand(Object data) {
        this.data = data;
    }

    public BodyCommand() {
    }

    public Object getData() {
        return data;
    }

}
