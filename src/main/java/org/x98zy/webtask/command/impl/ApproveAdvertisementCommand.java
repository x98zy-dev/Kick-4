package org.x98zy.webtask.command.impl;

import org.x98zy.webtask.command.Command;
import org.x98zy.webtask.service.advertisement.AdvertisementServiceImpl;
import org.x98zy.webtask.exception.WebTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApproveAdvertisementCommand implements Command {
    private final AdvertisementServiceImpl advertisementService;
    private final Long advertisementId;
    private static final Logger logger = LoggerFactory.getLogger(ApproveAdvertisementCommand.class);

    public ApproveAdvertisementCommand(AdvertisementServiceImpl advertisementService, Long advertisementId) {
        this.advertisementService = advertisementService;
        this.advertisementId = advertisementId;
    }

    @Override
    public void execute() throws WebTaskException {
        logger.info("Approving advertisement ID: {}", advertisementId);
        advertisementService.approveAdvertisement(advertisementId);
    }
}