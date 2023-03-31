package com.example.productapi.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;

    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "description is required")
    @Size(min=8, message = "Product description should have more than 8 characters")
    private String description;

    @NotNull(message = "price is required")
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotEmpty(message = "Category is required")
    private String category;

    public ProductDto() {}

    public ProductDto(String name, String description, BigDecimal price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
