package com.javasantos.estoque_project.infrastructure.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javasantos.estoque_project.infrastructure.enuns.MovementType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "solicitations")
public class Solicitation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "responsible_id")
	private Responsible responsible;
	
	@ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
	
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime createSolicition;
	
	@Enumerated(EnumType.STRING)
	private MovementType status;
}
