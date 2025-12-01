package org.x98zy.webtask.service;

import org.x98zy.webtask.dao.AdvertisementDao;
import org.x98zy.webtask.model.Advertisement;
import org.x98zy.webtask.model.AdvertisementStatus;
import org.x98zy.webtask.model.User;
import org.x98zy.webtask.exception.WebTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class AdvertisementService {
    private final AdvertisementDao advertisementDao;
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementService.class);

    public AdvertisementService(AdvertisementDao advertisementDao) {
        this.advertisementDao = advertisementDao;
    }

    public Advertisement createAdvertisement(String title, String description, BigDecimal price,
                                             String city, Long categoryId, User user, String imagePath)
            throws WebTaskException {

        logger.info("Creating advertisement: {} by user: {}", title, user.getUsername());

        if (title == null || title.trim().isEmpty()) {
            throw new WebTaskException("Title is required");
        }

        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new WebTaskException("Price cannot be negative");
        }

        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(title);
        advertisement.setDescription(description);
        advertisement.setPrice(price);
        advertisement.setCity(city);
        advertisement.setCategoryId(categoryId);
        advertisement.setUserId(user.getId());
        advertisement.setStatus(AdvertisementStatus.PENDING);
        advertisement.setImagePath(imagePath);

        return advertisementDao.save(advertisement);
    }

    public List<Advertisement> getActiveAdvertisements() throws WebTaskException {
        return advertisementDao.findAllActive();
    }

    public List<Advertisement> getUserAdvertisements(User user) throws WebTaskException {
        return advertisementDao.findByUserId(user.getId());
    }

    public Advertisement getAdvertisementById(Long id) throws WebTaskException {
        return advertisementDao.findById(id)
                .orElseThrow(() -> new WebTaskException("Advertisement not found with id: " + id));
    }

    public void updateAdvertisement(Advertisement advertisement, User user) throws WebTaskException {
        if (!advertisement.getUserId().equals(user.getId())) {
            throw new WebTaskException("You can only update your own advertisements");
        }

        advertisement.setStatus(AdvertisementStatus.PENDING);
        advertisementDao.update(advertisement);
    }

    public void deleteAdvertisement(Long id, User user) throws WebTaskException {
        advertisementDao.delete(id, user.getId());
    }

    public List<Advertisement> getPendingAdvertisements() throws WebTaskException {
        return advertisementDao.findPendingForModeration();
    }

    public void approveAdvertisement(Long id) throws WebTaskException {
        advertisementDao.updateStatus(id, AdvertisementStatus.ACTIVE);
    }

    public void rejectAdvertisement(Long id) throws WebTaskException {
        advertisementDao.updateStatus(id, AdvertisementStatus.REJECTED);
    }

    public boolean canUserEditAdvertisement(Advertisement advertisement, User user) {
        return advertisement.getUserId().equals(user.getId());
    }
}