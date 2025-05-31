package com.aura8.general_backend.dtos.job;

public class JobSimpleResponseDto {
    private Integer id;
    private String name;

    private Double price;

    public JobSimpleResponseDto(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;


        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
