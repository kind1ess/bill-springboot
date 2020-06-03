package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillReturn;
import top.kindless.billtest.model.params.VerifyParams;
import top.kindless.billtest.model.vo.ReturnPreviewVo;
import top.kindless.billtest.model.vo.ReturnVo;

import java.util.List;

public interface ReturnService{

    /**
     * 保存还车单表头
     * @param billReturn
     */
    @Transactional
    void saveBillReturn(@NonNull BillReturn billReturn);

    /**
     * 更新还车单表头
     * @param billReturn
     * @return
     */
    @Transactional
    BillReturn updateBillReturn(@NonNull BillReturn billReturn);

    /**
     * 根据id删除还车单表头
     * @param id
     */
    @Transactional
    void deleteBillReturnById(@NonNull String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    BillReturn findBillReturnById(@NonNull String id);

    /**
     * 根据状态查询
     * @param statusId
     * @return
     */
    List<BillReturn> findAllBillReturnByStatusId(@NonNull Integer statusId);

    /**
     * 生成还车单
     * @param orderId 订货单id
     * @return 还车单id
     */
    @Transactional
    @NonNull
    String generateBillReturn(@NonNull String orderId);

    /**
     * 根据id查询还车单信息
     * @param id 还车单id
     * @return 还车单信息
     */
    ReturnVo findReturnVoById(@NonNull String id);

    /**
     * 查询所有还车单id
     * @return 所有还车单id
     */
    List<String> findAllId();

    /**
     * 根据id查询所有
     * @return List<BillReturn>
     */
    List<BillReturn> findAll(@NonNull List<String> ids);

    /**
     * 查询所有还车单预览信息
     * @return 所有还车单预览信息
     */
    ReturnPreviewVo findAllReturnPreview();

    /**
     * 查询所有还车单预览信息
     * @return 所有还车单预览信息
     */
    ReturnPreviewVo findAllReturnPreviewPageBy(@NonNull Integer page,@NonNull Integer size);

    /**
     * 根据状态查询
     * @param statusId
     * @return
     */
    ReturnPreviewVo findAllReturnPreview(@NonNull Integer statusId);

    /**
     * 根据状态查询
     * @param statusId
     * @param size
     * @return
     */
    ReturnPreviewVo findAllReturnPreview(@NonNull Integer statusId,@NonNull Integer page,@NonNull Integer size);

    /**
     *
     * @param billId 单据id
     * @param statusId 状态id
     */
    void setStatus(@NonNull String billId,@NonNull Integer statusId);

    /**
     * 审核还车单
     * @param verifyParams
     */
    void verify(@NonNull VerifyParams verifyParams);

    /**
     * 查找还车单状态
     * @param billId 还车单编号
     * @return 状态id
     */
    @NonNull
    Integer findStatusIdByBillId(@NonNull String billId);

    /**
     * 查询用户所有还车单
     * @return 还车单预览信息
     */
    ReturnPreviewVo findAllReturnPreviewByUserId();

    /**
     * 获取数量
     * @return
     */
    Long getCount();

    /**
     * 获取数量
     * @return
     */
    Long getCount(@NonNull Integer statusId);
}
