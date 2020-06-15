package com.example.taco.controller;

import com.example.taco.pojo.Ingredient;
import com.example.taco.pojo.Ingredient.Type;
import com.example.taco.pojo.Order;
import com.example.taco.pojo.Taco;
import com.example.taco.repository.IngredientRepository;
import com.example.taco.repository.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute
    public void showDesignForm(Model model, @PathParam("id") String id){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        Type[] types = Ingredient.Type.values();
        for (Type type : types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients,type));
        }
        model.addAttribute("order",new Order());
        model.addAttribute("design",new Taco());
    }
    @GetMapping
    public String showDesignForm(Model model){
        model.addAttribute("design",new Taco());
        return "design";
    }
    @PostMapping
    public String processDesign(@Valid Taco design,
                                Errors errors, Model model,
                                @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            System.out.println(errors);
            return "design";
        }
        log.info("Processing design: " + design);
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients,Type type){
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
