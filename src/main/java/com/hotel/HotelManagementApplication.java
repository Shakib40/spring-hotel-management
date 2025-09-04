package com.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing   // ✅ Enable auditing for createDate & updatedDate
public class HotelManagementApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(HotelManagementApplication.class);

	public static void main(String[] args) {
		log.info("🚀 Starting Hotel Management Server...");
		SpringApplication.run(HotelManagementApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Runs after the context is initialized
		log.info("✅ Server started successfully!");
	}

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {
		log.info("🌍 Application context refreshed successfully!");
	}
}
