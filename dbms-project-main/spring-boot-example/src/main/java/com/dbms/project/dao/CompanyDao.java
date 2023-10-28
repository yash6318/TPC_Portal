package com.dbms.project.dao;

import com.dbms.project.model.Company;
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
import java.util.Optional;

@Repository
public class CompanyDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompanyDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }
    public void insertCompany(Company company) {
        final String sql = "INSERT INTO Company(companyID, companyName, HREmail, HRPhone) VALUES(?, ?, ?, ?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, company.getCompanyID());
            ps.setString(2, company.getCompanyName());
            ps.setString(3, company.getHREmail());
            ps.setString(4, company.getHRPhone());
            return ps;
        }, keyholder);
    }


    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS COMPANY(" +
                "companyID int primary key, " +
                "companyName varchar(100), " +
                "HREmail varchar(100), " +
                "HRPhone char(10)," +
                "FOREIGN KEY (companyID) references User(username)" +
                ")";
        jdbcTemplate.execute(sql);
    }
    public List<Company> getAllCompany(){
        final String sql = "SELECT * from COMPANY";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Company.class));
    }

    public Company getCompanyByID(Integer ID){
        String sql = "SELECT * from COMPANY where COMPANYID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{ID}, new BeanPropertyRowMapper<>(Company.class));
    }

    public boolean companyExists(Integer ID) {
        String query = "SELECT count(*) FROM company WHERE companyID = ?";
        int count = jdbcTemplate.queryForObject(query, new Object[]{ID}, Integer.class);
        return count > 0;
    }

    public void updateCompany(Company company) {
        final String updateSql = "UPDATE company SET " +
                "companyName = ?, " +
                "HREmail = ?, " +
                "HRPhone = ? " +
                "where companyID = ? ";

        jdbcTemplate.update(updateSql,
                company.getCompanyName(),
                company.getHREmail(),
                company.getHRPhone(),
                company.getCompanyID()
        );
    }

    public List<Company> getAllCompanies(){
        String sql = "SELECT * FROM COMPANY";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Company.class));
    }
}
