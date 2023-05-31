package com.example.desafiodescartes.domain.route.entity;

import java.time.LocalDate;
import java.util.List;

import com.example.desafiodescartes.domain.route.enums.StatusRouteEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "route")
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ROUTE")
	private Long id;

	@Enumerated(EnumType.STRING)
	private StatusRouteEnum status;

	private LocalDate date;

	@OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
	private List<Stop> stops;
	
	
	public void addRouteToStops() {
		if(stops == null && stops.isEmpty())
			return;
		
		stops.forEach(stop -> stop.setRoute(this));
	}

}
