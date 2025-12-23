package org.x98zy.webtask.service.advertisement;

import org.x98zy.webtask.model.Advertisement;
import org.x98zy.webtask.model.User;
import org.x98zy.webtask.exception.WebTaskException;
import java.math.BigDecimal;
import java.util.List;

public interface AdvertisementService {
    Advertisement createAdvertisement(String title, String description, BigDecimal price,
                                      String city, Long categoryId, User user, String imagePath)
            throws WebTaskException;
    List<Advertisement> getActiveAdvertisements() throws WebTaskException;
    List<Advertisement> getUserAdvertisements(User user) throws WebTaskException;
    Advertisement getAdvertisementById(Long id) throws WebTaskException;
    void updateAdvertisement(Advertisement advertisement, User user) throws WebTaskException;
    void deleteAdvertisement(Long id, User user) throws WebTaskException;
    List<Advertisement> getPendingAdvertisements() throws WebTaskException;
    void approveAdvertisement(Long id) throws WebTaskException;
    void rejectAdvertisement(Long id) throws WebTaskException;
    boolean canUserEditAdvertisement(Advertisement advertisement, User user);
}