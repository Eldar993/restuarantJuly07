package com.gmail.guliyev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController {
    @RequestMapping(value = "/access-denied")
    @ResponseBody
    public String accessDenied() {
        return "Access denied";
    }
}
