package com.vicarius.quota;

import com.vicarius.quota.repository.mysql.UserEntityMapper;
import com.vicarius.quota.repository.mysql.UserEntityMapperImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuotaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotaApplication.class, args);
	}



}
