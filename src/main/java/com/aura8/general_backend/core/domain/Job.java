package com.aura8.general_backend.core.domain;

public class Job {
    private Integer id;
    private String name;
    private String description;
    private Integer expectedDurationMinutes;
    private Double price;
    private Boolean deleted = false;

    public Job(String name, Integer expectedDurationMinutes, Double price) {
        this.name = validateString(name);
        this.expectedDurationMinutes = validateDuration(expectedDurationMinutes);
        this.price = validatePrice(price);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validateString(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = validateString(description);
    }

    public Integer getExpectedDurationMinutes() {
        return expectedDurationMinutes;
    }

    public void setExpectedDurationMinutes(Integer expectedDurationMinutes) {
        this.expectedDurationMinutes = validateDuration(expectedDurationMinutes);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = validatePrice(price);
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        this.deleted = true;
    }

    private String validateString(String str) {
        if(str == null || str.trim().isEmpty() || str.length() < 3) throw new IllegalArgumentException("String inválida: " + str);
        return str;
    }

    private Double validatePrice(Double price) {
        if(price == null || price < 0) throw new IllegalArgumentException("Preço inválido: " + price);
        return price;
    }

    private Integer validateDuration(Integer duration) {
        if(duration == null || duration < 0) throw new IllegalArgumentException("Duração inválida: " + duration);
        return duration;
    }
}
