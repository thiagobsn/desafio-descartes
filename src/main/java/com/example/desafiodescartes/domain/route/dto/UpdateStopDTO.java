package com.example.desafiodescartes.domain.route.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStopDTO {
	
	@NotNull
	private Long id;
	
	@NotNull
	private String description;
	
	@NotNull
	private Double latitude;
	
	@NotNull
	private Double longitude;
	
	@NotNull
	private String status;
	
	@NotNull
	private Integer deliveryRadius;

}
