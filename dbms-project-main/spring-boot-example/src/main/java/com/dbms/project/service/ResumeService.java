package com.dbms.project.service;
import com.dbms.project.dao.ResumeDao;
import com.dbms.project.model.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {
    private final ResumeDao resumeDao;

    @Autowired
    public ResumeService(ResumeDao resumeDao){
        this.resumeDao = resumeDao;
    }

    public void insertResume(Resume resume){
        resumeDao.insertResume(resume);
    }

//    public List<Post> getAllPosts(){
//        return postDao.getAllPosts();
//    }
//
    public List<Resume> getResumesByUser(Integer username) {return resumeDao.getResumesByUser(username);}

    public List<Resume> getVerifiedResumesByUser(Integer username) {return resumeDao.getVerifiedResumesByUser(username);}

    public String getResumeLinkByKey(Integer rollNo, String resumeName){
        return resumeDao.getResumeLinkByKey(rollNo, resumeName);
    }

    public Resume getResumeByKey(Integer rollNo, String resumeName){
        return resumeDao.getResumeByKey(rollNo, resumeName);
    }
//
//    public Post getPostByID(Integer postid) { return postDao.getPostByID(postid); }

    public List<Resume> getAllResumes() {return resumeDao.getAllResumes();}

    public void updateResume(String a,String b){
        resumeDao.updateResume(a,b);
    }

}
