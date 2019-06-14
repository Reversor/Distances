package io.github.reversor.distance.vincenty;

final public class Vincenty extends BaseVincenty {

    public Vincenty() {
    }

    public Vincenty(double lat, double lon) {
        super(lat, lon);
    }

    @Override
    public double calc(double lat1, double lon1, double lat2, double lon2) {
        this.lat = Math.toRadians(lat1);
        this.lon = Math.toRadians(lon1);
        return calc(lat2, lon2);
    }

    @Override
    public double calc(double lat, double lon) {
        return calculations(Math.toRadians(lon) - this.lon, this.lat, Math.toRadians(lat));
    }
}
