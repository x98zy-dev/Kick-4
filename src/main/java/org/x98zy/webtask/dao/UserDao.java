package org.x98zy.webtask.dao;

import org.x98zy.webtask.model.User;
import org.x98zy.webtask.model.UserRole;
import org.x98zy.webtask.connection.ConnectionPool;
import org.x98zy.webtask.exception.WebTaskException;

import java.sql.*;
import java.util.Optional;

public class UserDao {

    public Optional<User> findByUsername(String username) throws WebTaskException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(extractUser(resultSet));
            }
        } catch (SQLException e) {
            throw new WebTaskException("Error finding user by username", e);
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) throws WebTaskException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(extractUser(resultSet));
            }
        } catch (SQLException e) {
            throw new WebTaskException("Error finding user by email", e);
        }
        return Optional.empty();
    }

    public User save(User user) throws WebTaskException {
        String sql = "INSERT INTO users (username, email, password, role, phone, city) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().name());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getCity());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new WebTaskException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new WebTaskException("Creating user failed, no ID obtained.");
                }
            }

            return user;
        } catch (SQLException e) {
            throw new WebTaskException("Error saving user", e);
        }
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(UserRole.valueOf(resultSet.getString("role")));
        user.setPhone(resultSet.getString("phone"));
        user.setCity(resultSet.getString("city"));
        user.setRating(resultSet.getBigDecimal("rating"));
        user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        user.setIsActive(resultSet.getBoolean("is_active"));
        return user;
    }
}