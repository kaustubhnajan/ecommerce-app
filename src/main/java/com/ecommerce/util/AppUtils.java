package com.ecommerce.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public final class AppUtils {

    public static boolean isEmpty(String str) {
        if (str != null && !str.equals("")) {
            return false;
        }
        return true;
    }

    public static String getRandomOrderId() {
        String orderId = "";
        for (int i = 0; i < 3; i++) {
            orderId += getRandomSixDigitId() + "-";
        }
        orderId += getRandomSixDigitId();
        return orderId;
    }

    public static int getRandomSixDigitId() {
        return 100000 + new Random().nextInt(900000);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        return formatter.format(date);
    }
}
