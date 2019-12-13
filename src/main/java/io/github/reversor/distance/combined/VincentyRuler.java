package io.github.reversor.distance.combined;

import io.github.reversor.distance.DistanceCalculator;
import io.github.reversor.distance.ruler.wgs84.CheapRulerWGS84;
import io.github.reversor.distance.vincenty.Vincenty;

public class VincentyRuler implements DistanceCalculator {

    private double upperLimit;
    private double lowerLimit;
    private double interval;

    private DistanceCalculator ruler;
    private DistanceCalculator vincenty;

    public VincentyRuler(double interval, double error) {
        if (error > (interval / 2)) {
            throw new IllegalArgumentException("The error value must be less than half of the interval value");
        }

        this.interval = interval;
        this.upperLimit = error;
        this.lowerLimit = interval - error;
        this.ruler = CheapRulerWGS84.createDistanceCalculator();
        this.vincenty = Vincenty.createDistanceCalculator();
    }


    @Override
    public double calc(double lat1, double lon1, double lat2, double lon2) {
        double rulerDistance = ruler.calc(lat1, lon1, lat2, lon2);

        double near = rulerDistance % interval;
        if ((near < upperLimit) || (near > lowerLimit)) {
            return vincenty.calc(lat1, lon1, lat2, lon2);
        }

        return rulerDistance;
    }
}
