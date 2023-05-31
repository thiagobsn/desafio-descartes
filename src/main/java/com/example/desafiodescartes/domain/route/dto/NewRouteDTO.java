package com.example.desafiodescartes.domain.route.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewRouteDTO {
	
	@NotBlank
	private String status;
	
	@NotNull
	private LocalDate date;
	
	@Valid
	@NotNull(message = "{route.stops.null}")
	@Size(min = 1, message = "{route.stops.empty}")
	private List<NewStopDTO> stops;

}
