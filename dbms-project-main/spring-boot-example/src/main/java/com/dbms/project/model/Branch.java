package com.dbms.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Branch {
    private Integer BranchID;
    private String BranchName;
}
