package cn.tcualhp.tech_kg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * @author lihepeng
 * @description neo4j配置类
 * @date 2019-11-01 00:32
 **/
@Configuration
@EnableNeo4jRepositories(basePackages = "cn.tcualhp.tech_kg.neo4jRepo")
public class Neo4jConfig {
}
