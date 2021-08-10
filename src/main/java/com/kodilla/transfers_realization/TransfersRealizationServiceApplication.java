package com.kodilla.transfers_realization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableDiscoveryClient
@SpringBootApplication
public class TransfersRealizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransfersRealizationServiceApplication.class, args);
	}

}
