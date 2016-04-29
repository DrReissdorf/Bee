import java.awt.geom.Point2D;

public class Operations {
    public static Punkt kreisSchnittpunkt(Punkt from, Punkt to) {
        Vector2D richtung = new Vector2D(to.getX()-from.getX(),to.getY()-from.getY());
        Vector2D tempNormVec = richtung.normVector();
        return new Punkt(tempNormVec.getX(),tempNormVec.getY());
    }

    public static Punkt kreisZwischenSchnittpunkte(Punkt currentPos, Punkt schnittpunkt_1, Punkt schnittpunkt_2) {
        Vector2D richtungsVektor = new Vector2D(schnittpunkt_2.getX()-schnittpunkt_1.getX(),schnittpunkt_2.getY()-schnittpunkt_1.getY());
        Vector2D halberRichtungsVektor = richtungsVektor.multiplikation(0.5);

        Vector2D currentPosToSchnittpunktVektor = new Vector2D(halberRichtungsVektor.getX()-currentPos.getX(),halberRichtungsVektor.getY()-currentPos.getY());
        Vector2D currentPosToSchnittpunktVektorNorm = currentPosToSchnittpunktVektor.normVector();

        return new Punkt(currentPosToSchnittpunktVektorNorm.getX(),currentPosToSchnittpunktVektorNorm.getY());
    }

    public static int[] getPosition(Punkt p) {
        int a[] = new int[2];
        a[0] = (int) ((p.getX() + 7) * 34);
        a[1] = (int) ((p.getY() - 7) * -34);

        return a;
    }

    public static double getWinkel(Vector2D p1) {
        double ergebnis = (((Math.atan2(p1.getY(), p1.getX())) * 180) / Math.PI); //Umrechung ins Gradmaß
        return Math.round(ergebnis * 100.0)/100.0;
    }

    /**
     * Gibt die Abweichungen der durch den Vektor und den Punkt gebildeten Winkel
     *
     * @param endvektor - Berechneter Endvektor des Algorithmus
     * @param currentPos - Aktuelle Position der Biene
     * @return gibt die Abweichung zurueck
     */
    public static double abweichungBestimmen(Punkt currentPos, Vector2D endvektor) {
        Vector2D perfectVector = new Vector2D(0-currentPos.getX(), 0-currentPos.getY());
        double winkelZwischenVektoren = endvektor.winkel(perfectVector);

        return winkelZwischenVektoren;
    }

    public static Punkt geringsterAbstandPunkt(Punkt toCheck, Punkt p1, Punkt p2, Punkt p3) {
        Vector2D vektorToP1 = new Vector2D(p1.getX()-toCheck.getX(), p1.getY()-toCheck.getY());
        Vector2D vektorToP2 = new Vector2D(p2.getX()-toCheck.getX(), p2.getY()-toCheck.getY());
        Vector2D vektorToP3 = new Vector2D(p3.getX()-toCheck.getX(), p3.getY()-toCheck.getY());

        double betragToP1 = vektorToP1.betrag();
        double betragToP2 = vektorToP2.betrag();
        double betragToP3 = vektorToP3.betrag();

        if(betragToP1<=betragToP2 && betragToP1<=betragToP3) {
            return p1;
        } else {
            if(betragToP2<betragToP1 && betragToP2<betragToP3) {
                return p2;
            }
        }

        return p3;
    }

    public static Vector2D berechneTurnVector(Punkt currentPos, Punkt m, Punkt kp) {
        Vector2D vektorToM = new Vector2D(currentPos.getX() - m.getX(), currentPos.getY()-m.getY());
        Vector2D vektorToKp = new Vector2D(currentPos.getX() - kp.getX(), currentPos.getY()-kp.getY());

        double winkel = vektorToM.winkel(vektorToKp);

        if(winkel >= 180) {
            return vektorToKp.orthogonale();
        } else return vektorToKp.orthogonale().multiplikation(-1);
    }

    public static Vector2D berechnePositionVector(Punkt currentPos, Punkt kp) {
        return new Vector2D(currentPos,kp);
    }
}
