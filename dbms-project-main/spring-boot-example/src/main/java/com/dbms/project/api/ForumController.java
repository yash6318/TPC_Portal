package com.dbms.project.api;


import com.dbms.project.model.Post;
import com.dbms.project.model.User;
import com.dbms.project.service.CompanyService;
import com.dbms.project.service.PostService;
import com.dbms.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.time.Instant;

@Controller
public class ForumController {

    @Autowired
    PostService postService;
    @Autowired
    StudentService studentService;
    @Autowired
    CompanyService companyService;

    @GetMapping("/post/create")
    public String postCreate(Authentication auth){
        User user = (User)auth.getPrincipal();
        if(user.getDesignation().equals("Student")){
            if(!studentService.studentExists(Integer.parseInt(user.getUsername()))) return "redirect:/profile";
        }
        else if(user.getDesignation().equals("Company")){
            if(!companyService.companyExists(Integer.parseInt(user.getUsername()))) return "redirect:/company-profile";
        }
        return "post-create";
    }

    @PostMapping("post/create")
    public String insertPost(@ModelAttribute Post post, Model model, Authentication auth){

        System.out.println(post.getTitle());
        post.setAuthorId(Integer.parseInt(auth.getName()));
        post.setTimestamp(Timestamp.from(Instant.now()));
        postService.insertPost(post);
        Integer id = postService.getLastPost();
        return "redirect:/post/" + id.toString();
    }

    @GetMapping("post/{id}")
    public String postDetail(Model model, @PathVariable Integer id){
        Post post = postService.getPostByID(id);
        model.addAttribute("post", post);
        model.addAttribute("authorName", postService.getName(post.getAuthorId()));
        return "post";
    }

    @GetMapping("/forum")
    public String allPosts(Model model, Authentication auth){
        if(auth.isAuthenticated()){
            User user = (User)auth.getPrincipal();
            if(user.getDesignation().equals("Student")){
                if(!studentService.studentExists(Integer.parseInt(user.getUsername()))) return "redirect:/profile";
            }
            else if(user.getDesignation().equals("Company")){
                if(!companyService.companyExists(Integer.parseInt(user.getUsername()))) return "redirect:/company-profile";
            }
            if(user.getDesignation().equals("Student")){
                model.addAttribute("posts", postService.getAllPosts());
            }
            else{
                model.addAttribute("posts", postService.getPostsByUser(Integer.parseInt(user.getUsername())));
            }
            return "/forum";
        }
        else{
            return "/login";
        }
    }

}
