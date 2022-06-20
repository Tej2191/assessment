package com.be.assessment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.be.assessment.model.Stock;

public interface StockRepository extends MongoRepository<Stock, String> {

}
