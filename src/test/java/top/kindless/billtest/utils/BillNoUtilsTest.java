package top.kindless.billtest.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class BillNoUtilsTest {

    @Test
    void test01(){
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
//        System.out.println(simpleDateFormat.format(date));
//        String s = BillNoUtils.generateBillId("ORDER");
//        System.out.println(s);
        String value = BillId.ORDER.getValue();
        System.out.println(value);
    }
}
