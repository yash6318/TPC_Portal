package com.dbms.project;

import com.dbms.project.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootExampleApplication implements CommandLineRunner {

	@Autowired
	private UserDao userdao;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.userdao.CreateTable();
	}
}
