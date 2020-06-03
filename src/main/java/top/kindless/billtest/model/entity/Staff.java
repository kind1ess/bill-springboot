package top.kindless.billtest.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tb_staff")
@AllArgsConstructor
@NoArgsConstructor
public class Staff implements Serializable {

    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    private Integer departmentId;

    private String account;

    private String password;

    private String name;

    private String telephone;
}
