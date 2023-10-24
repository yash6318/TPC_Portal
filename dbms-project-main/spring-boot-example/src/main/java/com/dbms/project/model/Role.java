package com.dbms.project.model;

import lombok.Data;
import org.apache.http.message.TokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
public class Role {
    private String RoleName;
    private Integer CompanyID;
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
    private Integer BranchValue;
}
