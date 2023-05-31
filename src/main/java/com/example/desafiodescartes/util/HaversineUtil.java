package com.example.desafiodescartes.util;

import org.springframework.stereotype.Component;

import com.example.desafiodescartes.domain.route.dto.StopDTO;
import com.example.desafiodescartes.domain.stopevent.entity.StopEvent;

/**
 * Jason Winn
 * http://jasonwinn.org
 * Created July 10, 2013
 *
 * Description: Small class that provides approximate distance between
 * two points using the Haversine formula.
 *
 * Call in a static context:
 * Haversine.distance(47.6788206, -122.3271205,
 *                    47.6788206, -122.5271205)
 * --> 14.973190481586224 [km]
 *
 */
@Component
public class HaversineUtil {
	
	public static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

	private double distance(double startLat, double startLong, double endLat, double endLong) {

		double dLat = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c; // <-- d
	}

	private double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}

	public Integer distanceMeters(double startLat, double startLong, double endLat, double endLong) {
		final int METERS = 1000;
		Double distanceDouble = distance(startLat, startLong, endLat, endLong);
		distanceDouble = (distanceDouble * METERS);
		return distanceDouble.intValue();
	}
    
    public Integer distanceMeters(StopEvent stopEvent, StopDTO stop) {
    	return distanceMeters(stopEvent.getLatitude(), stopEvent.getLongitude(), stop.getLatitude(), stop.getLongitude());
    }
    
    
}
