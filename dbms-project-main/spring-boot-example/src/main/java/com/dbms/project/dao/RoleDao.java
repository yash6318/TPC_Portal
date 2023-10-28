package com.dbms.project.dao;

import com.dbms.project.model.Company;
import com.dbms.project.model.Role;
import com.dbms.project.model.Student;
import com.dbms.project.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Repository
public class RoleDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    BranchService branchService;
    @Autowired
    public RoleDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void CreateTable(){
        final String roleSql = "CREATE TABLE IF NOT EXISTS ROLE(" +
                "ROLENAME varchar(200), " +
                "COMPANYID int, " +
                "JOBDESCRIPTION text," +
                "STIPEND int, " +
                "BTECH boolean, " +
                "IDD boolean, " +
                "MINCPI float," +
                "MINPASSINGYEAR char(4)," +
                "MAXPASSINGYEAR char(4)," +
                "MAXACTIVEBACKLOGS int," +
                "MAXTOTALBACKLOGS int," +
                "DEADLINE datetime," +
                "BRANCHVALUE int," +
                "PRIMARY KEY (ROLENAME, COMPANYID)," +
                "FOREIGN KEY (companyID) references Company(companyID)" +
                ")";
        jdbcTemplate.execute(roleSql);

    }

    public void insertRole(Role role){
        final String insertSql="insert into ROLE(ROLENAME, COMPANYID,JOBDESCRIPTION,STIPEND,BTECH,IDD,MINCPI,MINPASSINGYEAR,MAXPASSINGYEAR,MAXACTIVEBACKLOGS, MAXTOTALBACKLOGS, DEADLINE, BRANCHVALUE)" +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, role.getRoleName());
            ps.setInt(2, role.getCompanyID());
            ps.setString(3, role.getJobDescription());
            ps.setInt(4,role.getStipend());
            ps.setInt(5,role.getBTech() ? 1 : 0);
            ps.setInt(6,role.getIdd() ? 1 : 0);
            ps.setFloat(7,role.getMinCpi());
            ps.setString(8, role.getMinPassingYear());
            ps.setString(9,role.getMaxPassingYear());
            ps.setInt(10,role.getMaxActiveBacklogs());
            ps.setInt(11,role.getMaxTotalBacklogs());
            ps.setTimestamp(12, java.sql.Timestamp.valueOf(role.getDeadLine()));
            ps.setInt(13, role.getBranchValue());
            return ps;
        }, keyholder);

    }

    public List<Role> getRoles(Student student){
        int studentBranchValue = (1 << branchService.getBranchID(student.getBranch()));
        Date javaDate = new Date(); // Replace this with your Java Date

        SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Format the Java Date to a MySQL DATETIME string
        String mysqlDatetime = mysqlDateFormat.format(javaDate);
        final String getRolesSql = "SELECT * from ROLE AS S where IF(?, BTECH = 1, IDD = 1) and MINCPI <= ? and MINPASSINGYEAR <= ? and MAXPASSINGYEAR >= ? and MAXACTIVEBACKLOGS >= ? and MAXTOTALBACKLOGS >= ? and (? & BRANCHVALUE) > 0 and DEADLINE>?";
        return jdbcTemplate.query(getRolesSql, new Object[]{student.getProgramme().equals("BTech"),student.getCpi(),student.getPassingYear(), student.getPassingYear(), student.getActiveBacklogs(), student.getTotalBacklogs(), studentBranchValue,mysqlDatetime},new BeanPropertyRowMapper<>(Role.class));
    }
    public List<Role> getRolesByCompanyId(Integer companyId){
        final String getRolesSql = "SELECT * from ROLE where CompanyID = ?";
        return jdbcTemplate.query(getRolesSql, new Object[]{companyId}, new BeanPropertyRowMapper<>(Role.class));
    }

    public List<Role> getRolesByCompanyIdRole(Integer companyId,String role){
        final String getRolesSql = "SELECT * from ROLE where CompanyID = ? and ROLENAME=?";
        return jdbcTemplate.query(getRolesSql, new Object[]{companyId,role}, new BeanPropertyRowMapper<>(Role.class));
    }

}
