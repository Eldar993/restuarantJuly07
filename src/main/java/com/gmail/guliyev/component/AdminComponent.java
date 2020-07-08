package com.example.restaurant.component;


import com.example.restaurant.entity.User;
import com.example.restaurant.enums.UserRoles;
import com.example.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
    public class AdminComponent  implements CommandLineRunner {
        @Autowired
        UserRepository userRepository;

        @Override
        public void run(String...args){
            User admin = new User();
            admin.setName("admin");
            admin.setPassword("1");
            admin.setUserRole(UserRoles.ADMIN);

            userRepository.saveAndFlush(admin);
        }
    }

