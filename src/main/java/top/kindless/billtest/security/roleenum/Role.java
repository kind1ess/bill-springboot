package top.kindless.billtest.security.roleenum;

public enum Role {

    /**
     * 销售员
     */
    SALESPERSON(1),

    /**
     * 仓库管理员
     */
    STORE_MAN(2),

    /**
     * 发货员
     */
    SHIPPER(3),

    /**
     * 验收员
     */
    INSPECTOR(4),

    /**
     * 采购员
     */
    BUYER(5),

    /**
     * 人力管理员
     */
    HR(6),

    /**
     * 总经理
     */
    MANAGER(7),

    /**
     * 超级管理员（技术部）
     */
    SUPER_ADMIN(8),
    /**
     * 所有管理员
     */
    ALL_ADMIN(0);

    private Integer value;
    Role(Integer value){
        this.value = value;
    }

    public void setValue(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
