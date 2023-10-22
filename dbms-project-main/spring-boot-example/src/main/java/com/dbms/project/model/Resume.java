package com.dbms.project.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
@Data
public class Resume{
    @NotBlank(message="Username cannot be blank")
    private Integer resumeId;
    @NotBlank(message="Title cannot be empty")
    @Setter
    @Getter
    private String resumeName;
    @Setter
    @Getter
    @NotBlank(message="Drive Link cannot be blank")
    private String link;
    @Getter
    @Setter
    private Integer authorId;
    @Getter
    @Setter
    private Integer isVerified;
}
