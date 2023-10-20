package com.dbms.project;

import com.dbms.project.dao.CompanyDao;
import com.dbms.project.dao.PostDao;
import com.dbms.project.dao.StudentDao;
import com.dbms.project.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootExampleApplication implements CommandLineRunner {

	@Autowired
	private UserDao userdao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private StudentDao studentDao;
	public static void main(String[] args) {
		SpringApplication.run(SpringBootExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.userdao.CreateTable();
		this.postDao.CreateTable();
		this.companyDao.CreateTable();
		this.studentDao.CreateTable();
	}
}
