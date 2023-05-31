package com.example.desafiodescartes.domain.stopevent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.desafiodescartes.domain.stopevent.dto.NewStopEventDTO;
import com.example.desafiodescartes.domain.stopevent.entity.StopEvent;

@Mapper(componentModel = "spring")
public interface StopEventMapper {
	
	@Mapping(source = "routeId", target = "idRoute")
	public StopEvent toRouteDTO(NewStopEventDTO dto);
	    
}
