package com.gmail.guliyev.service;

import com.gmail.guliyev.dto.UserDto;
import com.gmail.guliyev.entity.User;
import com.gmail.guliyev.enums.UserRoles;
import com.gmail.guliyev.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    public static final int NAME_LENGTH = 5;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * <p>The method for creating user with specified role.
     * </p>
     * @param user     The user that we want create
     * @param userRole user's role
     * @return <code>true</code> if the user was created
     * *                  <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean create(User user, UserRoles userRole) {

        Optional<User> oldUser = userRepository.findByName(user.getName());
        if (oldUser.isPresent()) {
            return false;
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setUserRole(userRole);
        user.setPassword(encodedPassword);
        userRepository.saveAndFlush(user);
        return true;
    }
    /**
     * <p>The method for creating user with role USER.
     * </p>
     * @param user     The user that we want create
     * @return <code>true</code> if the user was created
     * *                  <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean create(User user) {
        return create(user, UserRoles.USER);

    }

    /**
     * <p>The method for finding user by id
     * </p>
     * @param id     The user's id that we want to find
     * @return <code>true</code> if the user was created
     * *                  <code>false</code> otherwise.
     */
    public Optional<User> findUser(Long id) {
        Optional<User> userInfo = userRepository.findUserById(id);
        return userInfo;
    }

    private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generateRandomString(int count) {
        StringBuilder builder = new StringBuilder();

        while (count > 0) {
            int character = (int) (Math.random() * ALPHA_STRING.length());
            builder.append(ALPHA_STRING.charAt(character));
            count--;
        }
        return builder.toString();
    }

    public boolean update(User user, boolean shouldUpdatePassword) {
        Optional<User> oldInfo = userRepository.findById(user.getId());

        if (oldInfo.isEmpty()) {
            return false;
        }

        User updatedUser = oldInfo.get();
        updatedUser.setName(user.getName());
        if (shouldUpdatePassword) {
            updatedUser.setPassword(user.getPassword());
        }
        updatedUser.setUserRole(user.getUserRole());

        userRepository.saveAndFlush(updatedUser);
        return true;
    }


    public List<User> printUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setName(user.getName());
        result.setPassword(user.getPassword());
        result.setUserRole(user.getUserRole());

        return result;
    }

    public static List<UserDto> toDto(List<User> users) {
        return users
                .stream()
                .map(UserService::toDto)
                .collect(Collectors.toList());
    }

    public static User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User result = new User();
        result.setId(dto.getId());
        result.setName(dto.getName());
        result.setPassword(dto.getPassword());
        result.setUserRole(dto.getUserRole());
        return result;
    }

    public Optional<User> findByName(String username) {
        return userRepository.findByName(username);
    }

    public User findByUserRole(UserRoles userRole) {
        return userRepository.findByUserRole(userRole);
    }

    public boolean existsByUserRole(UserRoles userRole) {
        return userRepository.existsByUserRole(userRole);
    }
}
