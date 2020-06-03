package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonBillPreviewVo {
    private List<CommonBillPreviewWithUpdateTime> billPreviewList;
}
