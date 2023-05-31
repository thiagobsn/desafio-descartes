package com.example.desafiodescartes.domain.route.dto;

import com.example.desafiodescartes.domain.route.enums.StatusStopEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StopDTO {
	
	private Long stopCode;
	
	private String description;
	
	private Double latitude;
	
	private Double longitude;
	
	private StatusStopEnum status;
	
	private Integer deliveryRadius;
	
}
