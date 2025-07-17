package com.aura8.general_backend.dtos.job;

public class JobRequestDto {
    private String name;
    private String description;
    private Integer expectedDurationMinutes;
    private Double price;

    public JobRequestDto(String name, String description, Integer expectedDurationMinutes, Double price) {
        this.name = name;
        this.description = description;
        this.expectedDurationMinutes = expectedDurationMinutes;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExpectedDurationMinutes() {
        return expectedDurationMinutes;
    }

    public void setExpectedDurationMinutes(Integer expectedDurationMinutes) {
        this.expectedDurationMinutes = expectedDurationMinutes;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
