package com.dbms.project.api;


import com.dbms.project.model.Branch;
import com.dbms.project.model.Role;
import com.dbms.project.model.User;
import com.dbms.project.service.BranchService;
import com.dbms.project.service.CompanyService;
import com.dbms.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RoleController {

    @Autowired
    CompanyService companyService;
    @Autowired
    RoleService roleService;
    @Autowired
    BranchService branchService;


    @GetMapping("/role/create")
    public String testRoleCreate(Model model, Authentication auth){
        User user = (User)auth.getPrincipal();
        if(user.getDesignation().equals("Student")){
            model.addAttribute("errorMessage", "Unauthorized Request!");
            return "custom-error";
        }
        if(companyService.companyExists(Integer.parseInt(user.getUsername()))){
            model.addAttribute("brancheis", branchService.getAllBranches());
            return "create-role";
        }
        model.addAttribute("companyID", user.getUsername());
        return "redirect:/company-profile/create";
    }

    @PostMapping("/role/create")
    public String testInsertRole(@ModelAttribute("role") Role role, @RequestParam(value = "brlist") List<String> BranchList, Authentication auth){
        role.setCompanyID(Integer.parseInt(((User)auth.getPrincipal()).getUsername()));
        role.setBranchValue(branchService.getBin(BranchList));
        System.out.println(role);
        for(String str: BranchList){
            System.out.println(str);
        }
        roleService.insertRole(role);
        return "redirect:/company-home";
    }



}
