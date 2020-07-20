package com.gmail.guliyev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
    @RequestMapping(value = "/access-denied")
    @ResponseBody
    public String accessDenied() {
        return "Access denied";
    }

    @RequestMapping(value = "/order-error")
    @ResponseBody
    public ModelAndView orderError() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("message");
        mav.addObject("orderError",true);
        mav.addObject("message","You already have unpaid orders!");
        mav.addObject("solutionText","Please pay all your previous orders");
        return mav;
    }

}

