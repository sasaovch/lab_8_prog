package util;



import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.ut.common.data.AstartesCategory;

/**
 * util class that contains some realisations of StringConverter
 */

public final class StringConverterRealisation {
    private StringConverterRealisation() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static Date parseDate(String arguments) {
        SimpleDateFormat parser = new SimpleDateFormat("uuuu-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        try {
            return parser.parse(arguments);

        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
    public static String formatDate(String dateToFormat, Locale locale) {
        Date date = parseDate(dateToFormat);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);

        return dateFormat.format(date);
    }
    public static String localeNumber(Number number, Locale locale) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        return numberFormat.format(number);
    }

    public static String booleanFormat(Boolean value, Locale locale) {
        return "";
    }
    public static String categoryFormat(AstartesCategory category, Locale locale) {
        return "HELIX";
    }


}
