package top.kindless.billtest.utils;

import org.apache.commons.lang3.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BillNoUtils {

    public static String generateRandomSuf(){
        byte[] bytes = RandomUtils.nextBytes(5);
        StringBuilder s = new StringBuilder("");
        for (byte aByte : bytes) {
            s.append(Math.abs(aByte%10));
        }
        return s.toString();
    }

    public static String generateBillId(String pre) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String s = generateRandomSuf();
        return pre + simpleDateFormat.format(date) + s;
    }
}
