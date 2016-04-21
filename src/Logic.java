import java.awt.geom.Point2D;

public class Logic {

    private double radiusHome;
    private double radiusLM;
    private static Logic instanz;

    private Logic() {
        radiusHome = 1;
        radiusLM = 1;
    }

    public static Logic getInstance() {
        if (instanz == null) {
            instanz = new Logic();
        }
        return instanz;
    }


    /**
     * rechnet die Pixelpositionen in Punkte des Koordinatensystems um
     *
     * @param x
     * @param y
     * @return gibt einen Point mit den Punkten zurueck
     */
    public Point2D.Double getCoordinate(int x, int y) {

        double a = (x / 34) - 7;
        double b = (y / 34) + 7;
        return new Point2D.Double(a, b);

    }


    /**
     * rechnet die Punkte des Koordinatensystems in die Pixelpositionen um
     *
     * @param p
     * @return gibt ein Array[2] mit der x-Kooridnate[0] und der y-Koordinate[1] zurueck
     */
    public int[] getPosition(Point2D.Double p) {
        int a[] = new int[2];
        a[0] = (int) ((p.getX() + 7) * 34);
        a[1] = (int) ((p.getY() - 7) * -34);

        return a;

    }

    /**
     * Berechnet den Abstand zwischen den beiden uebergebenen Punkten
     *
     * @param p1
     * @param p2
     * @return gibt den Abstand zurueck
     */
    public double getAbstand(Point2D.Double p1, Point2D.Double p2) {
        return Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
    }


    /**
     * Berechnet den Abstand zwischen den beiden uebergebenen Vektoren
     *
     * @param p1
     * @param p2
     * @return gibt den Abstand zurueck
     */
    public double getAbstand(Vector2D p1, Vector2D p2) {
        return Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));
    }

    /**
     * Bestimmt den Richtungsvektor zweiter Punkte
     *
     * @param p1
     * @param p2
     * @return gibt den Richtungsvektor zurueck
     */
    public Vector2D richtungsvektor(Point2D.Double p1, Point2D.Double p2) {
        return new Vector2D(p2.x - p1.x, p2.y - p1.y);
    }

    /**
     * Bestimmt den Richtungsvektor eines Punktes und eines Vektors
     *
     * @param p1
     * @param v1
     * @return gibt den Richtungsvektor zurueck
     */
    public Vector2D richtungsvektor(Point2D.Double p1, Vector2D v1) {
        return new Vector2D(v1.getX() - p1.x, v1.getY() - p1.y);
    }


    public Point2D.Double richtungsvektoren(Point2D.Double p1, Point2D.Double p2) {
        return new Point2D.Double(p2.getX() - p1.x, p2.getY() - p1.y);
    }

    /**
     * Bestimmt einen Punkt auf dem Kreis mit dem Mittelpunkt p1
     *
     * @param richtungsvektor
     * @param p1
     * @param abstand
     * @return
     */
    public Point2D.Double getPoint(Vector2D richtungsvektor, Point2D.Double p1, double laenge) {
        return new Point2D.Double((richtungsvektor.getX() * laenge) + p1.x, (richtungsvektor.getY() * laenge) + p1.y);
    }


    /**
     * Bestimmt die Laenge der schwarzen Flaechen zum Vergleich
     *
     * @param abstand
     * @return gibt den Wert der Laenge zurueck
     */
    public double laengenBestimmung(double abstand) {
        return radiusHome / abstand;

    }


    /**
     * Addition von 6 Vektoren zu einem. Wichtig zur Bestimmung des einheitlichen Näherungsvektors bzw. Richtungsvektors
     *
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @return gibt einen Vektor zurueck bei dem alle Parameter aufaddiert wurden
     */
    public Vector2D addVector(Vector2D v1, Vector2D v2, Vector2D v3, Vector2D v4, Vector2D v5, Vector2D v6) {
        Vector2D v = v1.add(v2.add(v3.add(v4.add(v5.add(v6)))));
        return v;
    }

    /**
     * Naeherungsvektor wird in der Laenge verdreifacht und mit dem Richtungsvektor addiert
     *
     * @param v1 Naeherungsvektor
     * @param v2 Richtungsvektor
     * @return Rueckgabewert ist ein Vector2D
     */
    public Vector2D vectorenAddieren(Vector2D v1, Vector2D v2) {
        return v2.add(v1.multiplikation(3));
    }


    /**
     * Bestimmt den orthogonalen Vektor des uebergebenen Vektors
     *
     * @param v
     * @return gibt den orthogonalen Vektor zurueck
     */
    public Vector2D orthogonalerRichtungsvektor(Vector2D v) {
        return new Vector2D(v.getY(), v.getX() * -1);
    }

    /**
     * Bestimmt den Punkt, zu dem der Punkt p0 den geringsten Abstand hat
     *
     * @param p0 Ausgangspunkt mit dem alle anderen Punket verglichen werden
     * @param p1 Vergleichspunkt 1
     * @param p2 Vergleichspunkt 2
     * @param p3 Vergleichspunkt 3
     * @return der Punkt der bezueglich den Punkt p0 den kleinsten Abstand hat
     */
    public Point2D.Double kleinsterAbstand(Point2D.Double p0, Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
        double l1 = this.getAbstand(p0, p1);
        double l2 = this.getAbstand(p0, p2);
        double l3 = this.getAbstand(p0, p3);


        if (l1 < l2 && l1 < l3) {
            return p1;
        }
        if (l2 < l1 && l2 < l3) {
            return p2;
        }
        if (l3 < l1 && l3 < l2) {
            return p3;

        }
        return p1;

    }

    /**
     * Berechnet einen Punkt auf einem Kreis (Bestimmung der Mittelpunkte der weißen Flächen)
     *
     * @param mp
     * @param kp1
     * @param kp2
     * @param kp3
     * @return gibt den Mittelpunkt einer weißen Flaeche zurueck
     */
    public Point2D.Double punktbestimmung(Point2D.Double mp, Point2D.Double kp1, Point2D.Double kp2, Point2D.Double kp3) {
        Point2D.Double p = new Point2D.Double(((kp2.getX() - kp1.getX()) / 2) + kp1.getX(), ((kp2.getY() - kp1.getY()) / 2) + kp1.getY());

        double abstand1 = Math.abs(this.getAbstand(kp1, kp2));
        double abstand2 = Math.abs(this.getAbstand(kp2, kp3));
        double abstand3 = Math.abs(this.getAbstand(kp1, kp3));

        if ((abstand1 > abstand2) && (abstand1 > abstand3)) {
            double x = mp.getX() + (this.richtungsvektor(mp, p).getX() * (-1)) * (radiusHome / this.getAbstand(mp, p));
            double y = mp.getY() + (this.richtungsvektor(mp, p).getY() * (-1)) * (radiusHome / this.getAbstand(mp, p));

            return new Point2D.Double(x, y);
        }
        double x = mp.getX() + this.richtungsvektor(mp, p).getX() * (radiusHome / this.getAbstand(mp, p));
        double y = mp.getY() + this.richtungsvektor(mp, p).getY() * (radiusHome / this.getAbstand(mp, p));


        return new Point2D.Double(x, y);
    }

    /**
     * vergleicht den uebergebenen Punkt mit den in Snap gespeicherten Mittelpunkten der weißen Flaeche
     *
     * @param p0
     * @param snap
     * @return gibt den Abstand zwischen p0 und dem Snap mit der kuerzesten Strecke zurueck
     */
    public double vergleichStreckeWeiß(Point2D.Double p0, Snapshot snap) {
        double l1 = this.getAbstand(p0, snap.getM4());
        double l2 = this.getAbstand(p0, snap.getM5());
        double l3 = this.getAbstand(p0, snap.getM6());

        if (l1 < l2) {
            if (l1 < l3) {
                return this.getAbstand(snap.getM1(), snap.getM2());
            } else return this.getAbstand(snap.getM1(), snap.getM3());
        } else {
            if (l2 < l3) {
                return this.getAbstand(snap.getM2(), snap.getM3());
            } else return this.getAbstand(snap.getM1(), snap.getM3());
        }
    }

    /**
     * Vergleicht die Strecken und gibt so die Richtung des Vektors zurueck
     *
     * @param d1 Abstand
     * @param p1
     * @param p2
     * @return 1, wenn d1 größer als der Abstand der beiden uebergebenen Punkte, ansonsten -1
     */
    public int richtungDesVectors(double d1, Point2D.Double p1, Point2D.Double p2) {
        double d2 = getAbstand(p1, p2);
        if (d1 < d2) return 1;
        return -1;
    }


    /**
     * Addiert die beiden Punkte p1 und p2
     *
     * @param p1
     * @param p2
     * @return gibt den addierten Punkt zurueck
     */
    public Point2D.Double punktAddieren(Point2D.Double p1, Point2D.Double p2) {
        return new Point2D.Double(p1.x + p2.x, p1.y + p2.y);
    }

    /**
     * Gibt die Abweichungen der durch den Vektor und den Punkt gebildeten Winkel
     *
     * @param v Vector
     * @param p Punkt
     * @return gibt die Abweichung zurueck
     */
    public double abweichungBestimmen(Vector2D v, Point2D.Double p) {
        double winkelVec = 90 - this.getWinkel(v) + 180;
        double winkelPun = (90 - this.getWinkel(p)) + 180;
        if ((p.x == 0 && p.y == 0) || winkelVec == 0 || winkelPun == 0) {
            return 0;
        } else {

            return winkelPun - winkelVec;

        }
    }

    /**
     * Berechnet den Winkel des uebergebenen Vektors
     *
     * @param p1
     * @return gibt den Winkel zurueck
     */
    public double getWinkel(Vector2D p1) {
        if (Double.isNaN(((Math.atan2(p1.getY(), p1.getX())) * 180) / Math.PI))
            return 1;
        else return (((Math.atan2(p1.getY(), p1.getX())) * 180) / Math.PI); //Umrechung ins Gradmaß

    }


    /**
     * Berechnet den Winkel eines Punktes
     *
     * @param p1
     * @return gibt den Winkel zurueck
     */
    public double getWinkel(Point2D.Double p1) {
        double winkel = ((Math.atan2(p1.getY(), p1.getX())) * 180) / Math.PI; //Umrechung ins Gradmaß
        return winkel;
    }

    /**
     * Bestimmt einen Winkel zwischen zwei Vektoren
     *
     * @param v1 Vektor 1
     * @param v2 Vektor 2
     * @return gibt den Winkel zwischen den beiden uebergebenen Vektoren zurueck
     */
    public double getWinkel(Vector2D v1, Vector2D v2) {
        return Math.acos((v1.skalar(v2)) / (v1.betrag() * v2.betrag()));
    }


    /**
     * Mittelpunkt der schwarzen Fläche zwischen einem Punkt auf dem Koordinatensystem und einem Landmark
     *
     * @param p1 Punkt Punkt des Git's
     * @param p2 Punkt Mittelpunkt des Landmarks
     * @return Gibt den Mittelpunkt der schwarzen Flaeche zwischen p1 und p2 zurueck
     */
    public Point2D.Double mittelpunkt(Point2D.Double p1, Point2D.Double p2) {
        double abstand = getAbstand(p1, p2);
        double laenge = laengenBestimmung(abstand);
        Vector2D vector = richtungsvektor(p1, p2);
        return getPoint(vector, p1, laenge);
    }

    /**
     * Vergleicht den Punkt mit den ersten drei Punkten des Snaps
     *
     * @param p1
     * @param pHome
     * @param s
     * @return gibt den Vektor zurueck, der aus dem uebergebenen Punkt und dem Punkt mit dem geringsten Abstand gebildet wird
     */
    public Vector2D VectorAbstandzumnaechstenPunktSchwarz(Point2D.Double p1, Point2D.Double pHome, Snapshot s) {
        //  Point2D.Double kleinsterAbstand = kleinsterAbstand(p1, s.getM1(),s.getM2(),s.getM3());
        return richtungsvektor(pHome, kleinsterAbstand(p1, s.getM1(), s.getM2(), s.getM3()));
    }

    /**
     * Vergleicht den Punkt mit den letzten drei Punkten des Snaps
     *
     * @param p1
     * @param pHome
     * @param s
     * @return gibt den Vektor zurueck, der aus dem uebergebenen Punkt und dem Punkt mit dem geringsten Abstand gebildet wird
     */
    public Vector2D VectorAbstandzumnaechstenPunktWeiß(Point2D.Double p1, Point2D.Double pHome, Snapshot s) {
        // Point2D.Double kleinsterAbstand = kleinsterAbstand(p1, s.getM4(),s.getM5(),s.getM6());
        return richtungsvektor(pHome, kleinsterAbstand(p1, s.getM4(), s.getM5(), s.getM6()));
    }


}
