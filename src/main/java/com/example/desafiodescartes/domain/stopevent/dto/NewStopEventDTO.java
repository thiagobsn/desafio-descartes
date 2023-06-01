package com.example.desafiodescartes.domain.stopevent.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewStopEventDTO {
	
	@NotNull
	private Long routeId;
	
	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

	@NotNull
	private LocalDateTime instant;

}
