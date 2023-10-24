package com.dbms.project.dao;

//import com.dbms.project.model.Resume;
import com.dbms.project.model.Resume;
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
//    public List<Resume> getAllPosts(){
//        final String sql = "SELECT * from RESUME";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class));
//    }

    public List<Resume> getResumesByUser(Integer username) {
        final String sql = "SELECT * from RESUME where rollNo = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Resume.class));
    }
//
//    public Post getPostByID(Integer postid) {
//        final String sql = "SELECT * from POST where POSTID = ?";
//        return jdbcTemplate.queryForObject(sql, new Object[]{postid}, new BeanPropertyRowMapper<>(Post.class));
//    }
}
