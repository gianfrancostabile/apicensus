package com.accenture.java.apicensus.repository;

import com.accenture.java.apicensus.entity.UserCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends MongoRepository<UserCredentials, String> {
    UserCredentials findByUsername(String username);
}
