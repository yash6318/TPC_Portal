package com.dbms.project.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Roles {
    private Integer RoleID;
    private String RoleName;
    private String CompanyName;
    private String JobDescription;
    private Integer Stipend;
    private Boolean Idd;
    private Boolean BTech;
    private Float minCpi;
    private String minPassingYear;
    private String maxPassingYear;
    private Integer maxTotalBacklogs;
    private Integer maxActiveBacklogs;
    private Timestamp deadLine;
}
