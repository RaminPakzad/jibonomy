package sadad.com.jibonomy.utils;

import java.text.DecimalFormat;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/17/2019.
 */
public class StringUtil {
    public static final String UNDEFINED_TAG = "undefined";
    public static DecimalFormat df = new DecimalFormat("#,###");

    public static String formatDate(String persianDate) {
        String year = persianDate.substring(0, 4);
        String month = persianDate.substring(4, 6);
        String day = persianDate.substring(6, 8);
        String date = year + "/" + month + "/" + day;
        return date;
    }

    public static String formatTime(String transactionTime) {
        String hour = transactionTime.substring(0, 2);
        String min = transactionTime.substring(2, 4);
        return hour + ":" + min;
    }
}
