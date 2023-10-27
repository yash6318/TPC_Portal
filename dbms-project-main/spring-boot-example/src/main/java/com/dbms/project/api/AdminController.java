package com.dbms.project.api;

import com.dbms.project.model.*;
import com.dbms.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    StudentService studentService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    CompanyService companyService;
    @Autowired
    RoleService roleService;
    @Autowired
    BranchService branchService;
    @Autowired
    WillingnessService willingnessService;

    @GetMapping("/admin-home")
    public String adminHome(){
        return "admin-home";
    }

    @GetMapping("/approve-resume")
    public String getResumeList(Model model){
        List<Student> studentList = studentService.getAllStudents();
        List<List<Resume>> allResumes = new ArrayList<>();
        for(Student student: studentList){
            List<Resume> res_stud = resumeService.getResumesByUser(student.getRollNo());
            allResumes.add(res_stud);
        }
        model.addAttribute("studentList", studentList);
        model.addAttribute("allResumes", allResumes);
        return "approve-resume";
    }

    @GetMapping("/approve-resume/{resume_id}")
    public String approveResume(@PathVariable("resume_id") String resume_id){
        System.out.println(resume_id);
        String[] split_str = resume_id.split("\\|");
        for(String str: split_str)  System.out.println(str);
        resumeService.updateResume(split_str[0], Integer.parseInt(split_str[1]));
        return "redirect:/approve-resume";
    }

    @GetMapping("/roles-all")
    public String getAllRoles(Model model){
        List<Company> companyList = companyService.getAllCompanies();
        List<List<Role>> allRoles = new ArrayList<>();
        List<List<List<String>>> allBranches = new ArrayList<>();
        for(Company company: companyList){
            List<Role> roles = roleService.getRolesByCompanyId(company.getCompanyID());
            List<List<String>> branches2 = new ArrayList<>();
            for(Role role: roles){
                List<String> branches1 = branchService.getBranchFromBin(role.getBranchValue());
                branches2.add(branches1);
            }
            allBranches.add(branches2);
            allRoles.add(roles);
        }
        model.addAttribute("companyList", companyList);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("allBranches", allBranches);
        return "roles-all";
    }

    @GetMapping("/roles-all/{role_id}")
    public String getWillingStudents(@PathVariable("role_id") String role_id, Model model){
        String[] split_str = role_id.split("\\|");
        System.out.println(split_str[0] + " " + split_str[1]);
        List<Willingness> willingList = willingnessService.getWillingnessByRole(split_str[0], Integer.parseInt(split_str[1]));
        List<Student> studentList = new ArrayList<>();
        List<Resume> resumeList = new ArrayList<>();
        for(Willingness willingness: willingList){
            studentList.add(studentService.getStudentByRollNo(willingness.getRollNo()));
            resumeList.add(resumeService.getResumeByKey(willingness.getRollNo(), willingness.getResumeName()));
        }
        model.addAttribute("studentList", studentList);
        model.addAttribute("resumeList", resumeList);
        return "admin-willing-students";
    }
}
