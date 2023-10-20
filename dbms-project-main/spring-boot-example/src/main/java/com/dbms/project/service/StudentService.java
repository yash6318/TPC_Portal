package com.dbms.project.service;

import com.dbms.project.dao.StudentDao;
import com.dbms.project.dao.UserDao;
import com.dbms.project.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    public UserDao user;
//    private final DataSource dataSource;
//    public void saveOrUpdateStudent(Student student){
//        studentDao.insertOrUpdateStudent(student);
//    }
    public Student getStudentByRollNo(Integer rollNo) {
        // Fetch student details using the StudentDao
        return studentDao.getStudentByRollNo(rollNo);
    }
    public boolean studentExists(Integer rollNo) {
        return studentDao.studentExists(rollNo);
    }

    public void insertStudent(Student student){
        studentDao.insertStudent(student);
    }

    public void updateStudent(Student student){
        studentDao.updateStudent(student);
    }
}

/*
@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    public Student saveOrUpdateStudent(Student student) {
        // Validation and save/update logic using the StudentDao
        return studentDao.saveOrUpdateStudent(student);
    }

    public Student getStudentByRollNo(String rollNo) {
        // Fetch student details using the StudentDao
        return studentDao.getStudentByRollNo(rollNo);
    }
}
*/
