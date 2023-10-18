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
public class Post{

    @NotBlank(message="Username cannot be blank")
    private Integer postId;
    @Getter
    @NotBlank(message="Title cannot be empty")
    @Setter
    private String title;
    @Setter
    @Getter
    @NotBlank(message="Body cannot be blank")
    private String content;
    @Getter
    @Setter
    private Timestamp timestamp;
    @Getter
    @Setter
    private Integer authorId;

}
