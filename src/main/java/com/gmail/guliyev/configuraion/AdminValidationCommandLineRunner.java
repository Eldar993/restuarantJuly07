package com.gmail.guliyev.configuraion;


import com.gmail.guliyev.entity.User;
import com.gmail.guliyev.enums.UserRoles;
import com.gmail.guliyev.service.UserService;
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
        admin.setName(userService.generateRandomString(5));
        admin.setPassword(userService.generateRandomString(5));
        boolean result = userService.create(admin, UserRoles.ADMIN);
        if(result){
            System.out.println(admin.getName());
            System.out.println(admin.getPassword());
        }else{
            throw new ApplicationContextException("Admin not found!");
        }


    }
}


