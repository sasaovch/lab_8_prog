package util;

import java.io.Serializable;
import java.util.Date;

/*
 * Class that contains methods that check if the data correct
 */
public class SpaceMarineValidarot implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final int MIN_HEALTH = 1;
    private static final int MIN_HEARTCOUNT = 1;
    private static final int MAX_HEARTCOUNT = 3;
    private static final int MIN_MARINESCOUNT = 0;
    private static final int MAX_MARINESCOUNT = 1000;
    private boolean isChapterNull;

    public boolean nameValidator(String name) {
        return (name != null && !"".equals(name));
    }

    public boolean healthValidator(Integer health) {
        return health != null && health >= MIN_HEALTH;
    }

    public boolean heartCountValidator(Integer heartCount) {
        return heartCount != null && heartCount >= MIN_HEARTCOUNT && heartCount <= MAX_HEARTCOUNT;
    }

    public boolean yValidator(Long y) {
        return (y != null);
    }

    public boolean chapterValidator(String chapter) {
        if (chapter == null || "".equals(chapter)) {
            isChapterNull = true;
        } else {
            isChapterNull = false;
        }
        return true;
    }
    public boolean parentLegiolValidator(String parentLegion) {
        if (isChapterNull) {
            return parentLegion == null;
        }
        return true;
    }
    public boolean marinesCountValidator(Integer marinesCount) {
        if (isChapterNull) {
            return marinesCount == null;
        }
        return marinesCount != null && marinesCount >= MIN_MARINESCOUNT && marinesCount <= MAX_MARINESCOUNT;
    }

    public boolean worldValidator(String world) {
        if (isChapterNull) {
            return world == null || "".equals(world);
        }
        return world != null && !("".equals(world));
    }
}
