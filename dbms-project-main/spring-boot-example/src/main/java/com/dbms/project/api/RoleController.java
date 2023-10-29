package com.dbms.project.api;


import com.dbms.project.model.*;
import com.dbms.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RoleController {

    @Autowired
    CompanyService companyService;
    @Autowired
    RoleService roleService;
    @Autowired
    BranchService branchService;
    @Autowired
    WillingnessService willingnessService;
    @Autowired
    StudentService studentService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    EmailService emailService;


    @GetMapping("/role/create")
    public String testRoleCreate(Model model, Authentication auth){
        User user = (User)auth.getPrincipal();
        if(user.getDesignation().equals("Student")){
            model.addAttribute("errorMessage", "Unauthorized Request!");
            return "custom-error";
        }
        if(companyService.companyExists(Integer.parseInt(user.getUsername()))){
            model.addAttribute("brancheis", branchService.getAllBranches());
            return "create-role";
        }
        model.addAttribute("companyID", user.getUsername());
        return "redirect:/company-profile/create";
    }

    @PostMapping("/role/create")
    public String testInsertRole(@ModelAttribute("role") Role role, @RequestParam(value = "brlist") List<String> BranchList, Authentication auth){
        role.setCompanyID(Integer.parseInt(((User)auth.getPrincipal()).getUsername()));
        role.setBranchValue(branchService.getBin(BranchList));
        System.out.println(role);
        for(String str: BranchList){
            System.out.println(str);
        }
        roleService.insertRole(role);
        return "redirect:/company-home";
    }


    // roles only visible to company;
    @GetMapping("/roles")
    public String allRoles(Authentication auth, Model model){
        User user = (User)auth.getPrincipal();
        if(user.getDesignation().equals("Student")){
            model.addAttribute("errorMessage", "Unauthorized Request!");
            return "custom-error";
        }
        List<Role> roles = roleService.getRolesByCompanyId(Integer.parseInt(((User)auth.getPrincipal()).getUsername()));
        if(roles.isEmpty()) return "redirect:/role/create";
        List<List<String>> branches = new ArrayList<>();
        List<String> company = new ArrayList<>();
        for(Role role: roles){
            branches.add(branchService.getBranchFromBin(role.getBranchValue()));
            company.add(companyService.getCompanyByID(role.getCompanyID()).getCompanyName());
        }
        model.addAttribute("roles", roles);
        model.addAttribute("branches", branches);
        model.addAttribute("company", company);
        return "roles";
    }

    @GetMapping("/roles/{id}")
    public String willingStudents(Authentication auth, @PathVariable("id") String id, Model model){
        User user = (User)auth.getPrincipal();
        if(user.getDesignation().equals("Student")){
            model.addAttribute("errorMessage", "Unauthorized Request!");
            return "custom-error";
        }
        List<Willingness> wills = willingnessService.getWillingnessByRole(id, Integer.parseInt(user.getUsername()));
        List<Student> students = new ArrayList<>();
        List<Resume> resumes = new ArrayList<>();

        for(Willingness x : wills){
            students.add(studentService.getStudentByRollNo(x.getRollNo()));
            resumes.add(resumeService.getResumeByKey(x.getRollNo(), x.getResumeName()));
        }

        model.addAttribute("roleName", id);
        model.addAttribute("students", students);
        model.addAttribute("resumes", resumes);
        return "willingstudents";
    }
    @GetMapping("/roles/{id}/download")
    public void exportToExcel(HttpServletResponse response, Authentication auth, @PathVariable String id, Model model) throws IOException {

        User user = (User)auth.getPrincipal();
//        if(user.getDesignation().equals("Student")){
//            model.addAttribute("errorMessage", "Unauthorized Request!");
//            return "custom-error";
//        }
        List<Willingness> wills = willingnessService.getWillingnessByRole(id, Integer.parseInt(user.getUsername()));
        List<Student> students = new ArrayList<>();
        List<Resume> resumes = new ArrayList<>();

        for(Willingness x : wills){
            students.add(studentService.getStudentByRollNo(x.getRollNo()));
            resumes.add(resumeService.getResumeByKey(x.getRollNo(), x.getResumeName()));
        }

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = Role_" + id + "_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        UserExcelExporter excelExporter = new UserExcelExporter(students, resumes);

        excelExporter.export(response);
//        return "redirect: /roles";
    }

    @GetMapping("/roles/{id}/sendmail")
    public String recieve(@PathVariable String id){
        System.out.print(id);
        return ("redirect:/roles/"+id);
    }

    @PostMapping("/roles/{id}/sendmail")
    public String sendMail(@RequestParam(value = "list") List<String> StudentList, @PathVariable String id, Authentication auth, @RequestParam(value = "customText") String customText,
     @RequestParam(value = "customSub") String customSub, Model model){
        User user = (User)auth.getPrincipal();
        List<String> names = new ArrayList<>();
        List<String> mailIds= new ArrayList<>();
        for(String s : StudentList){
            String[] parts = s.split("/");
            names.add(parts[0]);
            mailIds.add(parts[1]);
        }

        System.out.println(names);
        System.out.println(customText);
        System.out.println(customSub);

        if(user.getDesignation().equals("Admin")){
            emailService.sendEmail(names, mailIds, "Admin", "Admin", customText, customSub);
        }
        else if(user.getDesignation().equals("Company")){
            emailService.sendEmail(names, mailIds, companyService.getCompanyByID(Integer.parseInt(user.getUsername())).getCompanyName(), id, customText, customSub);
        }
        else{
            model.addAttribute("errorMessage", "Unauthorized Request!");
            return "custom-error";
        }

        // show a notif or something saying: mail sent;
        return ("redirect:/roles/" + id);
    }

}
