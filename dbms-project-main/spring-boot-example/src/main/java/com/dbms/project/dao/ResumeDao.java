package com.dbms.project.dao;

//import com.dbms.project.model.Resume;
import com.dbms.project.model.Resume;
import com.dbms.project.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResumeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertResume(Resume resume) {
        final String sql = "INSERT INTO Resume(resumeName, resumeLink, rollNo,isVerified) VALUES(?, ?, ?,?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, resume.getResumeName());
            ps.setString(2, resume.getResumeLink());
            ps.setInt(3, resume.getRollNo());
            ps.setInt(4,resume.getIsVerified());
            return ps;
        }, keyholder);
    }


    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS RESUME(resumeName varchar(50), resumeLink varchar(200), rollNo int,isVerified int,primary key(rollNo, resumeName))";
        jdbcTemplate.execute(sql);
    }


    public List<Resume> getResumesByUser(Integer username) {
        final String sql = "SELECT * from RESUME where rollNo = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Resume.class));
    }

    public List<Resume> getVerifiedResumesByUser(Integer username) {
        final String sql = "SELECT * from RESUME where rollNo = ? and IsVerified = 1";
        return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Resume.class));
    }

    public String getResumeLinkByKey(Integer rollNo, String resumeName){
        final String sql = "SELECT RESUMELINK from RESUME where rollNo = ? and resumeName = ?";
        return (jdbcTemplate.queryForObject(sql, new Object[]{rollNo, resumeName}, String.class));
    }

    public Resume getResumeByKey(Integer rollNo, String resumeName){
        final String sql = "SELECT * from RESUME where rollNo = ? and resumeName = ?";
        return (jdbcTemplate.queryForObject(sql, new Object[]{rollNo, resumeName}, new BeanPropertyRowMapper<>(Resume.class)));
    }

    public void updateResume(String a,String b){
//        final String sql = "INSERT INTO Resume(resumeName, resumeLink, rollNo,isVerified) VALUES(?, ?, ?,?)";
        final String sql="UPDATE resume SET isVerified=1 where resumeName=(?) and resumeLink= (?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, a);
            ps.setString(2, b);
            return ps;
        }, keyholder);
    }

    public List<Resume> getAllResumes() {
        final String sql = "SELECT * from RESUME";
        return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(Resume.class));
    }

}
