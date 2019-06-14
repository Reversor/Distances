package io.github.reversor.distance;

import java.io.Serializable;

@FunctionalInterface
public interface DistanceCalculator extends Serializable {
    double calc(double lat1, double lon1, double lat2, double lon2);
}
