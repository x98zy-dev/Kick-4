package org.x98zy.webtask.service;

import org.x98zy.webtask.dao.UserDao;
import org.x98zy.webtask.model.User;
import org.x98zy.webtask.model.UserRole;
import org.x98zy.webtask.exception.WebTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserService {
    private final UserDao userDao;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> authenticate(String username, String password) throws WebTaskException {
        logger.info("Authenticating user: {}", username);

        Optional<User> userOptional = userDao.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(password) && Boolean.TRUE.equals(user.getIsActive())) {
                logger.info("User {} authenticated successfully", username);
                return Optional.of(user);
            }
        }

        logger.warn("Authentication failed for user: {}", username);
        return Optional.empty();
    }

    public User registerUser(String username, String email, String password,
                             String phone, String city) throws WebTaskException {
        logger.info("Registering new user: {}", username);

        if (userDao.findByUsername(username).isPresent()) {
            throw new WebTaskException("Username already exists: " + username);
        }

        if (userDao.findByEmail(email).isPresent()) {
            throw new WebTaskException("Email already exists: " + email);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(UserRole.CLIENT);
        user.setPhone(phone);
        user.setCity(city);

        User savedUser = userDao.save(user);
        logger.info("User registered successfully: {}", savedUser.getUsername());

        return savedUser;
    }

    public Optional<User> getUserByUsername(String username) throws WebTaskException {
        return userDao.findByUsername(username);
    }

    public boolean isAdmin(User user) {
        return user != null && user.getRole() == UserRole.ADMIN;
    }
}