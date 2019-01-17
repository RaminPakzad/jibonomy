package sadad.com.jibonomy.services;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sadad.com.jibonomy.biz.dto.smsDto;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/17/2019.
 */
public class SmsService {

    public static smsDto decodeSms(String test) {
        final Pattern amountPattern = Pattern.compile("(?miu)(?<title>\\s*)(?<amount>-?\\+?[1-9]{1}[0-9]{0,2}(,?\\d{3})*)", Pattern.UNICODE_CHARACTER_CLASS);
        final Pattern datePattern = Pattern.compile("(?miu)(?<date>\\d{4}-\\d2:\\d{2})");

        smsDto result = new smsDto();
        Scanner scanner = new Scanner(test);

        String amount = null;
        String date = null;
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            final Matcher amountMatcher = amountPattern.matcher(nextLine);
            if (amountMatcher.find() && amount == null) {
                amount = amountMatcher.group("amount");
//                System.out.println("amount = " + amount);
                result.setAmount(amount);
            } else {

                final Matcher dateMatcher = datePattern.matcher(nextLine);
                if (dateMatcher.find()) {
                    date = dateMatcher.group("date");
//                    result.date = date;
                    result.setDate(date);
//                    System.out.println("date = " + date);
                }
            }
        }
        return result;

    }

}