package org.x98zy.webtask.model;

import java.time.LocalDateTime;

public class Message {
    private Long id;
    private Long advertisementId;
    private Long fromUserId;
    private Long toUserId;
    private String messageText;
    private Boolean isRead;
    private LocalDateTime createdAt;

    public Message() {}

    public Message(Long id, Long advertisementId, Long fromUserId, Long toUserId,
                   String messageText, Boolean isRead, LocalDateTime createdAt) {
        this.id = id;
        this.advertisementId = advertisementId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.messageText = messageText;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAdvertisementId() { return advertisementId; }
    public void setAdvertisementId(Long advertisementId) { this.advertisementId = advertisementId; }

    public Long getFromUserId() { return fromUserId; }
    public void setFromUserId(Long fromUserId) { this.fromUserId = fromUserId; }

    public Long getToUserId() { return toUserId; }
    public void setToUserId(Long toUserId) { this.toUserId = toUserId; }

    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}