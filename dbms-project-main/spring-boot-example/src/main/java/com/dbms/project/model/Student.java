package com.dbms.project.model;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.*;
import java.time.Year;

@Data
public class Student{
    @Size(min=1,max=255,message = "Enter first name(length is not more than 255)")
    @NotBlank(message = "First name is mandatory")
    @Getter
    private String firstName;
    @Size(max=255,message = "Enter middle name(length is not more than 255)")
    @Getter
    private String middleName = null;
    @Size(max=255,message = "Enter your last name(length is not more than 255)")
    private String lastName;
    private Integer rollNo;
    @Size(min=1,max=255,message = "Enter Institute Mail ID")
    @NotBlank(message = "Institute Mail Id cannot be blank")
    private String instituteID;
    @Size(min=1,max=255,message = "Enter Personal Mail ID")
    @NotBlank(message = "Personal Mail Id cannot be blank")
    private String personalID;
    @NotBlank(message = "Sex should be selected")
    private String sex;
    @Size(min=10, max=10, message="Length of Contact Number must be between 1 and 20 characters")
    @NotBlank(message="Contact number cannot be blank")
    private String mobileNumber;
    @Size(min=1, max=50, message="Length of Street Address must be between 1 and 50 characters")
    @NotBlank(message = "Street address cannot be blank")
    private String street;
    @Size(min=1, max=50, message="Length of City must be between 1 and 50 characters")
    @NotBlank(message = "City cannot be blank")
    private String city;
    @Size(min=1, max=50, message="Length of State must be between 1 and 50 characters")
    @NotBlank(message = "State cannot be blank")
    private String state;
    @Size(min=6, max=6, message="Length of Postal Code must be between 1 and 10 characters")
    @NotBlank(message = "Postal code cannot be blank")
    private String postalCode;
    @NotNull(message = "Date cannot be empty")
    private java.sql.Date dateOfBirth;
    @Size(min=1,max=255,message = "Enter the Programme")
    @NotEmpty(message = "Programme cannot be empty")
    private String programme;
    @Size(min=1,max=255,message = "Enter the branch")
    @NotEmpty(message = "Branch cannot be empty")
    private String branch;
    @Min(value=0,message = "Total Backlogs cannot be negative")
    private int totalBacklogs;
    @Min(value=0,message = "Active Backlogs cannot be negative")
    private int activeBacklogs;
    @Min(value = 0,message = "CPI cannot be negative")
    @Max(value = 10,message = "Gaddari karbe")
    private float cpi;
    @Size(min = 1,max = 255,message = "Enter your class X board name")
    @NotBlank(message = "Board cannot be blank")
    private String classXBoard;
    @Min(value = 0,message = "Cannot be negative")
    @Max(value = 100,message = "Gaddari karbe")
    private float Xpercentage;
    @Size(min = 1,max = 255,message = "Enter your class XII board name")
    @NotBlank(message = "Board cannot be blank")
    private String classXIIBoard;
    @Min(value = 0,message = "Cannot be negative")
    @Max(value = 100,message = "Gaddari karbe")
    private float XIIpercentage;
    @NotNull(message = "Expected passing year should be entered")
    private String passingYear;


}
