package com.javagpt.dict.repository;

import com.javagpt.dict.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author JavaEdge
 * @date 2024/7/5
 */
@Repository
public interface AccountReactiveMongoRepository
        extends ReactiveMongoRepository<Account, String>,
        ReactiveQueryByExampleExecutor<Account> {
}
