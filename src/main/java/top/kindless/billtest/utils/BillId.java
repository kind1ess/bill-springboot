package top.kindless.billtest.utils;

public enum BillId {

    ORDER("ORDER"),

    ODO("ODO"),

    INV("INV"),

    RET("RET"),

    CHECK("CHECK"),

    ENTER("ENTER"),

    SHORT("SHORT"),

    PUR("PUR");

    private final String value;

    BillId(String pre){
        this.value = BillNoUtils.generateBillId(pre);
    }

    public String getValue(){
        return this.value;
    }

}
