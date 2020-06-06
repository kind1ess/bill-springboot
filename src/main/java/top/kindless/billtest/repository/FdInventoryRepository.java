package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.kindless.billtest.model.dto.GoodsDto;
import top.kindless.billtest.model.entity.FdInventory;

import java.util.List;

public interface FdInventoryRepository extends JpaRepository<FdInventory,Integer> {

    @Query(value = "select cur_amount from fd_inventory where goods_id=?1",nativeQuery = true)
    Integer findAmountById(Integer id);

    FdInventory findByGoodsId(Integer goodsId);

    @Query("select new top.kindless.billtest.model.dto.GoodsDto(f.goodsId,i.commodityId,c.commodityName,i.specificationId,s.specificationName,c.price,c.imgUrl,i.amount)" +
            "from FdInventory f,Inventory i,Commodity c,Specification s " +
            "where f.goodsId = i.goodsId " +
            "and i.commodityId = c.id " +
            "and i.specificationId = s.id " +
            "order by i.goodsId")
    List<GoodsDto> findAllGoodsDto();
}
