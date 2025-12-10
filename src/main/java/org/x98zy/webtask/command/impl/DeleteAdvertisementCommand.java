package org.x98zy.webtask.command.impl;

import org.x98zy.webtask.command.Command;
import org.x98zy.webtask.service.AdvertisementService;
import org.x98zy.webtask.exception.WebTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteAdvertisementCommand implements Command {
    private final AdvertisementService advertisementService;
    private final Long advertisementId;
    private static final Logger logger = LoggerFactory.getLogger(DeleteAdvertisementCommand.class);

    public DeleteAdvertisementCommand(AdvertisementService advertisementService, Long advertisementId) {
        this.advertisementService = advertisementService;
        this.advertisementId = advertisementId;
    }

    @Override
    public void execute() throws WebTaskException {
        logger.info("Deleting advertisement ID: {}", advertisementId);
        advertisementService.deleteAdvertisement(advertisementId, null);
    }
}