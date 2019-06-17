package io.github.reversor.distance.util;

import io.github.reversor.distance.FixedDistanceCalculator;
import io.github.reversor.distance.ruler.wgs84.CheapRulerWGS84;
import io.github.reversor.distance.vincenty.Vincenty;

public class RingFinder {

    private FixedDistanceCalculator ruler;
    private FixedDistanceCalculator vincenty;
    private double startRadius;
    private double endRadius;
    private double width;
    private double maxRadius;

    private RingFinder() {

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

        if (distance < startRadius || distance > endRadius) {
            return -1;
        }

        return (int) ring;
    }

    public static class Builder {

        RingFinder finder;

        public Builder newInstance() {
            finder = new RingFinder();

            return this;
        }

        public Builder setStartPoint(double lat, double lon) {
            finder.ruler = CheapRulerWGS84.apply(lat, lon);
            finder.vincenty = Vincenty.apply(lat, lon);

            return this;
        }

        public Builder setMaxRadius(double maxRadius) {
            finder.maxRadius = maxRadius;

            return this;
        }

        public Builder setRadiusWidth(double width) {
            finder.width = width;

            return this;
        }

        public Builder setRequiredRange(double startRadius, double endRadius) {
            finder.startRadius = startRadius;
            finder.endRadius = endRadius;

            return this;
        }

        public RingFinder build() {
            return finder;
        }
    }
}
