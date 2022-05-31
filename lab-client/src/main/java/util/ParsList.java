package util;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.ut.common.data.SpaceMarine;

public class ParsList {
    private static final int COLUMNS_NUMBER = 14;
    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int CREATION_DT_INDEX = 2;
    private static final int X_INDEX = 3;
    private static final int Y_INDEX = 4;
    private static final int HEALTH_INDEX = 5;
    private static final int HEART_COUNT_INDEX = 6;
    private static final int LOYAL_INDEX = 7;
    private static final int CATEGORY_INDEX = 8;
    private static final int CHAPTER_INDEX = 9;
    private static final int PARENT_LEGION_INDEX = 10;
    private static final int MARINEC_COUNT_INDEX = 11;
    private static final int WORLD_COUNT_INDEX = 12;
    private static final int OWNER_COUNT_INDEX = 13;
    public static String[][] parseList(List<SpaceMarine> list, Locale locale) {
        if (Objects.nonNull(list)) {
            String[][] table = new String[list.size()][COLUMNS_NUMBER];
            for (int i = 0; i < list.size(); i++) {
                SpaceMarine spaceMarine = (SpaceMarine) list.get(i);
                table[i][ID_INDEX] = StringConverterRealisation.localeNumber(spaceMarine.getID(), locale);
                table[i][NAME_INDEX] = spaceMarine.getName();
                table[i][CREATION_DT_INDEX] =  StringConverterRealisation.formatDate(spaceMarine.getCreationDateTime().toString(), locale);
                table[i][X_INDEX] = StringConverterRealisation.localeNumber(spaceMarine.getCoordinates().getX(), locale);
                table[i][Y_INDEX] = StringConverterRealisation.localeNumber(spaceMarine.getCoordinates().getY(), locale);
                table[i][HEALTH_INDEX] = StringConverterRealisation.localeNumber(spaceMarine.getHealth(), locale);
                table[i][HEART_COUNT_INDEX] = StringConverterRealisation.localeNumber(spaceMarine.getHeartCount(), locale);
                table[i][LOYAL_INDEX] = StringConverterRealisation.booleanFormat(spaceMarine.getLoyal(), locale);
                table[i][CATEGORY_INDEX] = StringConverterRealisation.categoryFormat(spaceMarine.getCategory(), locale);
                if (Objects.isNull(spaceMarine.getChapter())) {
                    table[i][CHAPTER_INDEX] = "";
                    table[i][PARENT_LEGION_INDEX] = "";
                    table[i][MARINEC_COUNT_INDEX] = "";
                    table[i][WORLD_COUNT_INDEX] = "";
                    table[i][OWNER_COUNT_INDEX] = "";
                } else {
                    table[i][CHAPTER_INDEX] = spaceMarine.getChapter().getName();
                    table[i][PARENT_LEGION_INDEX] = spaceMarine.getChapter().getParentLegion();
                    table[i][MARINEC_COUNT_INDEX] = StringConverterRealisation.localeNumber(spaceMarine.getChapter().getMarinesCount(), locale);
                    table[i][WORLD_COUNT_INDEX] = spaceMarine.getChapter().getWorld();
                    table[i][OWNER_COUNT_INDEX] = spaceMarine.getOwnerName();
                }
            }
            return table;
        }
        String [][] table = new String[0][];
        return table;
    }
}
