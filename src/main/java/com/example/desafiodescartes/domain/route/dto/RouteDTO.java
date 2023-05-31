package com.example.desafiodescartes.domain.route.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.desafiodescartes.domain.route.enums.StatusRouteEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteDTO {
	
	private Long routeCode;
	
	private StatusRouteEnum status;
	
	private LocalDate date;
	
	private List<StopDTO> stops;

}
