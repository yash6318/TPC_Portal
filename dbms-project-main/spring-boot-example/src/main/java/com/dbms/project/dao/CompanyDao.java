package com.dbms.project.dao;

import com.dbms.project.model.Company;
import com.dbms.project.model.Post;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CompanyDao {
    private final JdbcTemplate jdbcTemplate;

    public CompanyDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }
    public void insertCompany(Company company) {
        final String sql = "INSERT INTO Company(companyName, HREmail, HRPhone) VALUES(?, ?, ?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, company.getCompanyName());
            ps.setString(2, company.getHREmail());
            ps.setString(3, company.getHRPhone());
            return ps;
        }, keyholder);
    }


    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS COMPANY(companyName varchar(100) primary key, HREmail varchar(100), HRPhone char(10))";
        jdbcTemplate.execute(sql);
    }
    public List<Company> getAllCompany(){
        final String sql = "SELECT * from COMPANY";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Company.class));
    }
}
