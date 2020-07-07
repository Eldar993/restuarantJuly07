package com.gmail.guliyev.controller;

import com.gmail.guliyev.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BillController {
    @Autowired
    private BillService billService;

    @RequestMapping(value = "/bills", method = RequestMethod.GET)
    public ModelAndView printAll(ModelAndView mav){

        mav.setViewName("/Bills/bills");

        return mav;
    }
}
