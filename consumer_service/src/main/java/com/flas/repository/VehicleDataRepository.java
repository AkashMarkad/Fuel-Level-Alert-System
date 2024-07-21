package com.flas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.flas.model.VehicleData;

public interface VehicleDataRepository extends MongoRepository<VehicleData, String>{

}
