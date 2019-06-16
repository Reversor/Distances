package io.github.reversor.distance.ruler.wgs84;

import io.github.reversor.distance.DistanceCalculator;
import io.github.reversor.distance.FixedDistanceCalculator;
import io.github.reversor.distance.constants.WGS84;
import io.github.reversor.distance.ruler.BaseCheapRuler;

final public class CheapRulerWGS84 extends BaseCheapRuler {

    private CheapRulerWGS84(double lat, double lon) {
        super(lat, lon);
        initCoefficients(lat);
    }

    private CheapRulerWGS84() {
        super();
    }

    public static FixedDistanceCalculator apply(double lat, double lon) {
        return new CheapRulerWGS84(lat, lon);
    }

    public static DistanceCalculator apply() {
        return new CheapRulerWGS84();
    }

    @Override
    final protected void initCoefficients(double lat1) {
        this.lat = lat1;
        // Here are the parameters (major radius in km and flattening) for the
        // Clarke 1866 ellipsoid which were used to obtain the expansion
        // parameters used in the FCC formula.
        // These should be switched to the WGS84 parameters soon!
        // double a = 6378.137, f = 1/298.257223563;
        double a = WGS84.RADIUS_EQUATOR, f = WGS84.FLATTENING;

        double mul = (Math.PI / 180) * a;
        double cos = Math.cos(lat1 * Math.PI / 180);
        double den2 = (1 - f) * (1 - f) + f * (2 - f) * cos * cos;
        double den = Math.sqrt(den2);

        // multipliers for converting longitude and latitude
        // degrees into distance
        //   kx = pi/180 * N * cos(phi)
        //   ky = pi/180 * M
        // where phi = latitude and from
        // https://en.wikipedia.org/wiki/Earth_radius#Principal_sections
        //   N = normal radius of curvature
        //     = a^2/((a*cos(phi))^2 + (b*sin(phi))^2)^(1/2)
        //   M = meridional radius of curvature
        //     = (a*b)^2/((a*cos(phi))^2 + (b*sin(phi))^2)^(3/2)
        kx = mul * cos / den;
        ky = mul * (1 - f) * (1 - f) / (den * den2);
    }

}
