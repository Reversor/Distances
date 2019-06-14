package io.github.reversor.distance.vincenty;

import io.github.reversor.distance.DistanceCalculator;

public class Vincenty extends BaseVincenty implements DistanceCalculator {


    @Override
    public double calc(double lat1, double lon1, double lat2, double lon2) {
        return calculations(Math.toRadians(lon2) - Math.toRadians(lon1), Math.toRadians(lat1), Math.toRadians(lat2));
    }
}
