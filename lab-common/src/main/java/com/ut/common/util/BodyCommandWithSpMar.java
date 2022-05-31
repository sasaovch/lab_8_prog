package com.ut.common.util;


import com.ut.common.data.SpaceMarine;

public class BodyCommandWithSpMar extends BodyCommand {
    private static final long serialVersionUID = 740897631010321755L;
    private SpaceMarine spaceMarine;

    public BodyCommandWithSpMar(Object data, SpaceMarine spaceMarine) {
        super(data);
        this.spaceMarine = spaceMarine;
    }

    public SpaceMarine getSpaceMarine() {
        return spaceMarine;
    }
}
