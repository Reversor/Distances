package io.github.reversor.distance.ruler;

import io.github.reversor.distance.FixedDistanceCalculator;

public class FixedCheapRuler extends BaseCheapRuler implements FixedDistanceCalculator {
    protected double lon;

    public FixedCheapRuler(double lat, double lon) {
        initCoefficients(lat);
        this.lon = lon;
    }

    @Override
    public double calc(double lat, double lon) {
        double dX = (this.lon - lon) * kx;
        double dY = (this.lat - lat) * ky;

        return Math.sqrt(dX * dX + dY * dY);
    }
}
