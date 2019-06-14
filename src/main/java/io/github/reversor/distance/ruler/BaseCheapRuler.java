package io.github.reversor.distance.ruler;

import io.github.reversor.distance.BaseDistanceCalculator;

public abstract class BaseCheapRuler extends BaseDistanceCalculator {
    protected double kx;
    protected double ky;

    public BaseCheapRuler(double lat, double lon) {
        super(lat, lon);
        initCoefficients(lat);
    }

    public BaseCheapRuler() {
        super();
    }

    protected abstract void initCoefficients(double lat1);

    private double normalize(double v) {
        if (v > 180) {
            v -= 360;
        } else if (v < -180) {
            v += 360;
        }
        return v;
    }

    @Override
    final public double calc(double lat1, double lon1, double lat2, double lon2) {
        if (this.lat != lat1) {
            this.lat = lat1;
            initCoefficients(lat1);
        }

        double dX = normalize(lon1 - lon2) * kx;
        double dY = (lat1 - lat2) * ky;

        return Math.sqrt(dX * dX + dY * dY);
    }

    @Override
    final public double calc(double lat, double lon) {
        double dX = normalize(this.lon - lon) * kx;
        double dY = (this.lat - lat) * ky;

        return Math.sqrt(dX * dX + dY * dY);
    }
}
