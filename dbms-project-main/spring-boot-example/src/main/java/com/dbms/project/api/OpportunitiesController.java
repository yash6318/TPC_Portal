package com.dbms.project.api;

import com.dbms.project.model.Student;
import com.dbms.project.model.User;
import com.dbms.project.service.RoleService;
import com.dbms.project.service.StudentService;
import com.zaxxer.hikari.util.IsolationLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpportunitiesController {

    @Autowired
    StudentService studentService;

    @Autowired
    RoleService roleService;

    @GetMapping("/opportunities")
    public String opportunities(Authentication auth){
        Student student = studentService.getStudentByRollNo(Integer.parseInt(((User)auth.getPrincipal()).getUsername()));
        System.out.println(roleService.getRoles(student));
        System.out.println(student);
        return "opportunities";
    }


}
