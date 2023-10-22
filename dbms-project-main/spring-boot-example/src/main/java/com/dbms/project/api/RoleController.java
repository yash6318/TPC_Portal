package com.dbms.project.api;


import com.dbms.project.model.Role;
import com.dbms.project.model.User;
import com.dbms.project.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoleController {

    @Autowired
    CompanyService companyService;

    public List<String> getbrancheslist() {
        List<String> brancheslist = new ArrayList<String>();
        brancheslist.add("CSE");
        brancheslist.add("MNC");
        brancheslist.add("EEE");
        brancheslist.add("ECE");
        brancheslist.add("CIV");
        brancheslist.add("CHEM");

        return brancheslist;
    }

    @GetMapping("/role/create")
    public String roleCreate(Model model){
        model.addAttribute("brancheis", getbrancheslist());
        return "create-role";
    }

    @PostMapping("/role/create")
    public String insertRole(@ModelAttribute("role") Role role, Model model, Authentication auth){
        role.setCompanyName(companyService.getCompanyByID(Integer.parseInt(((User)auth.getPrincipal()).getUsername())).getCompanyName());
        System.out.println(role);
        System.out.println("hello\n");
        return "redirect:/role/create";
    }

}
