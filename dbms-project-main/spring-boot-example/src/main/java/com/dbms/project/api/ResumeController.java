package com.dbms.project.api;


import com.dbms.project.model.Resume;
import com.dbms.project.model.User;
import com.dbms.project.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @GetMapping("resume/create")
    public String resumeCreate(){
        return "resume-create";
    }

    @PostMapping("resume/create")
    public String insertResume(@ModelAttribute Resume resume, Model model, Authentication auth){

        resume.setAuthorId(Integer.parseInt(auth.getName()));
        resume.setIsVerified(0);
        System.out.println(resume);
        resumeService.insertResume(resume);
        return "redirect:/resume";
    }

    @GetMapping("/resume")
    public String allResume(Model model, Authentication auth){
        if(auth.isAuthenticated()){
            User user = (User)auth.getPrincipal();
            if(!user.getDesignation().equals("Student")){
            }
            else{
                System.out.println(resumeService.getResumesByUser(Integer.parseInt(user.getUsername())));
                model.addAttribute("resume", resumeService.getResumesByUser(Integer.parseInt(user.getUsername())));
            }
            return "/resume";
        }
        else{
            model.addAttribute("error", "Please sign in!");
            return "redirect:/";
        }
    }


}
