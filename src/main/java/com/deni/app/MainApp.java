package com.deni.app;

import com.deni.app.common.audit.AuditAwareImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// latest version :  1.11.2
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware") // config for jpa audit entity in package audit
//@EnableScheduling // config for scheduler task in package scheduler
public class MainApp {


    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
        System.out.println("Application Running");
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditAwareImpl();
    }




}
