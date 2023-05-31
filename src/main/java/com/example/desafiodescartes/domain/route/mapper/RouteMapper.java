package com.example.desafiodescartes.domain.route.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.desafiodescartes.domain.route.dto.NewRouteDTO;
import com.example.desafiodescartes.domain.route.dto.NewStopDTO;
import com.example.desafiodescartes.domain.route.dto.RouteDTO;
import com.example.desafiodescartes.domain.route.dto.StopDTO;
import com.example.desafiodescartes.domain.route.dto.UpdateRouteDTO;
import com.example.desafiodescartes.domain.route.dto.UpdateStopDTO;
import com.example.desafiodescartes.domain.route.entity.Route;
import com.example.desafiodescartes.domain.route.entity.Stop;

@Mapper(componentModel = "spring")
public interface RouteMapper {
	
	public Route toRoute(NewRouteDTO dto);
	
	public NewRouteDTO toNewRouteDTO(Route route);
	
    @Mapping(source = "id", target = "routeCode")
	public RouteDTO toRouteDTO(Route route);
    
    public List<RouteDTO> toRouteDTO(List<Route> list);
    
    public Route toRoute(UpdateRouteDTO dto);
	
	
	public Stop toStop(NewStopDTO dto);
	
	public NewStopDTO toNewStopDTO(Stop Stop);
	
	@Mapping(source = "id", target = "stopCode")
	public StopDTO toStopDTO(Stop stop);
	
	public List<StopDTO> toStopDTO(List<Stop> list);
	
	public Stop toStop(UpdateStopDTO dto);
	
}
