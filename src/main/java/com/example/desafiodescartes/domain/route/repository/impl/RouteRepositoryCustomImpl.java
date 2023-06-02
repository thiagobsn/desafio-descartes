package com.example.desafiodescartes.domain.route.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.desafiodescartes.domain.route.dto.RouteStopLongerDTO;
import com.example.desafiodescartes.domain.route.repository.RouteRepositoryCustom;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RouteRepositoryCustomImpl implements RouteRepositoryCustom {
	
	public final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<RouteStopLongerDTO> listLongerStopsRoutes() {
		StringBuilder query = new StringBuilder("");
		query.append("WITH STOP_EVENT_QUERY AS ( "
				+ "	SELECT SE.*, "
				+ "	ROW_NUMBER() OVER (PARTITION BY SE.ID_ROUTE, SE.ID_STOP , SE.STATUS_STOP ORDER BY SE.ID_ROUTE, SE.ID_STOP, SE.INSTANT ASC) AS RANK "
				+ "	FROM STOP_EVENT SE "
				+ "	WHERE SE.ID_STOP IS NOT NULL "
				+ "	AND SE.STATUS_STOP IN ( 'ON_ANSWER', 'ANSWER') "
				+ ") , "
				+ "STOP_TIME_QUERY AS ( "
				+ "	SELECT S.*, "
				+ "	DATEDIFF( MINUTE,  "
				+ "	( 	SELECT SEQ.INSTANT "
				+ "		FROM STOP_EVENT_QUERY SEQ "
				+ "		WHERE SEQ.RANK = 1 "
				+ "		AND SEQ.ID_ROUTE = S.ID_ROUTE "
				+ "		AND SEQ.ID_STOP = S.ID_STOP "
				+ "		AND SEQ.STATUS_STOP = 'ON_ANSWER'), "
				+ "	(	SELECT	SEQ.INSTANT "
				+ "		FROM STOP_EVENT_QUERY SEQ "
				+ "		WHERE SEQ.RANK = 1 "
				+ "		AND SEQ.ID_ROUTE = S.ID_ROUTE "
				+ "		AND SEQ.ID_STOP = S.ID_STOP "
				+ "		AND SEQ.STATUS_STOP = 'ANSWER') ) TIMESTOPMINUTE "
				+ "	FROM ROUTE R "
				+ "	INNER JOIN STOP S ON S.ID_ROUTE = R.ID_ROUTE "
				+ "         WHERE R.STATUS = 'DONE' "
				+ "), "
				+ "RANK_TIME_QUERY AS ( "
				+ "	SELECT STQ.*, "
				+ "	ROW_NUMBER() OVER (PARTITION BY STQ.ID_ROUTE ORDER BY STQ.TIMESTOPMINUTE DESC) AS RANKTIME "
				+ "	FROM STOP_TIME_QUERY STQ "
				+ ") "
				+ "SELECT ID_STOP AS IDSTOP, ID_ROUTE AS IDROUTE, DESCRIPTION, TIMESTOPMINUTE AS TIMEMINUTES "
				+ "FROM RANK_TIME_QUERY "
				+ "WHERE RANKTIME = 1 ");
		
		return namedParameterJdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<>(RouteStopLongerDTO.class));
	}

}
