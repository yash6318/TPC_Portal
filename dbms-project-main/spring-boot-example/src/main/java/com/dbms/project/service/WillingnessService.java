package com.dbms.project.service;

import com.dbms.project.dao.WillingnessDao;
import com.dbms.project.model.Willingness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WillingnessService {
    private final WillingnessDao willingnessDao;

    @Autowired
    public WillingnessService(WillingnessDao willingnessDao){
        this.willingnessDao = willingnessDao;
    }

    public void insertWillingness(Willingness willingness){
        willingnessDao.insertWillingness(willingness);
    }

    public List<Willingness> getWillingnessByRollNo(Integer rollNo){
       return willingnessDao.getWillingnessByRollNo(rollNo);
    }

    public List<Willingness> getWillingnessByRole(String roleName, Integer companyID){
       return willingnessDao.getWillingnessByRole(roleName, companyID);
    }


}
