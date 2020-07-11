package com.gmail.guliyev.configuraion;


import com.gmail.guliyev.entity.User;
import com.gmail.guliyev.enums.UserRoles;
import com.gmail.guliyev.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;


@Component
public class AdminValidationCommandLineRunner implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(AdminValidationCommandLineRunner.class);
    private final UserService userService;

    public AdminValidationCommandLineRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        LOG.trace("Running AdminValidation component...");
        if (userService.existsByUserRole(UserRoles.ADMIN)) {
            LOG.debug("Ok! Admin(-s) is present.");
            return;
        }
        User admin = new User();
        admin.setName(userService.generateRandomString(5));
        final String rawPassword = userService.generateRandomString(5);
        admin.setPassword(rawPassword);
        boolean result = userService.create(admin, UserRoles.ADMIN);
        if (result) {
            LOG.info("Generated admin's credentials: \n" +
                            "   login:    {}\n" +
                            "   password: {}",
                    admin.getName(), rawPassword);
        } else {
            throw new ApplicationContextException("Admin not found!");
        }
    }
}


