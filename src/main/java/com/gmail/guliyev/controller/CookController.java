package com.gmail.guliyev.controller;

import com.gmail.guliyev.dto.OrderDto;
import com.gmail.guliyev.enums.OrderStatus;
import com.gmail.guliyev.service.DishService;
import com.gmail.guliyev.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class CookController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    @RequestMapping(value = "/cook/orders", method = RequestMethod.GET)
    @Secured(value = {"ROLE_COOK"})
    public ModelAndView ordersToPrepare(ModelAndView mav) {
        //TODO: Show orders that should be prepared
        mav.setViewName("Orders/orders");
        List<OrderDto> orderList = OrderService.toDto(orderService.findByStatus(OrderStatus.IN_PROGRESS));

        mav.addObject("orderList", orderList);

        return mav;
    }


    @RequestMapping(value = "/cook/orders/{username}", method = RequestMethod.PUT)
    @Secured(value = {"ROLE_COOK"})
    public ModelAndView completeOrder(@PathVariable("username") String username,
                                      ModelAndView mav) {
        //TODO: Change order status to WAIT_PAYMENT
        orderService.cookCompleted(username);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/cook/orders");
        mav.setView(redirectView);


        return mav;
    }
}
