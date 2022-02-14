package com.example.time;

import com.example.time.dbseeder.DbSeeder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimeApplication {
    private final DbSeeder dbSeeder;

    public TimeApplication(DbSeeder dbSeeder) {
        this.dbSeeder = dbSeeder;
    }

    public static void main(String[] args) {
        SpringApplication.run(TimeApplication.class, args);
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        dbSeeder.seedUserRoles();
        dbSeeder.seedUserWithUserRole();
        dbSeeder.seedUserWithAdminRole();
        dbSeeder.seedUserWithAdminAndUserRoles();
    }

}
