package com.gmail.guliyev.controller;

import com.gmail.guliyev.dto.DishTypeDto;
import com.gmail.guliyev.entity.DishType;
import com.gmail.guliyev.service.DeleteService;
import com.gmail.guliyev.service.DishTypeService;
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
public class DishTypeController {
    @Autowired
    private DishTypeService dishTypeService;

    @Autowired
    private DeleteService deleteService;

    @RequestMapping(value = "/dishTypes", method = RequestMethod.GET)
    public ModelAndView printAll(ModelAndView mav) {
        List<DishTypeDto> dishTypeList = DishTypeService.toDto(dishTypeService.findAll());
        mav.setViewName("DishTypes/dishTypes");
        mav.addObject("dishTypeList", dishTypeList);

        return mav;
    }


    @RequestMapping(value = "/dishType", method = RequestMethod.GET)
    public ModelAndView createForm(@ModelAttribute ModelAndView mav) {

        mav.setViewName("DishTypes/dishTypeForm");
        mav.addObject("dishType", new DishType());
        mav.addObject("actionType", "create");
        return mav;
    }

    @RequestMapping(value = "/dishType", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("dishType") @Valid DishTypeDto dishTypeDto, BindingResult result, ModelAndView mav) {

        if (result.hasErrors()) {
            mav.setViewName("DishTypes/dishTypeForm");
            for (FieldError fieldError : result.getFieldErrors()) {
                mav.addObject(fieldError.getField() + "_hasError", true);
            }
            mav.addObject("actionType", "create");
            mav.addObject("dishType", dishTypeDto);
        } else {
            DishType entity = DishTypeService.toEntity(dishTypeDto);
            dishTypeService.create(entity);

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/dishTypes");
            mav.setView(redirectView);

        }
        return mav;

    }

    @RequestMapping(value = "/dishType/{id}", method = RequestMethod.GET)
    public ModelAndView updateForm(@PathVariable("id") Long id, ModelAndView mav) {

        mav.setViewName("DishTypes/dishTypeForm");
        DishTypeDto dishType = DishTypeService.toDto(dishTypeService.findById(id));
        mav.addObject("dishType", dishType);

        return mav;
    }

    @RequestMapping(value = "/dishType/{id}", method = RequestMethod.PUT)
    public ModelAndView update(@PathVariable("id") Long id, @ModelAttribute("dishType") @Valid DishTypeDto dishTypeDto, BindingResult result, ModelAndView mav) {

        if (result.hasErrors()) {
            mav.setViewName("DishTypes/dishTypeForm");
            for (FieldError fieldError : result.getFieldErrors()) {
                mav.addObject(fieldError.getField() + "_hasError", true);
            }
            mav.addObject("dishType", dishTypeDto);
        } else {
            DishType entity = DishTypeService.toEntity(dishTypeDto);
            dishTypeService.update(entity);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/dishTypes");
            mav.setView(redirectView);
        }
        return mav;

    }

    @RequestMapping(value = "/dishType/{id}", method = RequestMethod.DELETE)
    public RedirectView delete(@PathVariable("id") Long id) {
        deleteService.deleteDishTypeById(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/dishTypes");
        return redirectView;
    }
}
