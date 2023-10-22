package com.dbms.project.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Role {
    private Integer RoleID;
    private String RoleName;
    private String CompanyName;
    private String JobDescription;
    private Integer Stipend;
    private Boolean Idd = false;
    private Boolean BTech = false;
    private Float minCpi;
    private String minPassingYear;
    private String maxPassingYear;
    private Integer maxTotalBacklogs;
    private Integer maxActiveBacklogs;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadLine;
    private List<String> branches;
}
