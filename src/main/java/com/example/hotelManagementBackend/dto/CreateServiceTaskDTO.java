package com.example.hotelManagementBackend.dto;

import com.example.hotelManagementBackend.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateServiceTaskDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Long iconId;

    @NotNull
    private TaskPriority priority;

    @NotNull
    private Integer durationMinutes;

    @NotEmpty
    private List<Long> tagIds;

    // Optional fields
    private String imageUrl;
    private Boolean isPaid;
    private Double price;
    private Boolean approvalRequired;
    private Boolean guestRequestable;
    private String module;
    private int departmentId;

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public @NotNull Long getIconId() {
        return iconId;
    }

    public void setIconId(@NotNull Long iconId) {
        this.iconId = iconId;
    }

    public @NotNull TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(@NotNull TaskPriority priority) {
        this.priority = priority;
    }

    public @NotNull Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(@NotNull Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public @NotEmpty List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(@NotEmpty List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getApprovalRequired() {
        return approvalRequired;
    }

    public void setApprovalRequired(Boolean approvalRequired) {
        this.approvalRequired = approvalRequired;
    }

    public Boolean getGuestRequestable() {
        return guestRequestable;
    }

    public void setGuestRequestable(Boolean guestRequestable) {
        this.guestRequestable = guestRequestable;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
