package io.github.reversor.distance;

@FunctionalInterface
public interface DistanceCalculator {
    double calc(double lat1, double lon1, double lat2, double lon2);
}
