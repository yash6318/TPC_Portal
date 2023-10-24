package com.dbms.project.dao;

import com.dbms.project.model.Role;
import com.dbms.project.model.Willingness;
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
public class WillingnessDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WillingnessDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void CreateTable(){
        final String sql = "CREATE TABLE IF NOT EXISTS WILLINGNESS(" +
                "rollNo int," +
                "companyId int," +
                "resumeName varchar(50)," +
                "roleName varchar(200)," +
                "foreign key (rollNo) references Student(rollNo)," +
                "foreign key (rollNo, resumeName) references Resume(rollNo, resumeName)," +
                "foreign key (companyId) references  Company(companyId)," +
                "foreign key (roleName, companyId) references Role(roleName, companyId)," +
                "primary key (rollNo, companyId, roleName))";
        jdbcTemplate.execute(sql);
    }

    public void insertWillingness(Willingness willingness){
        final String sql = "INSERT INTO WILLINGNESS VALUES(?, ?, ?, ?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, willingness.getRollNo());
            ps.setInt(2, willingness.getCompanyID());
            ps.setString(3, willingness.getResumeName());
            ps.setString(4, willingness.getRoleName());
            return ps;
        }, keyholder);
    }

    public List<Willingness> getWillingnessByRollNo(Integer rollNo){
        final String sql = "SELECT * FROM WILLINGNESS WHERE ROLLNo = ?";
        return jdbcTemplate.query(sql, new Object[]{rollNo}, new BeanPropertyRowMapper<>(Willingness.class));
    }

    public List<Willingness> getWillingnessByRole(String roleName, Integer companyID){
        final String sql = "SELECT * FROM WILLINGNESS WHERE ROLENAME = ? and COMPANYID = ?";
        return jdbcTemplate.query(sql, new Object[]{roleName, companyID}, new BeanPropertyRowMapper<>(Willingness.class));
    }

}
