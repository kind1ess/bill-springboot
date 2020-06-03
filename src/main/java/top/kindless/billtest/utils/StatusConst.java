package top.kindless.billtest.utils;

public class StatusConst {

    /**
     * 待审核
     */
    public static final Integer TO_BE_VERIFIED = 1;

    /**
     * 未通过
     */
    public static final Integer FAILED = 2;

    /**
     * 待出库
     */
    public static final Integer TO_BE_OUTBOUND = 3;

    /**
     * 待发货
     */
    public static final Integer TO_BE_SHIPPED = 4;

    /**
     * 已发货
     */
    public static final Integer SHIPPED = 5;

    /**
     * 待验收
     */
    public static final Integer TO_BE_CHECKED = 6;

    /**
     * 已验收
     */
    public static final Integer CHECKED = 7;

    /**
     * 待入库
     */
    public static final Integer TO_BE_ENTERED = 8;

    /**
     * 已入库
     */
    public static final Integer ENTERED = 9;

    /**
     * 已归还
     */
    public static final Integer RETURNED = 10;

    /**
     * 待采购
     */
    public static final Integer TO_BE_PURCHASE = 11;

    /**
     * 已采购
     */
    public static final Integer PURCHASED = 12;
}
