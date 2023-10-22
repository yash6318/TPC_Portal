package com.dbms.project.api;

import com.dbms.project.model.User;
import com.dbms.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
public class AuthenticationController {

    @Autowired
    UserService userService;
    @Autowired
    UserService companyService;
    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String login(Model model, String error, String logout, HttpSession session) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");


        return "login";
    }


    @GetMapping("/signup")
    public String signup(){
        return "register";
    }

    @PostMapping("/signup")
    public String signupsubmit(@ModelAttribute User user,@RequestParam("confirmPassword") String confirmPassword, Model model)
    {
        System.out.println(user);

        if(userService.alreadyExists(user.getUsername())){
            model.addAttribute("error", "User Already Exists!");
            return "register";
        }
        else if(!user.getPassword().equals(confirmPassword)){
            model.addAttribute("error", "Confirm Password don't match!");
            return "register";
        }
        else {
            System.out.println(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(user.getPassword());
            userService.insertUser(user);
            return "redirect:/login";
        }
    }


    @GetMapping("/signup/company")
    public String companySignup(){
        return "company-register";
    }

    @PostMapping("/signup/company")
    public String companySignupSubmit(@ModelAttribute User user, Model model){
        System.out.println(user);
        if(userService.alreadyExists(user.getUsername())){
            if(user.getDesignation().equals("Student")){
                model.addAttribute("error", "Username already taken by a student");
            }
            else{
                model.addAttribute("error", "Username already taken by a Company");
            }
            return "company-register";
        }
        else {
            System.out.println(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setDesignation("Company");
            userService.insertUser(user);
            return "redirect:/";
        }
    }



    @GetMapping(path="/changepassword")
    public String changepassword(Model model,Authentication auth) {
        User user = (User)auth.getPrincipal();
        model.addAttribute("user",user);
        System.out.println(model);
        return "changepassword";
    }

    @PostMapping(path="/changepassword")
    public String changepasswordsubmit(@ModelAttribute User user,@RequestParam("oldPassword") String oldPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,Authentication auth,Model model){
        System.out.println(user);
        User user1 = (User)auth.getPrincipal();

        if(!Objects.equals(user1.getUsername(), user.getUsername())){
            model.addAttribute("error", "Enter Correct Username");
            return "/changepassword";
        }
        if(!user.getPassword().equals(confirmPassword)){
            model.addAttribute("error", "Confirm Password doesn't match!");
            return "/changepassword";
        }
        if(passwordEncoder.matches(oldPassword,user1.getPassword())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.updateUser(user);
            System.out.println(user);
            return "redirect:/";
        }
        else {
            model.addAttribute("error", "Enter Correct Old Password");
            return "/changepassword";
        }

    }
}
