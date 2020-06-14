package com.example.taco.repository.Impl;

import com.example.taco.pojo.Ingredient;
import com.example.taco.pojo.Taco;
import com.example.taco.repository.IngredientRepository;
import com.example.taco.repository.TacoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.scripts.JD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Repository
public class TacoRepoImpl implements TacoRepository {

    private SimpleJdbcInsert simpleJdbcInsert;
    private ObjectMapper objectMapper;
    @Autowired
    private IngredientRepository ingredientRepository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TacoRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco")
                .usingGeneratedKeyColumns("id");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for(Ingredient ingredient : taco.getIngredients()){
            saveIngredientToTaco(ingredient,tacoId);
        }
        return taco;
    }
    private long saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
        @SuppressWarnings("unchecked")
        Map<String,Object> values = objectMapper.convertValue(taco,Map.class);
        values.put("created_at",taco.getCreatedAt());
        values.put("name",taco.getName());
        return simpleJdbcInsert.executeAndReturnKey(values).longValue();
    }
    private void saveIngredientToTaco(Ingredient ingredient,long tacoId){
        jdbcTemplate.update(
                "insert into Taco_Ingredients (taco,ingredient)"+
                        "values(?,?)",
                tacoId,ingredient.getId()
        );
    }
}
