package com.dbms.project.dao;

import com.dbms.project.model.User;
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
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUser(User user) {
        final String sql = "INSERT INTO User(username, password, designation) VALUES(?, ?, ?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getDesignation());
            return ps;
        }, keyholder);
    }

    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS USER(username varchar(255), password varchar(255), designation ENUM('Student', 'TPR', 'Company') NOT NULL DEFAULT ('Student'))";
        jdbcTemplate.execute(sql);
    }


    public List<User> getAllUsers() {
        final String sql = "SELECT * from USER";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
//
//    public User getUserById(int id) {
//        final String sql = "SELECT * from employee WHERE id = ?";
//        return jdbcTemplate.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<>(Employee.class));
//    }

//    public int deleteUser(int id) {
//        final String sql = "DELETE FROM USER WHERE id = ?";
//        return jdbcTemplate.update(sql, id);
//    }

//    public int updateEmployee(int id, Employee employee) {
//        final String sql = "UPDATE employee SET firstName = ?, middleName = ?, lastName = ?, designation = ?, salary = ?, contactNumber = ?, dateOfBirth = ?, emailId = ?, city = ?, state = ?, postalCode = ?, country = ?, street = ? WHERE id = ?";
//        return jdbcTemplate.update(sql, employee.getFirstName(), employee.getMiddleName(), employee.getLastName(), employee.getDesignation(), employee.getSalary(), employee.getContactNumber(), employee.getDateOfBirth(), employee.getEmailId(), employee.getCity(), employee.getState(), employee.getPostalCode(), employee.getCountry(), employee.getStreet(), id);
//    }

//    public int updatePassword(int id, Employee employee) {
//        final String sql = "UPDATE employee SET password = ? WHERE id = ?";
//        return jdbcTemplate.update(sql, employee.getPassword(), id);
//    }

    public Optional<User> findUserByUsername(String username) {
        final String sql = "SELECT * from user WHERE username = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[] {username}, new BeanPropertyRowMapper<>(User.class)));
    }

    public boolean alreadyExists(String username){
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        int rs =  this.jdbcTemplate.queryForObject(query, new Object[]{username}, Integer.class);
        return (rs != 0);
    }
}
