package com.example.taco.pojo;

import com.example.taco.repository.IngredientRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Component
public class Taco {
    private long id;
    private Date createdAt;
    @NotNull
    @Size(min = 5,message = "name must be at least 5 characters long")
    private String name;
    @Size(min = 1,message = "you must choose at least 1 ingredient")
    private List<String> ingredients;

    public List<Ingredient> getIngredients() {
        List<Ingredient> all = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );
        List<Ingredient> objIngredients = new ArrayList<>();
        for(Ingredient ingredient : all){
            for(String strIngredient : ingredients){
                if(ingredient.getId().equals(strIngredient)){
                    objIngredients.add(ingredient);
                }
            }
        }
        return objIngredients;
    }
}
