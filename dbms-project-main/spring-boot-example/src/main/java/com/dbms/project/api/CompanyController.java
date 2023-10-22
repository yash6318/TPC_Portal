package com.dbms.project.api;

import com.dbms.project.model.Company;
import com.dbms.project.model.User;
import com.dbms.project.service.CompanyService;
import com.dbms.project.service.StudentService;
import com.dbms.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompanyController {

    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Autowired
    CompanyService companyService;



    @GetMapping("/company-profile")
    public String getCompanyDetails(Authentication auth, Model model){
        User temp = (User)auth.getPrincipal();
        System.out.println(temp);
        String uname = temp.getUsername();
        if(temp.getDesignation().equals("Student")){
            model.addAttribute("errorMessage","Unauthorized request");
            return "custom-error";
        }
        if(companyService.companyExists(Integer.parseInt(uname))){
            Company toBeShown = companyService.getCompanyByID(Integer.parseInt(uname));
            model.addAttribute("company", toBeShown);
            return "company-profile";
        }
        return "redirect:/company-profile/create";
    }



    @GetMapping(path="/company-profile/create")
    public String companyCreate(Model model,Authentication auth){
        User temp = (User)auth.getPrincipal();
        if(temp.getDesignation().equals("Student")){
            model.addAttribute("errorMessage","Unauthorized request");
            return "custom-error";
        }
        model.addAttribute("companyID",((User)auth.getPrincipal()).getUsername());
        return "company-profile-create";
    }

    @GetMapping("/company-profile/edit")
    public String companyEdit(Model model, Authentication auth){
        User temp = (User)auth.getPrincipal();
        if(temp.getDesignation().equals("Student")){
            model.addAttribute("errorMessage","Unauthorized request");
            return "custom-error";
        }
        model.addAttribute("company", companyService.getCompanyByID(Integer.parseInt(((User)auth.getPrincipal()).getUsername())));
        return "company-profile-edit";
    }

    @GetMapping("/company-home")
    public String companyHome(Authentication auth, Model model){
        if(auth.isAuthenticated()){
            User user = (User)auth.getPrincipal();
            if(user.getDesignation().equals("Student")){
                return "redirect:/";
            }
            return "company-home";
        }
        else{
            return "login";
        }
    }


    @PostMapping("/company-profile/create")
    public String companyCreatePost(@ModelAttribute Company company, Authentication auth){
        company.setCompanyID(Integer.parseInt(((User)auth.getPrincipal()).getUsername()));
        companyService.insertCompany(company);
        return "redirect:/company-profile";
    }

    @PostMapping(path="/company-profile/edit")
    public String companyEditPost(@ModelAttribute Company company, Authentication auth) {
        company.setCompanyID(Integer.parseInt(((User)auth.getPrincipal()).getUsername()));
        companyService.updateCompany(company);
        return "redirect:/company-profile";
    }
}
