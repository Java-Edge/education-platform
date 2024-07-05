package com.javagpt.back.config;

import com.javagpt.dict.entity.Account;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.UUID;

/**
 * @author JavaEdge
 * @date 2024/7/5
 */
public class InitDatabase {
    @Bean
    CommandLineRunner init(MongoOperations operations) {
        return args -> {
            operations.dropCollection(Account.class);

            operations.insert(new Account("A_" + UUID.randomUUID().toString(), "account1", "jianxiang1"));
            operations.insert(new Account("A_" + UUID.randomUUID().toString(), "account2", "jianxiang"));
            operations.findAll(Account.class).forEach(
                    account -> {
                        System.out.println(account.getId()
                        );
                    }
            );
        };
    }
}
