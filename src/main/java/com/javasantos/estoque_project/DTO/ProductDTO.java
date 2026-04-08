package com.javasantos.estoque_project.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private CategoryInfo category;
    private ResponsibleInfo responsible;
    private Integer quantity;
    private Boolean active;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dateCreate;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.code = product.getCode();
        this.price = product.getPrice();
        this.category = new CategoryInfo(product.getCategory().getId(), product.getCategory().getName());
        this.responsible = new ResponsibleInfo(product.getResponsible().getId(), product.getResponsible().getName());
        this.quantity = product.getQuantity();
        this.active = product.getActive();
        this.dateCreate = product.getDateCreate();
    }
    
    public record CategoryInfo(Long id, String name) {}
    public record ResponsibleInfo(Long id, String name) {}
}
