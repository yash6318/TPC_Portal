package com.dbms.project.service;

import com.dbms.project.dao.PostDao;
import com.dbms.project.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostDao postDao;

    @Autowired
    public PostService(PostDao postDao){
        this.postDao = postDao;
    }

    public void insertPost(Post post){
        postDao.insertPost(post);
    }

    public List<Post> getAllPosts(){
        return postDao.getAllPosts();
    }
}
