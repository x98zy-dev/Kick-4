package org.x98zy.webtask.controller;

import org.x98zy.webtask.service.UserService;
import org.x98zy.webtask.dao.UserDao;
import org.x98zy.webtask.model.User;
import org.x98zy.webtask.exception.WebTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    public LoginServlet() {
        this.userService = new UserService(new UserDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Optional<User> userOptional = userService.authenticate(username, password);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                logger.info("User {} logged in successfully", username);
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        } catch (WebTaskException e) {
            logger.error("Login error for user: {}", username, e);
            request.setAttribute("error", "System error. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}