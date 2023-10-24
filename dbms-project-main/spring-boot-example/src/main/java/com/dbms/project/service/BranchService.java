package com.dbms.project.service;

import com.dbms.project.dao.BranchDao;
import com.dbms.project.model.Branch;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchService {
    private final BranchDao branchDao;

    public BranchService(BranchDao branchDao) {
        this.branchDao = branchDao;
    }

    public void insertBranch(Branch branch){ branchDao.insertBranch(branch); }

    public Integer getBranchID(String BranchName){ return branchDao.getBranchID(BranchName); }

    public String getBranchName(Integer BranchID){ return branchDao.getBranchName(BranchID); }

    public Integer getBin(List<String> BranchList) {
        int value = 0;
        for(String br: BranchList){
            int id = branchDao.getBranchID(br);
            value += (1 << id);
        }
        return value;
    }

    public List<String> getBranchFromBin(Integer value){
        List<String> ans = new ArrayList<>();
        for(int i = 1; i <= 10; i++){
            if((value & (1 << i)) > 0){
                String str = getBranchName(i);
                if(str != null) ans.add(getBranchName(i));
            }
        }
        return ans;
    }

    public List<Branch> getAllBranches(){ return branchDao.getAllBranches(); }
}
