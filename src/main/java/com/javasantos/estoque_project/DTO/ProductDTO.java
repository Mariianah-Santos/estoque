package com.javasantos.estoque_project.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.javasantos.estoque_project.infrastructure.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id", "name", "code", "price", "categoryId", "responsibleId", "quantity", "active"})
public class ProductDTO {

    private Long id;
    private String name;
    private String code;
    private Double price;
    private Long categoryId;
    private Long responsibleId;
    private Integer quantity;
    private Boolean active;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.code = product.getCode();
        this.price = product.getPrice();
        this.categoryId = product.getCategory().getId();
        this.responsibleId = product.getResponsible().getId();
        this.quantity = product.getQuantity();
        this.active = product.getActive();
    }
}
