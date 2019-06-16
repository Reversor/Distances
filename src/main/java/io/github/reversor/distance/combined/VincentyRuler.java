package io.github.reversor.distance.combined;

import io.github.reversor.distance.DistanceCalculator;
import io.github.reversor.distance.ruler.wgs84.CheapRulerWGS84;
import io.github.reversor.distance.vincenty.Vincenty;

public class VincentyRuler implements DistanceCalculator {

    private double[] confidenceRange = new double[2];

    private DistanceCalculator ruler;
    private DistanceCalculator vincenty;
    private double interval;

    public VincentyRuler(double interval, double error) {
        if (error > interval) {
            throw new IllegalArgumentException();
        }
        this.interval = interval;
        confidenceRange[0] = error;
        confidenceRange[1] = interval - error;
        ruler = CheapRulerWGS84.apply();
        vincenty = Vincenty.apply();
    }


    @Override
    public double calc(double lat1, double lon1, double lat2, double lon2) {
        double rulerDistance = ruler.calc(lat1, lon1, lat2, lon2);

        double near = rulerDistance % interval;
        if ((near < confidenceRange[0]) || (near > confidenceRange[1])) {
            return vincenty.calc(lat1, lon1, lat2, lon2);
        }

        return rulerDistance;
    }
}
