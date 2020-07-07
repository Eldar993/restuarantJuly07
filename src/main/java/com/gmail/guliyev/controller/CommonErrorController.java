package com.gmail.guliyev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import java.util.Map;
import java.util.Optional;

@Controller
public class CommonErrorController implements ErrorController {
    private static final String ERROR_PATH = "error";

    @Value("${include-stack-trace:false}")
    private boolean includeStackTrace;

    private final ErrorAttributes errorAttributes;

    @Autowired
    public CommonErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @GetMapping(value = ERROR_PATH, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView error(WebRequest request,
                              ModelAndView modelAndView) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, includeStackTrace);

        modelAndView.addObject("errorAttributes", errorAttributes);
        String url = Optional.ofNullable(
                (String) (request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI, 0)))
                .orElse("");
        modelAndView.addObject("url", url);
        modelAndView.setViewName("errors/commonError");
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
        return errorAttributes.getErrorAttributes(request, includeStackTrace);
    }

    @RequestMapping(value = "/access-denied",
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied() {
        return "errors/accessDenied";
    }
}
