package org.x98zy.webtask.service.user;

import org.x98zy.webtask.model.User;
import org.x98zy.webtask.exception.WebTaskException;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String username, String password) throws WebTaskException;
    User registerUser(String username, String email, String password,
                      String phone, String city) throws WebTaskException;
    Optional<User> getUserByUsername(String username) throws WebTaskException;
    boolean isAdmin(User user);
}