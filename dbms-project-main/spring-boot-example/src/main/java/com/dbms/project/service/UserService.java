package com.dbms.project.service;

import com.dbms.project.dao.UserDao;
import com.dbms.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

//    public List<LeavesAndSalaries> getAllLeavesAndSalaries(int id) {
//        return employeeDao.getAllLeavesAndSalaries(id);
//    }

//    public List<WorkExperience> getAllWorkExperience(int id) {
//        return employeeDao.getAllWorkExperience(id);
//    }

//    public User getUserById(int id) {
//        return userDao.getUserById(id);
//    }

//    public int deleteUser(int id) {
//        return userDao.deleteUser(id);
//    }

//    public int updateUser(int id, User user) {
//        return userDao.updateUser(id, user);
//    }

//    public int updatePassword(int id, User user) {
//        return userDao.updatePassword(id, user);
//    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Integer uzairname = Integer.parseInt(username);
        return userDao.findUserByUsername(uzairname).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

    public User getUserByUsername(Integer username) {
        return userDao.findUserByUsername(username).get();
    }

    public boolean alreadyExists(String username) {
        return userDao.alreadyExists(username);
    }
}
