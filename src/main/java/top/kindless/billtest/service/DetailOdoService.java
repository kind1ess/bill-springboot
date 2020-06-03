package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.common.ListGoodsWithoutPrice;
import top.kindless.billtest.model.common.FlowReserveAmount;
import top.kindless.billtest.model.entity.DetailOdo;

import java.util.List;


public interface DetailOdoService {

    /**
     * 保存出库单明细
     * @param detailOdo
     */
    @Transactional
    void saveDetailOdo(@NonNull DetailOdo detailOdo);

    /**
     * 根据出库单id查询所有出库单明细
     * @param odoId
     * @return
     */
    List<DetailOdo> findAllDetailOdoByOdoId(@NonNull String odoId);

    /**
     * 根据出库单id查询出库单明细
     * @param billId 出库单id
     * @return 出库单明细
     */
    List<ListGoodsWithoutPrice> findAllListGoodsByBillId(@NonNull String billId);

    /**
     * 统计出库数量
     * @return
     */
    List<FlowReserveAmount> countOdoAmount();
}
