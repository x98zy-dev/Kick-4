package org.x98zy.webtask.dao;

import org.x98zy.webtask.model.Advertisement;
import org.x98zy.webtask.model.AdvertisementStatus;
import org.x98zy.webtask.connection.ConnectionPool;
import org.x98zy.webtask.exception.WebTaskException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdvertisementDao {

    public Advertisement save(Advertisement advertisement) throws WebTaskException {
        String sql = "INSERT INTO advertisements (title, description, price, city, category_id, user_id, status, image_path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, advertisement.getTitle());
            statement.setString(2, advertisement.getDescription());
            statement.setBigDecimal(3, advertisement.getPrice());
            statement.setString(4, advertisement.getCity());
            statement.setLong(5, advertisement.getCategoryId());
            statement.setLong(6, advertisement.getUserId());
            statement.setString(7, advertisement.getStatus().name());
            statement.setString(8, advertisement.getImagePath());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new WebTaskException("Creating advertisement failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    advertisement.setId(generatedKeys.getLong(1));
                }
            }

            return advertisement;
        } catch (SQLException e) {
            throw new WebTaskException("Error saving advertisement", e);
        }
    }

    public List<Advertisement> findAllActive() throws WebTaskException {
        String sql = "SELECT * FROM advertisements WHERE status = 'ACTIVE' ORDER BY created_at DESC";
        return executeQuery(sql);
    }

    public List<Advertisement> findByUserId(Long userId) throws WebTaskException {
        String sql = "SELECT * FROM advertisements WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return extractAdvertisements(resultSet);

        } catch (SQLException e) {
            throw new WebTaskException("Error finding advertisements by user id", e);
        }
    }

    public Optional<Advertisement> findById(Long id) throws WebTaskException {
        String sql = "SELECT * FROM advertisements WHERE id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(extractAdvertisement(resultSet));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new WebTaskException("Error finding advertisement by id", e);
        }
    }

    public void update(Advertisement advertisement) throws WebTaskException {
        String sql = "UPDATE advertisements SET title = ?, description = ?, price = ?, city = ?, " +
                "category_id = ?, status = ?, image_path = ?, updated_at = CURRENT_TIMESTAMP " +
                "WHERE id = ? AND user_id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, advertisement.getTitle());
            statement.setString(2, advertisement.getDescription());
            statement.setBigDecimal(3, advertisement.getPrice());
            statement.setString(4, advertisement.getCity());
            statement.setLong(5, advertisement.getCategoryId());
            statement.setString(6, advertisement.getStatus().name());
            statement.setString(7, advertisement.getImagePath());
            statement.setLong(8, advertisement.getId());
            statement.setLong(9, advertisement.getUserId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new WebTaskException("Error updating advertisement", e);
        }
    }

    public void delete(Long id, Long userId) throws WebTaskException {
        String sql = "DELETE FROM advertisements WHERE id = ? AND user_id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.setLong(2, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new WebTaskException("Error deleting advertisement", e);
        }
    }

    public List<Advertisement> findPendingForModeration() throws WebTaskException {
        String sql = "SELECT * FROM advertisements WHERE status = 'PENDING' ORDER BY created_at DESC";
        return executeQuery(sql);
    }

    public void updateStatus(Long id, AdvertisementStatus status) throws WebTaskException {
        String sql = "UPDATE advertisements SET status = ? WHERE id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status.name());
            statement.setLong(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new WebTaskException("Error updating advertisement status", e);
        }
    }

    private List<Advertisement> executeQuery(String sql) throws WebTaskException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            return extractAdvertisements(resultSet);

        } catch (SQLException e) {
            throw new WebTaskException("Error executing query", e);
        }
    }

    private List<Advertisement> extractAdvertisements(ResultSet resultSet) throws SQLException {
        List<Advertisement> advertisements = new ArrayList<>();
        while (resultSet.next()) {
            advertisements.add(extractAdvertisement(resultSet));
        }
        return advertisements;
    }

    private Advertisement extractAdvertisement(ResultSet resultSet) throws SQLException {
        Advertisement ad = new Advertisement();
        ad.setId(resultSet.getLong("id"));
        ad.setTitle(resultSet.getString("title"));
        ad.setDescription(resultSet.getString("description"));
        ad.setPrice(resultSet.getBigDecimal("price"));
        ad.setCity(resultSet.getString("city"));
        ad.setCategoryId(resultSet.getLong("category_id"));
        ad.setUserId(resultSet.getLong("user_id"));
        ad.setStatus(AdvertisementStatus.valueOf(resultSet.getString("status")));
        ad.setImagePath(resultSet.getString("image_path"));
        ad.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        ad.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        return ad;
    }
}