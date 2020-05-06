package top.kindless.billtest.service.common;


import java.util.List;

/**
 * 公共单据服务
 * B是单据     BillXXX
 * D是单据详情   DetailXXX
 */
public interface BillService<B,D> {
    /**
     * 根据单据编号查询单据表头
     * @param id
     * @return
     */
    B findBillById(String id);

    /**
     * 根据状态查询单据
     * @param statusId
     * @return
     */
    List<B> findBillsByStatusId(Integer statusId);

    /**
     * 根据单据编号查询单据详情
     * @param billId
     * @return
     */
    List<D> findDetailBillsByBillId(String billId);

    /**
     * 根据单据编号删除单据
     * @param billId
     */
    void deleteBillAndDetailBillsByBillId(String billId);

    /**
     * 修改单据
     * @param b
     * @param d
     */
    void updateBill(B b, D d);
}
