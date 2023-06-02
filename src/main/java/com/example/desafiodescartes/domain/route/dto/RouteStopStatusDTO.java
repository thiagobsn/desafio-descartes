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
public class RouteStopStatusDTO {
	
	private Long idRoute;
	
	private Long idStop;
	
	private String descriptionStop;
	
	private StatusStopEnum statusStop;

}
