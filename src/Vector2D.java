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

    /**
     * Addiert den Vektor mit dem uebergebenen Vektor
     *
     * @param a
     * @return gibt den addierten Vektor zurueck
     */
    public Vector2D add(Vector2D a) {
        return new Vector2D(x+a.getX(),y+a.getY());
    }

    /**
     * Subtrahiert den Vektor mit dem uebergebenen Vektor
     *
     * @param a
     * @return gibt den subtrahierten Vektor zurueck
     */
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

    /**
     * Berechnet das Skalarprodukt mit dem Vektor im Parameter
     *
     * @param a
     * @return Gibt das Skalarprodukt zurueck
     */
    public double skalar(Vector2D a) {
        return (a.x * x) + (a.y * y);
    }

    /**
     * Berechnet den Betrag des Vektors
     *
     * @return gibt den Betrag zurueck
     */
    public double betrag() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    /**
     * @param wert
     * @return Multipliziert alle Werte des Vektors mit dem Parameter
     */
    public Vector2D multiplikation(double wert) {
        return new Vector2D(x*wert,y*wert);
    }

    public double abstand(Vector2D v) {
        return subtrahieren(v).betrag();
    }

    public Vector2D normVector() {
        return multiplikation(1/ betrag());
    }

    public Vector2D orthogonale() {
        return new Vector2D(y*(-1),x);
    }
}
