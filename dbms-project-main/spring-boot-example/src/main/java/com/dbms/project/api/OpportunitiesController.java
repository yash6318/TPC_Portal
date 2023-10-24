package com.dbms.project.api;

import com.dbms.project.model.*;
import com.dbms.project.service.*;
import com.zaxxer.hikari.util.IsolationLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    ResumeService resumeService;
    @Autowired
    WillingnessService willingnessService;

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
        List<Resume> resumes = resumeService.getResumesByUser(Integer.parseInt(user.getUsername()));
        for(Role role: roles){
            branches.add(branchService.getBranchFromBin(role.getBranchValue()));
            company.add(companyService.getCompanyByID(role.getCompanyID()).getCompanyName());
        }
        model.addAttribute("roles", roles);
        model.addAttribute("branches", branches);
        model.addAttribute("company", company);
        model.addAttribute("resumes", resumes);
        return "opportunities";
    }

    @PostMapping("/opportunities")
    public String saveOpportunities(@ModelAttribute Willingness willingness, Authentication auth){
        User user = (User)auth.getPrincipal();
        if(!Objects.equals(willingness.getResumeName(), "NO")){
            willingness.setRollNo(Integer.parseInt(user.getUsername()));
            System.out.println(willingness);
            willingnessService.insertWillingness(willingness);
        }
        // else me delete bhi karna padega;
        return "redirect:/opportunities";
    }



}
