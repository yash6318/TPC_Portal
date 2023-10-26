package com.dbms.project.api;


import com.dbms.project.model.Student;
import com.dbms.project.model.User;
import com.dbms.project.service.BranchService;
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
public class StudentController {

    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Autowired
    BranchService branchService;
    @GetMapping("/profile")
    public String getStudentDetails(Authentication auth, Model model){
        User temp = (User)auth.getPrincipal();
        String uname = temp.getUsername();
        if(studentService.studentExists(Integer.parseInt(uname))){
            Student toBeShown = studentService.getStudentByRollNo(Integer.parseInt(uname));
            model.addAttribute("student", toBeShown);
            return "profile";
        }
        return "redirect:/profile/create";
    }



    @GetMapping(path="/profile/create")
    public String studentCreate(Model model,Authentication auth){
        model.addAttribute("rollNo",((User)auth.getPrincipal()).getUsername());
        model.addAttribute("branches", branchService.getAllBranches());
        return "profile-create";
    }

    @GetMapping("/profile/edit")
    public String studentEdit(Model model, Authentication auth){
        model.addAttribute("student", studentService.getStudentByRollNo(Integer.parseInt(((User)auth.getPrincipal()).getUsername())));
        model.addAttribute("branches", branchService.getAllBranches());
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

    @GetMapping(path="/student-home")
    public String studentHome(Authentication auth,Model model){
        User user = (User)auth.getPrincipal();
        if(user.getDesignation().equals("Company")){
            model.addAttribute("errorMessage","Unauthorized request");
            return "custom-error";
        }
        return "student-home";
    }

}

