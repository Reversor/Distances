package io.github.reversor.distance.util;

import io.github.reversor.distance.FixedDistanceCalculator;
import io.github.reversor.distance.ruler.wgs84.CheapRulerWGS84;
import io.github.reversor.distance.vincenty.Vincenty;

public class RingFinder {

    private final FixedDistanceCalculator ruler;
    private final FixedDistanceCalculator vincenty;
    private final double startDistance;
    private final double endDistance;
    private final double width;
    private final double maxRadius;

    public RingFinder(double lat, double lon, double width, double maxRadius, double startDistance, double endDistance) {
        this.startDistance = startDistance;
        this.endDistance = endDistance;
        this.ruler = CheapRulerWGS84.apply(lat, lon);
        this.vincenty = Vincenty.apply(lat, lon);

        this.width = width;
        this.maxRadius = maxRadius;
    }

    public int calc(double lat, double lon) {
        double ring;
        double distance;

        distance = ruler.calc(lat, lon);

        ring = distance / width;

        double fraction = ring - (int) ring;
        if (fraction > 0.875 || fraction < 0.125) {
            distance = vincenty.calc(lat, lon);
            ring = distance / width;
        }

        if (distance > maxRadius) {
            return Integer.MIN_VALUE;
        }

        if ( distance < startDistance || distance > endDistance) {
            return -1;
        }

        return (int) ring;
    }
}
