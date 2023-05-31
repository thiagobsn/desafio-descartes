package com.example.desafiodescartes.domain.stopevent.entity;

import java.time.LocalDateTime;

import com.example.desafiodescartes.domain.route.enums.StatusStopEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "STOP_EVENT")
public class StopEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_STOP_EVENT")
	private Long id;

	@Column(name = "ID_ROUTE")
	private Long idRoute;

	@Column(name = "ID_STOP")
	private Long idStop;

	@Column(name = "STATUS_STOP")
	@Enumerated(EnumType.STRING)
	private StatusStopEnum statusStop;

	private Double latitude;

	private Double longitude;

	private LocalDateTime instant;

}
