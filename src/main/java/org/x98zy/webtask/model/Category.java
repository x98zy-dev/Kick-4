package org.x98zy.webtask.model;

import java.time.LocalDateTime;

public class Category {
    private Long id;
    private String name;
    private String description;
    private Long createdBy; // ID администратора, который создал категорию
    private LocalDateTime createdAt;
    private Boolean isActive;

    public Category() {}

    public Category(Long id, String name, String description, Long createdBy,
                    LocalDateTime createdAt, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}