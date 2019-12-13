package io.github.reversor.distance.ruler.wgs84;

import io.github.reversor.distance.DistanceCalculator;
import io.github.reversor.distance.FixedDistanceCalculator;
import io.github.reversor.distance.ruler.BaseCheapRuler;

import static io.github.reversor.distance.constants.WGS84.FLATTENING;
import static io.github.reversor.distance.constants.WGS84.RADIUS_EQUATOR;

final public class CheapRulerWGS84 extends BaseCheapRuler {

    private CheapRulerWGS84(double lat, double lon) {
        super(lat, lon);
        initCoefficients(lat);
    }

    private CheapRulerWGS84() {
        super();
    }

    public static FixedDistanceCalculator createFixedDistanceCalculator(double lat, double lon) {
        return new CheapRulerWGS84(lat, lon);
    }

    public static DistanceCalculator createDistanceCalculator() {
        return new CheapRulerWGS84();
    }

    @Override
    final protected void initCoefficients(double lat1) {
        this.lat = lat1;

        double mul = (Math.PI / 180) * RADIUS_EQUATOR;
        double cos = Math.cos(lat1 * Math.PI / 180);
        double den2 = (1 - FLATTENING) * (1 - FLATTENING) + FLATTENING * (2 - FLATTENING) * cos * cos;
        double den = Math.sqrt(den2);

        kx = mul * cos / den;
        ky = mul * (1 - FLATTENING) * (1 - FLATTENING) / (den * den2);
    }

}
