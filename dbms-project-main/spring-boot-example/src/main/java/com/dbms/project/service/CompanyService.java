package com.dbms.project.service;

import com.dbms.project.dao.CompanyDao;
import com.dbms.project.dao.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyDao companyDao;

    @Autowired
    public CompanyService(CompanyDao companyDao){
        this.companyDao = companyDao;
    }
}
