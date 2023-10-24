package com.dbms.project.dao;

import com.dbms.project.model.Branch;
import com.dbms.project.model.Post;
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
public class BranchDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BranchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertBranch(Branch branch) {
        final String sql = "INSERT INTO Branch(BranchName) VALUES(?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, branch.getBranchName());
            return ps;
        }, keyholder);
    }


    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS BRANCH(BranchID int primary key AUTO_INCREMENT, BranchName VARCHAR(100))";
        jdbcTemplate.execute(sql);
    }

    public Integer getBranchID(String BranchName){
        String sql = "SELECT BranchID FROM BRANCH WHERE BranchName = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{BranchName}, Integer.class);
    }

    public String getBranchName(Integer branchID) {
        String sql = "SELECT BranchName FROM BRANCH WHERE BranchID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {branchID}, String.class);
    }

    public List<Branch> getAllBranches(){
        String sql = "SELECT * FROM BRANCH";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Branch.class));
    }

    public void insertAll(){
        String sql = "SELECT COUNT(*) FROM BRANCH";
        int cnt = jdbcTemplate.queryForObject(sql, Integer.class);
        if(cnt == 0){
            Branch b1 = new Branch();
            b1.setBranchName("Computer Science and Engineering"); insertBranch(b1);
            b1.setBranchName("Mathematics and Computing"); insertBranch(b1);
            b1.setBranchName("Electronics Engineering"); insertBranch(b1);
            b1.setBranchName("Electrical Engineering"); insertBranch(b1);
            b1.setBranchName("Mechanical Engineering"); insertBranch(b1);
            b1.setBranchName("Chemical Engineering"); insertBranch(b1);
        }
    }

}
