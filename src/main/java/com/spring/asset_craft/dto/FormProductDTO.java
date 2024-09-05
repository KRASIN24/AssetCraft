package com.spring.asset_craft.dto;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FormProductDTO {

    private List<MultipartFile> files;

    // TODO: add maven dependency to continue doing validation
    //@NotBlank(message = "Name is required")
    private String name;
    private String category;
    private Double price;
    private String description;

    public FormProductDTO(List<MultipartFile> files, String name, String category, Double price, String description) {
        this.files = files;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
