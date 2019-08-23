package com.ale.shirojwtdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ale.shirojwtdemo.dao")
public class ShiroJwtDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiroJwtDemoApplication.class, args);
	}

}
