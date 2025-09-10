package com.system.estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories(basePackages = {
        "com.system.estoque.repositories"
})
@EntityScan(basePackages = {
        "com.system.estoque.entities"
})
@EnableCaching
@SpringBootApplication
@EnableScheduling
public class InventoryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApiApplication.class, args);
    }

}
