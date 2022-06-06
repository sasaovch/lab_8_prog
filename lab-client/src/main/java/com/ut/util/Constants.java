package com.ut.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.ut.common.data.AstartesCategory;

public final class Constants {
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int LABEL_WIDTH = SCREEN_WIDTH / 2;
    public static final int LABEL_HEIGHT = SCREEN_HEIGHT / 9;
    public static final int TEXTFIELD_WIDTH = SCREEN_WIDTH / 4;
    public static final int TEXTFIELD_HIGHT = SCREEN_HEIGHT / 15;
    public static final int BUTTON_WIDTH = SCREEN_WIDTH / 7;
    public static final int BUTTON_HEIGHT = SCREEN_HEIGHT / 15;
    public static final int COMBOX_WIDTH = SCREEN_WIDTH / 7;
    public static final int COMBOX_HIGHT = SCREEN_HEIGHT / 15;
    public static final int POPUP_FRAME_WIDTH = SCREEN_WIDTH / 3;
    public static final int POPUP_FRAME_HIGHT = SCREEN_HEIGHT / 3;
    public static final int CENTER_PANEL_HEIGHT = SCREEN_HEIGHT / 5 * 3;
    public static final int DATE_CHOOSER_HEIGHT = Constants.SCREEN_HEIGHT / 20;
    public static final int DATE_CHOOSER_WIDTH = Constants.SCREEN_WIDTH / 10;
    public static final int CALENDAR_WIDTH = Constants.SCREEN_WIDTH / 5;
    public static final int CALENDAR_HEIGHT = Constants.SCREEN_HEIGHT / 5;
    public static final Color MAIN_COLOR = Color.PINK;
    public static final Color SUB_COLOR = new Color(0xFFFFFF);
    public static final int NORTH_PANEL_HEIGHT = SCREEN_HEIGHT / 5;
    public static final int VGAP = SCREEN_HEIGHT / 30;
    public static final int HGAP = SCREEN_WIDTH / 25;
    public static final int RIGHT_OF_CENTER_SIZE = SCREEN_WIDTH;
    public static final int ROW_HEIGHT = SCREEN_HEIGHT / 21;
    public static final int BORDER_GAP = 50;
    public static final Font MAIN_FONT = new Font(Font.SANS_SERIF,  Font.BOLD, SCREEN_HEIGHT / 30);
    public static final Font SUB_FONT = new Font(Font.SANS_SERIF,  Font.BOLD, SCREEN_HEIGHT / 40);

    public static final String[] LANGUAGES = {"Русский", "Português", "Español", "Català"};
    public static final String DEFAUTL_LANGUAGE = "Русский";
    public static final ResourceBundle RU_BUNDLE = ResourceBundle.getBundle("com.ut.resources.Resource", new Locale("ru", "RU"));
    public static final ResourceBundle PT_BUNDLE = ResourceBundle.getBundle("com.ut.resources.Resource", new Locale("pt", "PT"));
    public static final ResourceBundle ES_BUNDLE = ResourceBundle.getBundle("com.ut.resources.Resource", new Locale("es", "PR"));
    public static final ResourceBundle CAT_BUNDLE = ResourceBundle.getBundle("com.ut.resources.Resource", new Locale("cat", "CAT"));
    public static final HashMap<String, ResourceBundle> LANGUAGES_AND_BUNDLES = new HashMap<>();
    public static final String[] CATEGORY = Arrays.stream(AstartesCategory.values()).map(Enum::toString).toArray(String[]::new);


    static {
        LANGUAGES_AND_BUNDLES.put("Русский", RU_BUNDLE);
        LANGUAGES_AND_BUNDLES.put("Português", PT_BUNDLE);
        LANGUAGES_AND_BUNDLES.put("Español", ES_BUNDLE);
        LANGUAGES_AND_BUNDLES.put("Català", CAT_BUNDLE);
    }

    private Constants() {
        throw new UnsupportedOperationException();
    }


    public static ResourceBundle getBundleFromLanguageName(String s) {
        return LANGUAGES_AND_BUNDLES.get(s);
    }

    public static String getNameByBundle(ResourceBundle resourceBundle) {
        for (String key : LANGUAGES_AND_BUNDLES.keySet()) {
            if (LANGUAGES_AND_BUNDLES.get(key) == resourceBundle) {
                return key;
            }
        }
        return null;
    }
}
