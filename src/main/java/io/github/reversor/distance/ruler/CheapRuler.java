package io.github.reversor.distance.ruler;

import io.github.reversor.distance.DistanceCalculator;

public class CheapRuler extends BaseCheapRuler implements DistanceCalculator {

    @Override
    public double calc(double lat1, double lon1, double lat2, double lon2) {
        if (this.lat != lat1) {
            this.lat = lat1;
            initCoefficients(lat1);
        }

        double dX = (lon1 - lon2) * kx;
        double dY = (lat1 - lat2) * ky;

        return Math.sqrt(dX * dX + dY * dY);
    }
}
