package com.example.desafiodescartes.domain.stopevent.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewStopEventDTO {
	
	private Long routeId;
	
	private Double latitude;

	private Double longitude;

	private LocalDateTime instant;

}
