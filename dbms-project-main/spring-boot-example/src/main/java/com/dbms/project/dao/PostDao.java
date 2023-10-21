package com.dbms.project.dao;

import com.dbms.project.model.Company;
import com.dbms.project.model.Post;
import com.dbms.project.model.Student;
import com.dbms.project.model.User;
import com.dbms.project.service.CompanyService;
import com.dbms.project.service.StudentService;
import com.dbms.project.service.UserService;
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
public class PostDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;
    @Autowired
    CompanyService companyService;
    @Autowired
    StudentService studentService;
    @Autowired
    public PostDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertPost(Post post) {
        final String sql = "INSERT INTO Post(title, content, `timestamp`, authorId) VALUES(?, ?, ?, ?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setTimestamp(3, post.getTimestamp());
            ps.setInt(4, post.getAuthorId());
            return ps;
        }, keyholder);
    }


    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS POST(postid int primary key AUTO_INCREMENT, title text, content text, `timestamp` timestamp, authorid int)";
        jdbcTemplate.execute(sql);
    }
    public List<Post> getAllPosts(){
        final String sql = "SELECT * from POST";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class));
    }

    public List<Post> getPostsByUser(Integer username) {
        final String sql = "SELECT * from POST where AUTHORID = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Post.class));
    }

    public Post getPostByID(Integer postid) {
        final String sql = "SELECT * from POST where POSTID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{postid}, new BeanPropertyRowMapper<>(Post.class));
    }
    public Integer getLastPost(){
        final String sql = "SELECT MAX(postId) from POST";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    public String getName(Integer ID){
        String designation = userService.getDesignationFromID(ID);
        if(designation.equals("Student")){
            Student student = studentService.getStudentByRollNo(ID);
            return student.getFirstName() + " " + student.getLastName();
        }
        else if(designation.equals("Company")){
            Company company = companyService.getCompanyByID(ID);
            return company.getCompanyName();
        }
        else{
            return "ADMIN";
        }
    }
}
