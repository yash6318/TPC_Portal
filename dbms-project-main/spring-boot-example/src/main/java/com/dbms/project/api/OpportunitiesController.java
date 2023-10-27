package com.dbms.project.api;

import com.dbms.project.model.*;
import com.dbms.project.service.*;
import com.zaxxer.hikari.util.IsolationLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        else if(!studentService.studentExists(Integer.parseInt(user.getUsername()))){
            return "redirect:/profile/create";
        }
        Student student = studentService.getStudentByRollNo(Integer.parseInt(user.getUsername()));
        List<Role> roles = roleService.getRoles(student);
        List<List<String>> branches = new ArrayList<>();
        List<String> company = new ArrayList<>();
        List<String> selected_resume = new ArrayList<>();
        List<Boolean> is_selected = new ArrayList<>();
        List<Resume> resumes = resumeService.getVerifiedResumesByUser(Integer.parseInt(user.getUsername()));
        for(Role role: roles){
            branches.add(branchService.getBranchFromBin(role.getBranchValue()));
            company.add(companyService.getCompanyByID(role.getCompanyID()).getCompanyName());
            if(willingnessService.willingnessIsPresent(student.getRollNo(), role.getCompanyID(), role.getRoleName())){
                Willingness willingness = willingnessService.getWillingness(student.getRollNo(), role.getCompanyID(), role.getRoleName());
                selected_resume.add(willingness.getResumeName());
                is_selected.add(true);
            }
            else{
                selected_resume.add("Not Willing");
                is_selected.add(false);
            }
        }
        model.addAttribute("roles", roles);
        model.addAttribute("branches", branches);
        model.addAttribute("company", company);
        model.addAttribute("resumes", resumes);
        model.addAttribute("sel_res", selected_resume);
        model.addAttribute("is_sel", is_selected);
        return "opportunities";
    }

    @PostMapping("/opportunities")
    public String saveOpportunities(@RequestParam(value = "returnVal") String str, Authentication auth){
        User user = (User)auth.getPrincipal();
        String[] split_str = str.split("\\|");
        Willingness willingness = new Willingness();
        willingness.setResumeName(split_str[0]);
        willingness.setRoleName(split_str[1]);
        willingness.setCompanyID(Integer.parseInt(split_str[2]));
        willingness.setRollNo(Integer.parseInt(user.getUsername()));
        willingnessService.deleteWillingness(willingness.getRollNo(), willingness.getCompanyID(), willingness.getRoleName());
        System.out.println(willingness);
        if(!split_str[0].equals("No")) willingnessService.insertWillingness(willingness);
        return "redirect:/opportunities";
    }

    @GetMapping("/willingness")
    public String willingness(Authentication auth, Model model){
        User user = (User)auth.getPrincipal();
        if(user.getDesignation().equals("Company")){
            model.addAttribute("errorMessage", "Unauthorized Request!");
            return "custom-error";
        }
        else if(!studentService.studentExists(Integer.parseInt(user.getUsername()))){
            return "redirect:/profile/create";
        }
        Student student = studentService.getStudentByRollNo(Integer.parseInt(user.getUsername()));
        List<Willingness>willing=willingnessService.getWillingnessByRollNo(Integer.parseInt(user.getUsername()));
        List<Role> roles=new LinkedList<Role>();

        List<List<String>> branches = new ArrayList<>();
        List<String> company = new ArrayList<>();
        List<String> selected_resume = new ArrayList<>();
        List<Boolean> is_selected = new ArrayList<>();
        List<Resume> resumes = resumeService.getVerifiedResumesByUser(Integer.parseInt(user.getUsername()));

        for(Willingness w:willing)
        {
            List<Role>lst=roleService.getRolesByCompanyIdRole(w.getCompanyID(),w.getRoleName());
            roles.addAll(lst);
        }

        for(Role role: roles){
            branches.add(branchService.getBranchFromBin(role.getBranchValue()));
            company.add(companyService.getCompanyByID(role.getCompanyID()).getCompanyName());
            if(willingnessService.willingnessIsPresent(student.getRollNo(), role.getCompanyID(), role.getRoleName())){
                Willingness willingness = willingnessService.getWillingness(student.getRollNo(), role.getCompanyID(), role.getRoleName());
                selected_resume.add(willingness.getResumeName());
                is_selected.add(true);
            }
            else{
                selected_resume.add("Not Willing");
                is_selected.add(false);
            }
        }
        model.addAttribute("roles", roles);
        model.addAttribute("branches", branches);
        model.addAttribute("company", company);
        model.addAttribute("resumes", resumes);
        model.addAttribute("sel_res", selected_resume);
        model.addAttribute("is_sel", is_selected);
        return "willingness";
    }

    @GetMapping("willingness/{id}")
    public String willingnessdelete(Model model, @PathVariable String id, Authentication auth){
        System.out.println(id);
        String[] parts = id.split("-");
        User user = (User)auth.getPrincipal();
        willingnessService.deleteWillingness(Integer.parseInt(user.getUsername()),Integer.parseInt(parts[0]),parts[1]);
        return "redirect:/willingness";
    }

}
