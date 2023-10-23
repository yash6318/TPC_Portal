package com.dbms.project.service;

import com.dbms.project.dao.RoleDao;
import com.dbms.project.model.Role;
import com.dbms.project.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao){
        this.roleDao = roleDao;
    }

    public void insertRole(Role role){
        roleDao.insertRole(role);
    }

    public List<Role> getRoles(Student student){
        return roleDao.getRoles(student);
    }
}
