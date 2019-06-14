package io.github.reversor.distance;

import java.io.Serializable;

@FunctionalInterface
public interface FixedDistanceCalculator extends Serializable {
    double calc(double lat, double lon);
}
