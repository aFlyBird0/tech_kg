package cn.tcualhp.tech_kg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class TechKgApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechKgApplication.class, args);
    }

}
