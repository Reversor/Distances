package io.github.reversor.distance.haversine;

import io.github.reversor.distance.DistanceCalculator;

public class Haversine implements DistanceCalculator {
    private static final int EARTH_RADIUS = 6371000; // Approx Earth radius in KM

    @Override
    public double calc(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians((lat2 - lat1));
        double dLong = Math.toRadians((lon2 - lon1));

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double b = Math.sin(dLat / 2);
        double d = Math.sin(dLong / 2);
        double a = b * b + Math.cos(lat1) * Math.cos(lat2) * d * d;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
