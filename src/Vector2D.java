public class Vector2D {
    private double x;
    private double y;

    Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Vector2D(Punkt from, Punkt to) {
        this.x = to.getX()-from.getX();
        this.y = to.getY()-from.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector2D add(Vector2D a) {
        return new Vector2D(x+a.getX(),y+a.getY());
    }

    public Vector2D subtrahieren(Vector2D a) {
        return new Vector2D(x-a.getX(),y-a.getY());
    }

    public double winkel(Vector2D v) {
        double skalarProdukt = skalar(v);
        double betragprodukt = betrag() * v.betrag();
        double winkel = Math.acos(skalarProdukt/betragprodukt)*100;

        double gerundetesErgebnis = Math.round(winkel*100.0)/100.0;

        if(gerundetesErgebnis > 180) gerundetesErgebnis = 360 - gerundetesErgebnis;

        return gerundetesErgebnis;
    }

    public double skalar(Vector2D a) {
        return (a.x * x) + (a.y * y);
    }

    public double betrag() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vector2D multiplikation(double wert) {
        return new Vector2D(x*wert,y*wert);
    }

    public Vector2D normVector() {
        return multiplikation(1/ betrag());
    }

    public Vector2D orthogonale() {
        return new Vector2D(y*(-1),x);
    }
}
