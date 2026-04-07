package com.javasantos.estoque_project.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

	  private String name;
	  private String code;
	    private Double price;
	    private Long categoryId;
	    private Long responsibleId;
	    private Integer quantity;
	    private Boolean active;
	    private String movementType;
}
