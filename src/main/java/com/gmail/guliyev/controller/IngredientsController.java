package com.gmail.guliyev.controller;

import com.gmail.guliyev.dto.IngredientDto;
import com.gmail.guliyev.entity.Ingredient;
import com.gmail.guliyev.service.DeleteService;
import com.gmail.guliyev.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class IngredientsController {
    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private DeleteService deleteService;

    @RequestMapping(value = "/ingredients", method = RequestMethod.GET)
    public ModelAndView printAll(ModelAndView mav) {
        List<IngredientDto> ingredient = IngredientService.toDto(ingredientService.findAll());
        mav.setViewName("Ingredients/ingredients");
        mav.addObject("allIngredients", ingredient);

        return mav;
    }

    @RequestMapping(value = "/ingredient", method = RequestMethod.GET)
    public ModelAndView createForm(@ModelAttribute ModelAndView mav) {

        mav.setViewName("Ingredients/ingredientForm");
        mav.addObject("ingredient", new Ingredient());
        mav.addObject("actionType", "create");
        return mav;
    }

    @RequestMapping(value = "/ingredient", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("ingredient") @Valid IngredientDto ingredientDto, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()) {
            mav.setViewName("Ingredients/ingredientForm");
            for (FieldError fieldError : result.getFieldErrors()) {
                mav.addObject(fieldError.getField() + "_hasError", true);
            }
            mav.addObject("actionType", "create");
            mav.addObject("ingredient", ingredientDto);
        } else {
            Ingredient entity = IngredientService.toEntity(ingredientDto);
            ingredientService.create(entity);

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/ingredients");
            mav.setView(redirectView);

        }
        return mav;


    }

    @RequestMapping(value = "/ingredient/{id}", method = RequestMethod.GET)
    public ModelAndView updateForm(@PathVariable("id") Long id, ModelAndView mav) {

        mav.setViewName("Ingredients/ingredientForm");
        Ingredient ingredient = ingredientService.findById(id);
        IngredientService.toDto(ingredient);
        mav.addObject("ingredient", ingredient);

        return mav;
    }

    @RequestMapping(value = "/ingredient/{id}", method = RequestMethod.PUT)
    public ModelAndView update(@PathVariable("id") Long id, @ModelAttribute("ingredient") @Valid IngredientDto ingredientDto, BindingResult result, ModelAndView mav) {

        if (result.hasErrors()) {
            mav.setViewName("Ingredients/ingredientForm");
            for (FieldError fieldError : result.getFieldErrors()) {
                mav.addObject(fieldError.getField() + "_hasError", true);
            }
            mav.addObject("ingredient", ingredientDto);
        } else {
            Ingredient entity = IngredientService.toEntity(ingredientDto);
            Ingredient updatedIngredient = ingredientService.update(entity);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/ingredients");
            mav.setView(redirectView);
        }
        return mav;

    }

    @RequestMapping(value = "/ingredient/{id}", method = RequestMethod.DELETE)
    public RedirectView delete(@PathVariable("id") Long id) {
        deleteService.deleteIngredientById(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/ingredients");
        return redirectView;
    }
}
