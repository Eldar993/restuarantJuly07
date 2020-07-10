package com.example.restaurant.component;


import com.example.restaurant.entity.User;
import com.example.restaurant.enums.UserRoles;
import com.example.restaurant.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;


@Component
public class AdminValidationCommandLineRunner implements CommandLineRunner {
    private final UserService userService;

    public AdminValidationCommandLineRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if (userService.existsByUserRole(UserRoles.ADMIN)) {
            return;
        }
        User admin = new User();
        admin.setName("a");
        admin.setPassword("7");
        boolean result = userService.create(admin, UserRoles.ADMIN);
        if(result){
            System.out.println(admin.getName());
            System.out.println(admin.getPassword());
        }else{
            throw new ApplicationContextException("Admin not found!");
        }


    }
}


