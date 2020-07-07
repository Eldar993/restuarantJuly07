package com.gmail.guliyev.controller;

import com.gmail.guliyev.dto.DishDto;
import com.gmail.guliyev.dto.OrderDto;
import com.gmail.guliyev.entity.Order;
import com.gmail.guliyev.enums.OrderStatus;
import com.gmail.guliyev.service.DishService;
import com.gmail.guliyev.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    @RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
    public ModelAndView printAll(ModelAndView mav) {
        mav.setViewName("Orders/orders");
        List<DishDto> dishList = DishService.toDto(dishService.findAll());
        List<OrderDto> orderList = OrderService.toDto(orderService.findAll());
        mav.addObject("orderList", orderList);
        mav.addObject("dishList", dishList);


        return mav;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ModelAndView createOrderOrAddDish(ModelAndView mav,
                                             Authentication authentication,
                                             @ModelAttribute("dish-id") Long dishId,
                                             @ModelAttribute("count") Long count) {
        //create new order or add dish to opened order
        final String username = authentication.getName();

        try {
            orderService.addDish(username, dishId, count);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/dishes"); //redirect to dish page
            mav.setView(redirectView);
        } catch (Exception e) {
            log.error("Order not created: {}", e.getMessage(), e);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/error"); //todo: should be redirected to error page
            mav.setView(redirectView);
        }

        return mav;
    }

    @RequestMapping(value = "/admin/orders/{id}", method = RequestMethod.DELETE)
    @Secured(value = {"ROLE_ADMIN"})
    public ModelAndView delete(ModelAndView mav,
                               @PathVariable("id") Long orderId) {
        // Delete order
        orderService.remove(orderId);
        //TODO: add redirect(?)
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/admin/orders"); //redirect to dish page
        mav.setView(redirectView);
        return mav;
    }

    @RequestMapping(value = "/user/orders/{order-id}/dish/{dish-id}", method = RequestMethod.DELETE)
    @Secured(value = {"ROLE_USER"})
    public ModelAndView removeDish(ModelAndView mav,
                                   @PathVariable("dish-id") Long dishId,
                                   @PathVariable("order-id") Long orderId) {
        orderService.removeDish(orderId, dishId);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/basket");
        mav.setView(redirectView);
        return mav;
    }

    @RequestMapping(value = "/user/orders", method = RequestMethod.GET)
    public ModelAndView userOrdersHistory(Authentication authentication,
                                          ModelAndView mav) {
        //Show order statistics for current user
        //      orders history
        final String username = authentication.getName();
        final List<OrderDto> orders = orderService.findAllByUser(username)
                .stream()
                .map(OrderService::toDto)
                .collect(Collectors.toList());
        //TODO: pass orders to view
        mav.setViewName("Orders/orders");
        mav.addObject("orderList", orders);
        return mav;
    }

    @RequestMapping(value = "/user/orders/checkout", method = RequestMethod.POST)
    @Secured(value = {"ROLE_USER"})
    public ModelAndView confirmOrder(Authentication authentication,
                                     ModelAndView mav) {
        final String username = authentication.getName();
        orderService.confirmOrder(username);

        mav.setViewName("message");
        mav.addObject("title", "Checkout");
        mav.addObject("message", "Checkout success");
        return mav;
    }

    @RequestMapping(value = "/user/orders/pay", method = RequestMethod.GET)
    @Secured(value = {"ROLE_USER"})
    public ModelAndView payOrderPage(Authentication authentication, ModelAndView mav) {
        final String username = authentication.getName();
        providePaymentDetails(mav, username);
        mav.setViewName("Orders/payment");
        return mav;
    }

    @RequestMapping(value = "/user/orders/pay", method = RequestMethod.POST)
    @Secured(value = {"ROLE_USER"})
    public ModelAndView payOrder(Authentication authentication,
                                 @ModelAttribute("payment") long payment,
                                 ModelAndView mav) {
        final String username = authentication.getName();
        orderService
                .complete(username, payment)
                .ifPresentOrElse(
                        e -> {
                            providePaymentDetails(mav, username);
                            mav.addObject("errorMsg", e);
                            mav.setViewName("Orders/payment");
                        },
                        () -> {
                            mav.setViewName("message");
                            mav.addObject("title", "Payment");
                            mav.addObject("message", "Payment done. Thank You!");
                        }
                );

        return mav;
    }

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public ModelAndView basketPage(Authentication authentication,
                                   ModelAndView mav) {
        final String username = authentication.getName();
        OrderDto orderDto;
        Long totalPrice = 0L;
        try {
            Order order = orderService.findByStatus(username, OrderStatus.NEW);
            orderDto = OrderService.toDto(order);
        } catch (Exception e) {
            orderDto = new OrderDto();
        }

        mav.setViewName("basket");
        mav.addObject("order", orderDto);
        return mav;
    }

    private void providePaymentDetails(ModelAndView mav, String username) {
        final Order unpaidOrder = orderService.findUnpaid(username);
        if (unpaidOrder == null) {
            mav.addObject("errorMsg", "You don't have an unpaid order");
        } else {
            long totalPrice = OrderService.calculateTotalPrice(unpaidOrder);
            mav.addObject("totalPrice", totalPrice);
        }
    }
}
