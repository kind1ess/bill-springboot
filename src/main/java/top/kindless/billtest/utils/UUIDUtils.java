package top.kindless.billtest.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String generateUserUUID(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return "user_"+uuid;
    }

    public static String generateOrderUUID(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return "order_"+uuid;
    }

    public static String generateStaffUUID(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return "staff_"+uuid;
    }

    public static String generateOutboundOrderUUID(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return "ODO_"+uuid;
    }

    public static String generateInvoiceUUID(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return "invoice_"+uuid;
    }

    public static String generateReturnUUID(){
        String uuid =UUID.randomUUID().toString().replace("-","");
        return "return_"+uuid;
    }

    public static String generateCheckUUID(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return "check_"+uuid;
    }

    public static String generateEnterUUID(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "enter_"+uuid;
    }

    public static String generateGoodsUUID(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return "goods_"+uuid;
    }

    public static String generateToken(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
