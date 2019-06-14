package io.github.reversor.distance.ruler.fcc;

import io.github.reversor.distance.ruler.BaseCheapRuler;

final public class CheapRulerFCC extends BaseCheapRuler {

    public CheapRulerFCC(double lat, double lon) {
        super(lat, lon);
    }

    public CheapRulerFCC() {
    }

    @Override
    protected void initCoefficients(double lat1) {
        this.lat = lat1;
        double cos = Math.cos(lat1 * Math.PI / 180.0);
        double cos2 = 2.D * cos * cos - 1;
        double cos3 = 2.D * cos * cos2 - cos;
        double cos4 = 2.D * cos * cos3 - cos2;
        double cos5 = 2.D * cos * cos4 - cos3;

        kx = 111415.13D * cos - 94.55D * cos3 + 0.12D * cos5;
        ky = 111132.09D - 566.05D * cos2 + 1.2D * cos4;
    }

}
