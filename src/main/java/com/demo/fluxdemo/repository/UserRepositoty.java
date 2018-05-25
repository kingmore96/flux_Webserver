package com.demo.fluxdemo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.demo.fluxdemo.pojo.User;

@Repository
public interface UserRepositoty extends ReactiveMongoRepository<User,String> {

}
