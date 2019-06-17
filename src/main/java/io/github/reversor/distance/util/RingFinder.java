package io.github.reversor.distance.util;

import io.github.reversor.distance.FixedDistanceCalculator;
import io.github.reversor.distance.ruler.wgs84.CheapRulerWGS84;
import io.github.reversor.distance.vincenty.Vincenty;

public class RingFinder {

    private FixedDistanceCalculator ruler;
    private FixedDistanceCalculator vincenty;

    private double width;
    private double maxRadius;
    private int minRing;
    private int maxRing;

    public RingFinder(double lat, double lon, double width, double maxRadius, int minRing, int maxRing) {
        this.minRing = minRing;
        this.maxRing = maxRing;
        this.ruler = CheapRulerWGS84.apply(lat, lon);
        this.vincenty = Vincenty.apply(lat, lon);

        this.width = width;
        this.maxRadius = maxRadius;
    }

    public int calc(double lat, double lon) {
        double ring;
        double distance;

        distance = ruler.calc(lat, lon);
        if (distance > maxRadius) {
            return -1;
        }

        ring = distance / width;

        double fraction = ring - (int) ring;
        if (fraction > 0.175 || fraction < 0.125) {
            ring = vincenty.calc(lat, lon) / width;
        }


        int index = (int) ring;

        return index < minRing || index > maxRing ? -1 : index;
    }
}
