package com.flas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.flas.model.Alert;

public interface AlertRepository extends MongoRepository<Alert, String>{

}
