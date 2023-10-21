package com.dbms.project.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
@Data
public class Company{
    @NotBlank(message="Username cannot be blank")
    private Integer companyID;
    @Getter
    @NotBlank(message="Title cannot be empty")
    @Setter
    private String companyName;
    @Setter
    @Getter
    @NotBlank(message="Body cannot be blank")
    private String HREmail;
    @Getter
    @Setter
    @NotBlank(message="Body cannot be blank")
    private String HRPhone;
}
