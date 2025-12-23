package org.x98zy.webtask.command.impl;

import org.x98zy.webtask.command.Command;
import org.x98zy.webtask.service.advertisement.AdvertisementServiceImpl;
import org.x98zy.webtask.exception.WebTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RejectAdvertisementCommand implements Command {
    private final AdvertisementServiceImpl advertisementService;
    private final Long advertisementId;
    private static final Logger logger = LoggerFactory.getLogger(RejectAdvertisementCommand.class);

    public RejectAdvertisementCommand(AdvertisementServiceImpl advertisementService, Long advertisementId) {
        this.advertisementService = advertisementService;
        this.advertisementId = advertisementId;
    }

    @Override
    public void execute() throws WebTaskException {
        logger.info("Rejecting advertisement ID: {}", advertisementId);
        advertisementService.rejectAdvertisement(advertisementId);
    }
}