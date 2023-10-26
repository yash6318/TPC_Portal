package com.dbms.project.api;


import com.dbms.project.model.Company;
import com.dbms.project.model.Post;
import com.dbms.project.model.User;
import com.dbms.project.service.CompanyService;
import com.dbms.project.service.PostService;
import com.dbms.project.service.StudentService;
import com.dbms.project.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class ForumController {

    @Autowired
    PostService postService;
    @Autowired
    StudentService studentService;
    @Autowired
    CompanyService companyService;
    @Autowired
    UserService userService;

    @GetMapping("/post/create")
    public String postCreate(Authentication auth, Model model){
        User user = (User)auth.getPrincipal();
        if(user.getDesignation().equals("Student")){
            model.addAttribute("errorMessage", "Unauthorized Request!");
            return "custom-error";
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
    public String postDetail(Model model, @PathVariable Integer id, Authentication auth){
        Post post = postService.getPostByID(id);
        model.addAttribute("post", post);
        model.addAttribute("authorName", postService.getName(post.getAuthorId()));
        model.addAttribute("designation", ((User)auth.getPrincipal()).getDesignation());
        return "post";
    }

    @GetMapping("/forum")
    public String allPosts(Model model, Authentication auth){
        User user = (User)auth.getPrincipal();
        if(user.getDesignation().equals("Student")){
            if(!studentService.studentExists(Integer.parseInt(user.getUsername()))) return "redirect:/profile";
        }
        else if(user.getDesignation().equals("Company")){
            if(!companyService.companyExists(Integer.parseInt(user.getUsername()))) return "redirect:/company-profile";
        }
        List<Post> posts = postService.getAllPosts();

        List<String> authors = new ArrayList<>();
        for(Post post:posts){
            User author = userService.getUserByUsername(post.getAuthorId());
            if(author.getDesignation().equals("Company"))
                authors.add(companyService.getCompanyByID(post.getAuthorId()).getCompanyName());
            else
                authors.add("Admin");
        }
        model.addAttribute("posts", posts);
        model.addAttribute("authors", authors);
        model.addAttribute("designation", user.getDesignation());
        return "/forum";
    }


}
