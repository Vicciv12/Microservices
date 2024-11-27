package com.manager.roommanagementservice; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RoommanagementserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoommanagementserviceApplication.class, args);
	}

}
