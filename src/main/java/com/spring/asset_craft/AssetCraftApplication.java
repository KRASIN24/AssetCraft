package com.spring.asset_craft;

import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.entity.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AssetCraftApplication {

	//TODO: Add Loading Types to Hibernate mappings



	public static void main(String[] args) {
		SpringApplication.run(AssetCraftApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
//		return runner -> {
//			System.out.println("Hello World!");
//		};
//	}

}
