package com.offnine.food_app.Repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.offnine.food_app.entity.FoodEntity;

public interface FoodRepo extends MongoRepository <FoodEntity,String>{
    
}
