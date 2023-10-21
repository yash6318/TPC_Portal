package com.dbms.project.api;


import com.dbms.project.model.Student;
import com.dbms.project.model.User;
import com.dbms.project.service.StudentService;
import com.dbms.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class StudentController {

    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @GetMapping("/profile")
    public String getStudentDetails(Authentication auth, Model model){
        User temp = (User)auth.getPrincipal();
        String uname = temp.getUsername();
        if(temp.getDesignation().equals("Company")){
            model.addAttribute("errorMessage","Unauthorized request");
            return "error";
        }
        if(studentService.studentExists(Integer.parseInt(uname))){
            Student toBeShown = studentService.getStudentByRollNo(Integer.parseInt(uname));
            model.addAttribute("student", toBeShown);
            return "profile";
        }
        return "redirect:/profile/create";
    }



    @GetMapping(path="/profile/create")
    public String studentCreate(Model model,Authentication auth){
        User temp = (User)auth.getPrincipal();
        if(temp.getDesignation().equals("Company")){
            model.addAttribute("errorMessage","Unauthorized request");
            return "error";
        }
        model.addAttribute("rollNo",((User)auth.getPrincipal()).getUsername());
        return "profile-create";
    }

    @GetMapping("/profile/edit")
    public String studentEdit(Model model, Authentication auth){
        User temp = (User)auth.getPrincipal();
        if(temp.getDesignation().equals("Company")){
            model.addAttribute("errorMessage","Unauthorized request");
            return "error";
        }
        model.addAttribute("student", studentService.getStudentByRollNo(Integer.parseInt(((User)auth.getPrincipal()).getUsername())));
        return "profile-edit";
    }

    @PostMapping("/profile/create")
    public String studentCreatePost(@ModelAttribute Student student, Authentication auth){
        student.setRollNo(Integer.parseInt(((User)auth.getPrincipal()).getUsername()));
        studentService.insertStudent(student);
        return "redirect:/profile";
    }

    @PostMapping(path="/profile/edit")
    public String studentEditPost(@ModelAttribute Student student, Authentication auth) {
        student.setRollNo(Integer.parseInt(((User)auth.getPrincipal()).getUsername()));
        studentService.updateStudent(student);
        return "redirect:/profile";
    }
}

