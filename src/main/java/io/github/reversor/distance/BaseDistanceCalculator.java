package io.github.reversor.distance;

public abstract class BaseDistanceCalculator implements DistanceCalculator, FixedDistanceCalculator {

    protected double lat;
    protected double lon;

    protected BaseDistanceCalculator(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    protected BaseDistanceCalculator() {

    }

}
