package com.dbms.project.dao;

import com.dbms.project.model.Company;
import com.dbms.project.model.Role;
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
import java.util.Objects;

@Repository
public class RoleDao {
    private final JdbcTemplate jdbcTemplate;

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
                "MINPASSINGYEAR year," +
                "MAXPASSINGYEAR year," +
                "MAXACTIVEBACKLOGS int," +
                "MAXTOTALBACKLOGS int," +
                "DEADLINE datetime," +
                "PRIMARY KEY (ROLENAME, COMPANYID))";
        jdbcTemplate.execute(roleSql);

        final String branchSql = "CREATE TABLE IF NOT EXISTS ROLE_BRANCHES(" +
                "ROLENAME varchar(200), " +
                "COMPANYID int, " +
                "BRANCH varchar(255), " +
                "PRIMARY KEY(ROLENAME,COMPANYID,BRANCH), " +
                "FOREIGN KEY(ROLENAME, COMPANYID) REFERENCES ROLE(ROLENAME, COMPANYID) ON DELETE CASCADE)";
        jdbcTemplate.execute(branchSql);

    }

    public void insertRole(Role role){
        final String insertSql="insert into ROLE(ROLENAME, COMPANYID,JOBDESCRIPTION,STIPEND,BTECH,IDD,MINCPI,MINPASSINGYEAR,MAXPASSINGYEAR,MAXACTIVEBACKLOGS, MAXTOTALBACKLOGS, DEADLINE)" +
                "values(?,?,?,?,?,?,?,?,?,?,?,?)";
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
            return ps;
        }, keyholder);

        for(String x : role.getBranches()){
            final String insertBranchSql = "insert into ROLE_BRANCHES VALUES(?,?,?)";
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertBranchSql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, role.getRoleName());
                ps.setInt(2, role.getCompanyID());
                ps.setString(3, x);
                return ps;
            }, keyholder);
        }
    }

    public List<Role> getRoles(Student student){
        final String getRolesSql = "SELECT * from ROLE AS S where IF(?, BTECH = 1, IDD = 1) and MINCPI <= ? and MINPASSINGYEAR <= ? and MAXPASSINGYEAR >= ? and MAXACTIVEBACKLOGS >= ? and MAXTOTALBACKLOGS >= ? and ? in (SELECT BRANCH FROM ROLE_BRANCHES AS T WHERE T.ROLENAME = S.ROLENAME and T.COMPANYID = S.COMPANYID)";
        return jdbcTemplate.query(getRolesSql, new Object[]{student.getProgramme().equals("BTech"),student.getCpi(),student.getPassingYear(), student.getPassingYear(), student.getActiveBacklogs(), student.getTotalBacklogs(), student.getBranch()},new BeanPropertyRowMapper<>(Role.class));
    }

}
