package com.dbms.project.service;

import com.dbms.project.dao.CompanyDao;
import com.dbms.project.dao.PostDao;
import com.dbms.project.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyDao companyDao;

    @Autowired
    public CompanyService(CompanyDao companyDao){
        this.companyDao = companyDao;
    }

    public Company getCompanyByID(Integer ID){ return companyDao.getCompanyByID(ID); }

    public boolean companyExists(int ID) {
        return companyDao.companyExists(ID);
    }

    public void insertCompany(Company company){ companyDao.insertCompany(company); }

    public void updateCompany(Company company) { companyDao.updateCompany(company); }
    public List<Company> getAllCompanies(){ return companyDao.getAllCompanies(); }
}
