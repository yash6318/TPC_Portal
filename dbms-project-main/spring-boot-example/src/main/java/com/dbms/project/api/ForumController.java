package com.dbms.project.api;


import com.dbms.project.model.Post;
import com.dbms.project.model.User;
import com.dbms.project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Controller
public class ForumController {

    @Autowired
    PostService postService;

    @GetMapping("/post/create")
    public String postCreate(){
        return "post-create";
    }

    @PostMapping("post/create")
    public String insertPost(@ModelAttribute Post post, Model model, Authentication auth){
        System.out.println(post.getTitle());
        post.setAuthorId(Integer.parseInt(auth.getName()));
        post.setTimestamp(Timestamp.from(Instant.now()));
        postService.insertPost(post);
        return "post-create";
    }

    @GetMapping("post/{id}")
    public String postDetail(Model model, @PathVariable Integer id){
        model.addAttribute("post", postService.getPostByID(id));
        return "post";
    }

    @GetMapping("/forum")
    public String allPosts(Model model, Authentication auth){
        if(auth.isAuthenticated()){
            User user = (User)auth.getPrincipal();
            if(user.getDesignation().equals("Student")){
                model.addAttribute("posts", postService.getAllPosts());
            }
            else{
                model.addAttribute("posts", postService.getPostsByUser(Integer.parseInt(user.getUsername())));
            }
            return "/forum";
        }
        else{
            model.addAttribute("error", "Please sign in");
            return "/forum";
        }
    }
}
