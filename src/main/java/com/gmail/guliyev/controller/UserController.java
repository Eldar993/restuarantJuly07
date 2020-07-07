package com.gmail.guliyev.controller;

import com.gmail.guliyev.dto.UserDto;
import com.gmail.guliyev.entity.User;
import com.gmail.guliyev.enums.UserRoles;
import com.gmail.guliyev.service.DeleteService;
import com.gmail.guliyev.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;
    private final DeleteService deleteService;

    public UserController(UserService userService, DeleteService deleteService) {
        this.userService = userService;
        this.deleteService = deleteService;
    }


    @RequestMapping(value = "/print_all", method = RequestMethod.GET)
    public ModelAndView printAll(ModelAndView mav, @ModelAttribute("user") UserDto userDto) {
        List<UserDto> printUsers = UserService.toDto(userService.printUsers());

        mav.setViewName("print_all");
        mav.addObject("usersList", printUsers);

        mav.addObject("userInfo", userDto);
        return mav;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registrationForm(ModelAndView mav) {

        mav.setViewName("registration");
        mav.addObject("userInfo", new UserDto());

        return mav;
    }

    @RequestMapping(value = "/qwe")
    @ResponseBody
    public String test() {
        return "Hello world)";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView register(@Validated @ModelAttribute("userInfo") UserDto userDto,
                                 BindingResult bindingResult, ModelAndView mav) {


        if (bindingResult.hasErrors()) {
            mav.setViewName("registration");
            mav.addObject("userInfo", userDto);
        } else {
            try {
                User entity = UserService.toEntity(userDto);
                final boolean result = userService.create(entity);
                if (result) {
                    RedirectView redirectView = new RedirectView("/");
                    mav.setView(redirectView);
                } else {
                    mav.setViewName("registration");
                    mav.addObject("userInfo", userDto);
                    mav.addObject("generalError", "Something way wrong:(");
                }
            } catch (Exception e) {
                mav.setViewName("registration");
                mav.addObject("userInfo", userDto);
                System.out.println(e.getMessage());
                mav.addObject("generalError", "User constraint violation");
            }
        }


        return mav;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView change(@PathVariable("id") Long id, ModelAndView mav) {
        ///// find user by id and return user's data
        Optional<User> user = userService.findUser(id);

        mav.setViewName("user_profile");
        UserRoles roles[] = UserRoles.values();
        //Optional<AnyType>: "Optional[" + anyType.toString() + "]"
        mav.addObject("userInfo", user.orElse(null));
        mav.addObject("roles", roles);
        //mav.addObject("isEven",userService.isEven(id));


        return mav;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public ModelAndView change(@PathVariable("id") Long id, @ModelAttribute("user") UserDto userDto, ModelAndView mav) {
        User entity = UserService.toEntity(userDto);

        if (id.equals(userDto.getId())) {
            userService.update(entity, false);
        }
        mav.setViewName("user_profile");

        UserRoles[] roles = UserRoles.values();
        mav.addObject("roles", roles);
        mav.addObject("userInfo", userDto);
        return mav;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public RedirectView delete(@PathVariable("id") Long id) {
        deleteService.deleteUserById(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/print_all");
        return redirectView;
    }

}
