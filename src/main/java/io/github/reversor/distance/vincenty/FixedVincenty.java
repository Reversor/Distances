package io.github.reversor.distance.vincenty;

import io.github.reversor.distance.FixedDistanceCalculator;

public class FixedVincenty extends BaseVincenty implements FixedDistanceCalculator {

    private double lat;
    private double lon;

    public FixedVincenty(double lat, double lon) {
        this.lat = Math.toRadians(lat);
        this.lon = Math.toRadians(lon);
    }

    @Override
    public double calc(double lat, double lon) {
        return calculations(Math.toRadians(lon) - this.lon, this.lat, Math.toRadians(lat));
    }


}
