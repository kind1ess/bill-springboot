package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.CommonBillPreview;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPreviewVo {
    private List<CommonBillPreview> commonBillPreviewList;
}
