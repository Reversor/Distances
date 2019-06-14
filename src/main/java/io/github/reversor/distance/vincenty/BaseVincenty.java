package io.github.reversor.distance.vincenty;

import io.github.reversor.distance.BaseDistanceCalculator;
import io.github.reversor.distance.constants.WGS84;

abstract class BaseVincenty extends BaseDistanceCalculator {
    BaseVincenty(double lat, double lon) {
        this.lat = Math.toRadians(lat);
        this.lon = Math.toRadians(lon);
    }

    BaseVincenty() {
    }

    final double calculations(double l, double lat1, double lat2) {
        double cosU1;
        double sinU1;
        double cosU2;
        double sinU2;
        {
            double tan = (1 - WGS84.FLATTENING) * Math.tan(lat1);
            cosU1 = 1 / Math.sqrt((1 + tan * tan));
            sinU1 = tan * cosU1;

            tan = (1 - WGS84.FLATTENING) * Math.tan(lat2);
            cosU2 = 1 / Math.sqrt((1 + tan * tan));
            sinU2 = tan * cosU2;
        }

        double iterationLimit = 100;

        double lambda = l;
        double lambda2;
        double cosSqAlpha;
        double sigma;
        double cos2SigmaM;
        double cosSigma;
        double sinSigma;
        double sinLambda;
        double cosLambda;
        do {
            sinLambda = Math.sin(lambda);
            cosLambda = Math.cos(lambda);
            double v1 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda;
            double v2 = cosU2 * sinLambda;
            double sinSqSigma = v2 * v2 + v1 * v1;
            sinSigma = Math.sqrt(sinSqSigma);
            if (sinSigma == 0) {
                return 0.D;
            }
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);
            double sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
            cosSqAlpha = 1 - sinAlpha * sinAlpha;
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;

            if (Double.isNaN(cos2SigmaM)) {
                cos2SigmaM = 0;
            }
            double C = (WGS84.FLATTENING / 16) * cosSqAlpha * (4 + WGS84.FLATTENING * (4 - 3 * cosSqAlpha));
            lambda2 = lambda;
            double v = (1 - C) * WGS84.FLATTENING * sinAlpha
                    * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1
                    + 2 * cos2SigmaM * cos2SigmaM)));
            lambda = l + v;
        } while (Math.abs(lambda - lambda2) > 1e-12 && --iterationLimit > 0);

        if (iterationLimit == 0) {
            return Double.NaN;
        }

        double uSq = cosSqAlpha * (WGS84.RADIUS_EQUATOR * WGS84.RADIUS_EQUATOR - WGS84.RADIUS_POLES * WGS84.RADIUS_POLES)
                / (WGS84.RADIUS_POLES * WGS84.RADIUS_POLES);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double sqCos2SigmaM = cos2SigmaM * cos2SigmaM;
        double deltaSigma = B * sinSigma *
                (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * sqCos2SigmaM)
                        - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3
                        + 4 * sqCos2SigmaM)));

        return WGS84.RADIUS_POLES * A * (sigma - deltaSigma);
    }
}
