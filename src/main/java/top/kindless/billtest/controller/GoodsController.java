package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kindless.billtest.model.vo.GoodsVo;
import top.kindless.billtest.service.FdInventoryService;
import top.kindless.billtest.utils.Result;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    FdInventoryService fdInventoryService;

    @GetMapping("/findAll")
    @ApiOperation("获取所有商品信息")
    public Result<GoodsVo> findAllGoods(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),fdInventoryService.findAllFdGoods());
    }

}
