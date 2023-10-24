package com.dbms.project.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class Resume{
    @NotBlank(message="Name cannot be empty")
    @Setter
    @Getter
    private String resumeName;
    @Setter
    @Getter
    @NotBlank(message="Drive Link cannot be blank")
    private String resumeLink;
    @Getter
    @Setter
    private Integer rollNo;
    @Getter
    @Setter
    private Integer isVerified;
}
