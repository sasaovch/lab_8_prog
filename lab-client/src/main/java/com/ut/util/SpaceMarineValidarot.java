package com.ut.util;

import java.io.Serializable;

public final class SpaceMarineValidarot implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MIN_HEALTH = 1;
    private static final int MIN_HEARTCOUNT = 1;
    private static final int MAX_HEARTCOUNT = 3;
    private static final int MIN_MARINESCOUNT = 0;
    private static final int MAX_MARINESCOUNT = 1000;
    private static boolean isChapterNull;

    private SpaceMarineValidarot() {
        throw new UnsupportedOperationException();
    }

    public static boolean nameValidator(String name) {
        return (name != null && !"".equals(name));
    }

    public static boolean healthValidator(Integer health) {
        return health != null && health >= MIN_HEALTH;
    }

    public static boolean heartCountValidator(Integer heartCount) {
        return heartCount != null && heartCount >= MIN_HEARTCOUNT && heartCount <= MAX_HEARTCOUNT;
    }

    public static boolean yValidator(Long y) {
        return (y != null);
    }

    public static boolean chapterValidator(String chapter) {
        if (chapter == null || "".equals(chapter)) {
            isChapterNull = true;
        } else {
            isChapterNull = false;
        }
        return true;
    }
    public static boolean parentLegiolValidator(String parentLegion) {
        return true;
    }
    public static boolean marinesCountValidator(Integer marinesCount) {
        if (isChapterNull) {
            return true;
        }
        return marinesCount != null && marinesCount >= MIN_MARINESCOUNT && marinesCount <= MAX_MARINESCOUNT;
    }

    public static boolean worldValidator(String world) {
        if (isChapterNull) {
            return true;
        }
        return world != null && !("".equals(world));
    }
}
