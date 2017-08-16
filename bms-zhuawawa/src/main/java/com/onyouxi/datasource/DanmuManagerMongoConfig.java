package com.onyouxi.datasource;

import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by liuwei on 16/3/31.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.onyouxi.repository.manager", mongoTemplateRef = "adminUserMongoTemplate")
public class DanmuManagerMongoConfig {

    @Bean(name = "adminUserMongoProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.1")
    public MongoProperties adminUserMongoProperties() {
        return new MongoProperties();
    }



    @Bean(name = "adminUserMongoTemplate")
    public MongoTemplate adminUserMongoTemplate() throws Exception {
        MongoProperties mongoProperties = adminUserMongoProperties();
        MongoClient mongoClient = mongoProperties.createMongoClient(null);
        return new MongoTemplate(mongoClient, mongoProperties.getDatabase());
    }
}
