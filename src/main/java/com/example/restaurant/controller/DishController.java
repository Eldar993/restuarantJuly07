package com.example.restaurant.controller;

import com.example.restaurant.dto.DishDetailDto;
import com.example.restaurant.dto.DishDto;
import com.example.restaurant.dto.DishTypeDto;
import com.example.restaurant.dto.IngredientDto;
import com.example.restaurant.entity.Dish;
import com.example.restaurant.service.DeleteService;
import com.example.restaurant.service.DishService;
import com.example.restaurant.service.DishTypeService;
import com.example.restaurant.service.IngredientService;
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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishTypeService dishTypeService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private DeleteService deleteService;

    @RequestMapping(value = "/dishes", method = RequestMethod.GET)
    public ModelAndView printAll(ModelAndView mav) {

        mav.setViewName("Dishes/dishes");
        List<DishDetailDto> dishList = dishService.toDetailDto(dishService.findAll());

        mav.addObject("dishList", dishList);

        return mav;

    }

    @RequestMapping(value = "/dish", method = RequestMethod.GET)
    public ModelAndView createForm(ModelAndView mav) {
        List<DishTypeDto> dishTypeList = DishTypeService.toDto(dishTypeService.findAll());
        Map<IngredientDto, Boolean> ingredients = ingredientService.findAll()
                .stream()
                .map(IngredientService::toDto)
                .collect(Collectors.toMap(Function.identity(), v -> Boolean.FALSE));

        mav.setViewName("Dishes/dishForm");
        final DishDetailDto dishDetail = new DishDetailDto();
        dishDetail.setIngredients(ingredients);
        mav.addObject("dish", dishDetail);

        mav.addObject("dishTypeList", dishTypeList);
        mav.addObject("actionType", "create");
        return mav;
    }

    @RequestMapping(value = "/dish", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("dish") @Valid DishDto dishDto, BindingResult result, ModelAndView mav) {

        if (result.hasErrors()) {
            mav.setViewName("/Dishes/dishForm");
            for (FieldError fieldError : result.getFieldErrors()) {
                mav.addObject(fieldError.getField() + "_hasError", true);
            }
            mav.addObject("actionType", "create");
            mav.addObject("dish", dishDto);

        } else {
            Dish entity = dishService.toEntity(dishDto);
            dishService.create(entity);

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/dishes");
            mav.setView(redirectView);
        }

        return mav;
    }

    @RequestMapping(value = "/dish/{id}", method = RequestMethod.GET)
    public ModelAndView updateForm(@PathVariable("id") Long id, ModelAndView mav) {

        mav.setViewName("Dishes/dishForm");
        List<DishTypeDto> dishTypeList = DishTypeService.toDto(dishTypeService.findAll());
        mav.addObject("dishTypeList", dishTypeList);
        DishDetailDto dish = dishService.toDetailDto(dishService.findById(id));
        mav.addObject("dish", dish);

        return mav;
    }

    @RequestMapping(value = "/dish/{id}", method = RequestMethod.PUT)
    public ModelAndView update(@PathVariable("id") Long id, @ModelAttribute("dish") @Valid DishDto dishDto, BindingResult result, ModelAndView mav) {

        if (result.hasErrors()) {
            mav.setViewName("Dishes/dishForm");
            for (FieldError fieldError : result.getFieldErrors()) {
                mav.addObject(fieldError.getField() + "_hasError", true);
            }
            mav.addObject("dish", dishDto);
        } else {
            Dish entity = dishService.toEntity(dishDto);
            dishService.update(entity);

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/dishes");
            mav.setView(redirectView);
        }
        return mav;

    }

    @RequestMapping(value = "/dish/{id}", method = RequestMethod.DELETE)
    public RedirectView delete(@PathVariable("id") Long id) {
        deleteService.deleteDishById(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/dishes");
        return redirectView;
    }

}
