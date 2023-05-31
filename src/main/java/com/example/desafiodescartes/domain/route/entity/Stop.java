package com.example.desafiodescartes.domain.route.entity;

import com.example.desafiodescartes.domain.route.enums.StatusStopEnum;

import jakarta.persistence.Column;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stop")
public class Stop {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_STOP")
	private Long id;
	
	private String description;
	
	private Double latitude;
	
	private Double longitude;
	
    @Enumerated(EnumType.STRING)
	private StatusStopEnum status;
    
	private Integer deliveryRadius;
	
	@ManyToOne
	@JoinColumn(name = "ID_ROUTE")
	private Route route;
	
}
