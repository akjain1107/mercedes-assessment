package com.assessment.mercedesservice;

import com.assessment.mercedesservice.grpc.UserWriteServiceGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class MercedesServiceApplication {

	public static void main(String[] args) throws InterruptedException,IOException{
		SpringApplication.run(MercedesServiceApplication.class, args);
			}
}
