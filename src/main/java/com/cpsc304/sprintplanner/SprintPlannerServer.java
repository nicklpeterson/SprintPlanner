package com.cpsc304.sprintplanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EntityScan(basePackages = "com.cpsc304.sprintplanner.persistence.entities")
public class SprintPlannerServer {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(SprintPlannerServer.class);
        log.info("Starting Sprint Planner Server");
        new SpringApplication(SprintPlannerServer.class).run(args);
    }
}
