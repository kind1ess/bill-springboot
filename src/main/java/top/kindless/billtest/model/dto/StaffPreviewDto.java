package top.kindless.billtest.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffPreviewDto {

    @ApiModelProperty(name = "员工id")
    private String staffId;

    @ApiModelProperty(name = "员工姓名")
    private String staffName;

    @ApiModelProperty(name = "部门id")
    private Integer departmentId;

    @ApiModelProperty(name = "部门名称")
    private String departmentName;
}
