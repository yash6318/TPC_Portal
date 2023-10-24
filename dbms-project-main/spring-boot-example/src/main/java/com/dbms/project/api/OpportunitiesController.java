package com.dbms.project.api;

import com.dbms.project.model.Role;
import com.dbms.project.model.Student;
import com.dbms.project.model.User;
import com.dbms.project.service.BranchService;
import com.dbms.project.service.CompanyService;
import com.dbms.project.service.RoleService;
import com.dbms.project.service.StudentService;
import com.zaxxer.hikari.util.IsolationLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OpportunitiesController {

    @Autowired
    StudentService studentService;
    @Autowired
    CompanyService companyService;

    @Autowired
    RoleService roleService;
    @Autowired
    BranchService branchService;

    @GetMapping("/opportunities")
    public String opportunities(Authentication auth, Model model){
        User user = (User)auth.getPrincipal();
        if(user.getDesignation().equals("Company")){
            model.addAttribute("errorMessage", "Unauthorized Request!");
            return "custom-error";
        }
        Student student = studentService.getStudentByRollNo(Integer.parseInt(user.getUsername()));
        List<Role> roles = roleService.getRoles(student);
        List<List<String>> branches = new ArrayList<>();
        List<String> company = new ArrayList<>();
        for(Role role: roles){
            branches.add(branchService.getBranchFromBin(role.getBranchValue()));
            company.add(companyService.getCompanyByID(role.getCompanyID()).getCompanyName());
        }
        model.addAttribute("roles", roles);
        model.addAttribute("branches", branches);
        model.addAttribute("company", company);
        return "opportunities";
    }


}
