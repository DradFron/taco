package com.example.taco.repository;

import com.example.taco.pojo.Ingredient;

import java.util.List;

public interface IngredientRepository {
    List<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
