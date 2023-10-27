package com.dbms.project.dao;


import com.dbms.project.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDao {
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateStudent(Student student){
        final String updateSql = "UPDATE student SET " +
                    "firstName = ?, " +
                    "middleName = ?, " +
                    "lastName = ?, " +
                    "instituteID = ?, " +
                    "personalID = ?, " +
                    "sex = ?, " +
                    "mobileNumber = ?, " +
                    "street = ?, " +
                    "city = ?, " +
                    "state = ?, " +
                    "postalCode = ?, " +
                    "dateOfBirth = ?, " +
                    "programme = ?, " +
                    "branch = ?, " +
                    "totalBacklogs = ?, " +
                    "activeBacklogs = ?, " +
                    "cpi = ?, " +
                    "classXBoard = ?, " +
                    "Xpercentage = ?, " +
                    "classXIIBoard = ?, " +
                    "XIIpercentage = ?, " +
                    "passingYear = ? " +
                    "WHERE rollNo = ?";

            jdbcTemplate.update(updateSql,
                    student.getFirstName(),
                    student.getMiddleName(),
                    student.getLastName(),
                    student.getInstituteID(),
                    student.getPersonalID(),
                    student.getSex(),
                    student.getMobileNumber(),
                    student.getStreet(),
                    student.getCity(),
                    student.getState(),
                    student.getPostalCode(),
                    student.getDateOfBirth(),
                    student.getProgramme(),
                    student.getBranch(),
                    student.getTotalBacklogs(),
                    student.getActiveBacklogs(),
                    student.getCpi(),
                    student.getClassXBoard(),
                    student.getXpercentage(),
                    student.getClassXIIBoard(),
                    student.getXIIpercentage(),
                    student.getPassingYear(),
                    student.getRollNo()
            );
    }



    public void insertStudent(Student student){
        final String insertSql="insert into student(firstName,middleName,lastName,rollNo, instituteID, personalID, sex, mobileNumber, street, city, state, postalCode, dateOfBirth, " +
                "programme, branch, totalBacklogs, activeBacklogs, cpi, classXBoard, Xpercentage, classXIIBoard, XIIpercentage, passingYear)" +
            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getMiddleName());
            ps.setString(3, student.getLastName());
            ps.setInt(4,student.getRollNo());
            ps.setString(5,student.getInstituteID());
            ps.setString(6,student.getPersonalID());
            ps.setString(7,student.getSex());
            ps.setString(8, student.getMobileNumber());
            ps.setString(9,student.getStreet());
            ps.setString(10,student.getCity());
            ps.setString(11,student.getState());
            ps.setString(12,student.getPostalCode());
            ps.setDate(13,student.getDateOfBirth());
            ps.setString(14,student.getProgramme());
            ps.setString(15,student.getBranch());
            ps.setInt(16,student.getTotalBacklogs());
            ps.setInt(17,student.getActiveBacklogs());
            ps.setFloat(18,student.getCpi());
            ps.setString(19,student.getClassXBoard());
            ps.setFloat(20,student.getXpercentage());
            ps.setString(21,student.getClassXIIBoard());
            ps.setFloat(22,student.getXIIpercentage());
            ps.setString(23,student.getPassingYear());
            return ps;
        }, keyholder);

    }
    public boolean studentExists(Integer rollNo) {
        String query = "SELECT count(*) FROM student WHERE rollNo = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, rollNo);
        return count > 0;
    }

    public void CreateTable(){
        String sql="create table if not exists student(" +
                "firstName varchar(255) not null," +
                "middleName varchar(255) default null," +
                "lastName varchar(255)," +
                "rollNo int primary key," +
                "instituteID varchar(255) not null," +
                "personalID varchar(255) not null," +
                "sex enum('Male', 'Female') not null," +
                "mobileNumber char(10) not null," +
                "street varchar(50) not null," +
                "city varchar(50) not null," +
                "state varchar(50) not null," +
                "postalCode char(6) not null," +
                "dateOfBirth date not null," +
                "programme varchar(255) not null," +
                "branch varchar(255) not null," +
                "totalBacklogs int default 0," +
                "activeBacklogs int default 0," +
                "cpi float not null," +
                "classXBoard varchar(255) not null," +
                "Xpercentage float not null," +
                "classXIIBoard varchar(255) not null," +
                "XIIpercentage float not null," +
                "passingYear char(4) not null" +
                ")";
        jdbcTemplate.execute(sql);
    }

    public Student getStudentByRollNo(Integer rollNo) {
            String sql2 = "SELECT * from student where rollNo = ?";
            return jdbcTemplate.queryForObject(sql2, new Object[]{rollNo}, new BeanPropertyRowMapper<>(Student.class));
    }

    public List<Student> getAllStudents(){
        String sql = "SELECT * FROM STUDENT";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class));
    }
}
