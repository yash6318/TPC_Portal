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
import java.util.Optional;

@Repository
public class WillingnessDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WillingnessDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void CreateTable(){
        final String sql = "CREATE TABLE IF NOT EXISTS WILLINGNESS(" +
                "companyID int," +
                "roleName varchar(200)," +
                "rollNo int," +
                "resumeName varchar(50)," +
                "foreign key (rollNo) references Student(rollNo)," +
                "foreign key (rollNo, resumeName) references Resume(rollNo, resumeName)," +
                "foreign key (companyID) references  Company(companyID)," +
                "foreign key (roleName, companyID) references Role(roleName, companyID)," +
                "primary key (rollNo, companyID, roleName))";
        jdbcTemplate.execute(sql);
    }

    public void insertWillingness(Willingness willingness){
        final String sql = "INSERT INTO WILLINGNESS(companyID, roleName, rollNo, resumeName) VALUES(?, ?, ?, ?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, willingness.getCompanyID());
            ps.setString(2, willingness.getRoleName());
            ps.setInt(3, willingness.getRollNo());
            ps.setString(4, willingness.getResumeName());
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

    public void deleteWillingness(Integer rollNo, Integer companyID, String roleName){
        final String sql = "DELETE FROM WILLINGNESS WHERE rollNo = ? and companyID = ? and roleName = ?";
        jdbcTemplate.update(sql, new Object[]{rollNo, companyID, roleName});
    }
    public Willingness getWillingness(Integer rollNo, Integer companyID, String roleName){
        final String sql = "SELECT * FROM WILLINGNESS WHERE rollNo = ? and companyID = ? and roleName = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{rollNo, companyID, roleName}, new BeanPropertyRowMapper<>(Willingness.class));
    }

    public boolean willingnessIsPresent(Integer rollNo, Integer companyID, String roleName) {
        final String sql = "SELECT COUNT(*) FROM WILLINGNESS WHERE rollNo = ? and companyID = ? and roleName = ?";
        int rs = jdbcTemplate.queryForObject(sql, new Object[]{rollNo, companyID, roleName}, Integer.class);
        return rs > 0;
    }
}
