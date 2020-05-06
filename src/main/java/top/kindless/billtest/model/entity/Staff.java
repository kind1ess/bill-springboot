package top.kindless.billtest.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "tb_staff")
public class Staff {

    @Id
    private String id;

    private Integer departmentId;

    private String account;

    private String password;

    private String name;

    private String telephone;
}
