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

    /**
     * 单据编号设计
     * @param pre 单据前缀，三位字母组成，主要用于作为单据类型的唯一标识
     * @return 单据编号，由3位单据前缀，14位时间戳信息以及5位随机数后缀组成
     */
    public static String generateBillId(String pre) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String suf = generateRandomSuf();
        return pre + simpleDateFormat.format(date) + suf;
    }
}
