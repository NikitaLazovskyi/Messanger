package com.userservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.userservice.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${database.uri}")
    private String uri;
    @Value("${database.name}")
    private String dbName;

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }


    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), dbName);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.userservice");
    }
}
