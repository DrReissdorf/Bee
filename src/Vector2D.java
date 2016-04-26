public class Vector2D {
    private double x;
    private double y;

    Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Addiert den Vektor mit dem uebergebenen Vektor
     *
     * @param a
     * @return gibt den addierten Vektor zurueck
     */
    public Vector2D add(Vector2D a) {
        Vector2D d = new Vector2D(x, y);
        d.setX(a.getX() + this.x);
        d.setY(a.getY() + this.y);
        return d;
    }

    /**
     * Subtrahiert den Vektor mit dem uebergebenen Vektor
     *
     * @param a
     * @return gibt den subtrahierten Vektor zurueck
     */
    public Vector2D subtrahieren(Vector2D a) {
        Vector2D d = new Vector2D(x, y);
        d.setX(this.x - a.getX());
        d.setY(this.y - a.getY());
        return d;
    }

    /**
     * Berechnet das Skalarprodukt mit dem Vektor im Parameter
     *
     * @param a
     * @return Gibt das Skalarprodukt zurueck
     */
    public double skalar(Vector2D a) {
        return (a.x * this.x) + (a.y * this.y);
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
        this.setX(this.getX() * wert);
        this.setY(this.getY() * wert);
        return this;
    }

    public Vector2D addieren(Vector2D v1, Vector2D v2) {
        this.setX(v1.x * v2.x);
        this.setY(v1.y * v2.y);
        return this;
    }
    /**
     *
     * @return gibt den Winkel des Vektors zurück
     */
 /*public double winkelbestimmung(){
    Vector2D d = new Vector2D(x,y);
    return ((Math.atan2(d.getY(),d.getX()))*180)/Math.PI;;   
   }*/
}
