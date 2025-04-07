package com.offnine.food_app.service;

import org.springframework.web.multipart.MultipartFile;

public interface FoodService {

   String uploadFile(MultipartFile file);

}
