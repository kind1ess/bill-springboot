package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.dto.StaffPreviewDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffPreviewVo {
    private List<StaffPreviewDto> staffPreviewDtoList;
}
