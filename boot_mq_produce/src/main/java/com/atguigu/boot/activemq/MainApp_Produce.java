package com.atguigu.boot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MainApp_Produce {

	public static void main(String[] args) {
		SpringApplication.run(MainApp_Produce.class, args);
	}

}
