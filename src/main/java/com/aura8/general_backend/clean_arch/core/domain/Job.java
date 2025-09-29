package com.aura8.general_backend.clean_arch.core.domain;

public class Job {
    private Integer id;
    private String name;
    private String description;
    private Integer expectedDurationMinutes;
    private Double price;
    private Boolean deleted = false;

    public Job(String name, String description, Integer expectedDurationMinutes, Double price) {
        this.name = validateName(name);
        this.description = validateDescription(description);
        this.expectedDurationMinutes = validateDuration(expectedDurationMinutes);
        this.price = validatePrice(price);
    }

    public Job(Integer id, String name, String description, Integer expectedDurationMinutes, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.expectedDurationMinutes = expectedDurationMinutes;
        this.price = price;
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
        this.name = validateName(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = validateDescription(description);
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

    private String validateName(String name) {
        if(!isValidString(name)) throw new IllegalArgumentException("Nome inválido: " + name);
        return name;
    }

    private String validateDescription(String description) {
        if(!isValidString(description)) throw new IllegalArgumentException("Descrição inválida: " + description);
        return description;
    }

    private Boolean isValidString(String str) {
        if(str == null || str.trim().isEmpty() || str.length() < 3) return false;
        return true;
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
