package com.example.desafiodescartes.domain.route.dto;

import com.example.desafiodescartes.domain.route.enums.StatusRouteEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteStatusDTO {
	
	private Long idRoute;
	
	private StatusRouteEnum statusRoute;

}
