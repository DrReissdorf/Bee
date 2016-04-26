import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.System.in;

public class CanvasGUI extends Canvas {
    private Point[] origPoints;
    private Logic logic;
    private Point2D.Double home;
    private Point2D.Double LM1;
    private Point2D.Double LM2;
    private Point2D.Double LM3;
    private Snapshot snapHome;
    private Snapshot snap;
    private Point2D.Double M1;
    private Point2D.Double M2;
    private Point2D.Double M3;
    private Point2D.Double M4;
    private Point2D.Double M5;
    private Point2D.Double M6;
    private Point2D.Double currentPos;
    private Point2D.Double KP1;
    private Point2D.Double KP2;
    private Point2D.Double KP3;
    private Point2D.Double KP4;
    private Point2D.Double KP5;
    private Point2D.Double KP6;
    private double abweichungen;

    private String lm1_x;
    private String lm1_y;
    private String lm2_x;
    private String lm2_y;
    private String lm3_x;
    private String lm3_y;

    CanvasGUI() {
        logic = Logic.getInstance();
        home = new Point2D.Double(0, 0);
        LM1 = new Point2D.Double(3.5, 2);
        LM2 = new Point2D.Double(3.5, -2);
        LM3 = new Point2D.Double(0, -4);

        //getInput();

        Point2D.Double mittelPunktTemporary = logic.mittelpunkt(home, LM1);
        M1 = new Point2D.Double(mittelPunktTemporary.getX(),mittelPunktTemporary.getY());

        mittelPunktTemporary = logic.mittelpunkt(home, LM2);
        M2 = new Point2D.Double(mittelPunktTemporary.getX(),mittelPunktTemporary.getY());

        mittelPunktTemporary = logic.mittelpunkt(home, LM3);
        M3 = new Point2D.Double(mittelPunktTemporary.getX(),mittelPunktTemporary.getY());

        M4 = logic.punktbestimmung(home, M1, M2, M3);
        M5 = logic.punktbestimmung(home, M3, M2, M1);
        M6 = logic.punktbestimmung(home, M1, M3, M2);

        snapHome = new Snapshot(M1, M2, M3, M4, M5, M6);

        abweichungen = 0;
    }

    private void getInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try{
            System.out.println("LM1 x-Koordinate:");
            lm1_x = br.readLine();

            System.out.println("LM1 y-Koordinate:");
            lm1_y = br.readLine();

            System.out.println("LM2 x-Koordinate:");
            lm2_x = br.readLine();

            System.out.println("LM2 y-Koordinate:");
            lm2_y = br.readLine();

            System.out.println("LM3 x-Koordinate:");
            lm3_x = br.readLine();

            System.out.println("LM3 y-Koordinate:");
            lm3_y = br.readLine();


            LM1 = new Point2D.Double(Double.valueOf(lm1_x), Double.valueOf(lm1_y));
            LM2 = new Point2D.Double(Double.valueOf(lm2_x), Double.valueOf(lm2_y));
            LM3 = new Point2D.Double(Double.valueOf(lm3_x), Double.valueOf(lm3_y));
        } catch (Exception e) {

        }


    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int[] landmark1 = logic.getPosition(LM1);
        int[] landmark2 = logic.getPosition(LM2);
        int[] landmark3 = logic.getPosition(LM3);

        g2d.setColor(new Color(102, 152, 248));
        g2d.fillOval(landmark1[0], landmark1[1], 17 * 2, 17 * 2);
        g2d.fillOval(landmark2[0], landmark2[1], 17 * 2, 17 * 2);
        g2d.fillOval(landmark3[0], landmark3[1], 17 * 2, 17 * 2);

        for (int i = -7; i <= 7; i++) {
            for (int j = 7; j >= -7; j--) {
                currentPos = new Point2D.Double(i, j);

                /********* SNAPSHOT SCHNITTPUNKTE BESTIMMEN **************/
                Point2D.Double mittelPunktTemporary = logic.mittelpunkt(currentPos, LM1);
                KP1 = new Point2D.Double(mittelPunktTemporary.getX(), mittelPunktTemporary.getY());

                mittelPunktTemporary = logic.mittelpunkt(currentPos, LM2);
                KP2 = new Point2D.Double(mittelPunktTemporary.getX(), mittelPunktTemporary.getY());

                mittelPunktTemporary = logic.mittelpunkt(currentPos, LM3);
                KP3 = new Point2D.Double(mittelPunktTemporary.getX(), mittelPunktTemporary.getY());

                KP4 = logic.punktbestimmung(currentPos, KP1, KP2, KP3);
                KP5 = logic.punktbestimmung(currentPos, KP2, KP3, KP1);
                KP6 = logic.punktbestimmung(currentPos, KP1, KP3, KP2);

                snap = new Snapshot(KP1, KP2, KP3, KP4, KP5, KP6);
                /***********************************************************/

                Vector2D vectorgeringsterAbstand1 = logic.VectorAbstandzumnaechstenPunktSchwarz(logic.punktAddieren(snapHome.getM1(), currentPos), currentPos, snap);
                Vector2D vectorgeringsterAbstand2 = logic.VectorAbstandzumnaechstenPunktSchwarz(logic.punktAddieren(snapHome.getM2(), currentPos), currentPos, snap);
                Vector2D vectorgeringsterAbstand3 = logic.VectorAbstandzumnaechstenPunktSchwarz(logic.punktAddieren(snapHome.getM3(), currentPos), currentPos, snap);

                Vector2D vectorgeringsterAbstand4 = logic.VectorAbstandzumnaechstenPunktWeiß(logic.punktAddieren(snapHome.getM4(), currentPos), currentPos, snap);
                Vector2D vectorgeringsterAbstand5 = logic.VectorAbstandzumnaechstenPunktWeiß(logic.punktAddieren(snapHome.getM5(), currentPos), currentPos, snap);
                Vector2D vectorgeringsterAbstand6 = logic.VectorAbstandzumnaechstenPunktWeiß(logic.punktAddieren(snapHome.getM6(), currentPos), currentPos, snap);

                Vector2D vector11 = getOrthogonalenRichtungsvektor(M1,snap,vectorgeringsterAbstand1);
                Vector2D vector21 = getOrthogonalenRichtungsvektor(M2,snap,vectorgeringsterAbstand2);
                Vector2D vector31 = getOrthogonalenRichtungsvektor(M3,snap,vectorgeringsterAbstand3);
                Vector2D vector41 = getOrthogonalenRichtungsvektor(M4,snap,vectorgeringsterAbstand4);
                Vector2D vector51 = getOrthogonalenRichtungsvektor(M5,snap,vectorgeringsterAbstand5);
                Vector2D vector61 = getOrthogonalenRichtungsvektor(M6,snap,vectorgeringsterAbstand6);

                double abstand1 = logic.getLaenge(home, LM1);
                int vectorRichtung1 = logic.richtungDesVectors(abstand1, currentPos, this.AbstandzumnaechstenPunktLM(logic.punktAddieren(M1, currentPos), snap));
                Vector2D vector12 = vectorgeringsterAbstand1.multiplikation(vectorRichtung1);

                double abstand2 = logic.getLaenge(home, LM2);
                int vectorRichtung2 = logic.richtungDesVectors(abstand2, currentPos, this.AbstandzumnaechstenPunktLM(logic.punktAddieren(M2, currentPos), snap));
                Vector2D vector22 = vectorgeringsterAbstand2.multiplikation(vectorRichtung2);

                double abstand3 = logic.getLaenge(home, LM3);
                int vectorRichtung3 = logic.richtungDesVectors(abstand3, currentPos, this.AbstandzumnaechstenPunktLM(logic.punktAddieren(M3, currentPos), snap));
                Vector2D vector32 = vectorgeringsterAbstand3.multiplikation(vectorRichtung3);

                Vector2D vector42 = getVektorGeringsterAbstand(snapHome.getM1(),snapHome.getM2(), snapHome.getM4(), vectorgeringsterAbstand4);
                Vector2D vector52 = getVektorGeringsterAbstand(snapHome.getM2(),snapHome.getM3(), snapHome.getM5(), vectorgeringsterAbstand5);
                Vector2D vector62 = getVektorGeringsterAbstand(snapHome.getM1(),snapHome.getM3(), snapHome.getM6(), vectorgeringsterAbstand6);

                Vector2D richtungsvektor = logic.addVector(vector11, vector21, vector31, vector41, vector51, vector61);
                Vector2D naeherungsvektor = logic.addVector(vector12, vector22, vector32, vector42, vector52, vector62);
                Vector2D endvektor = logic.vectorenAddieren(naeherungsvektor, richtungsvektor);

                abweichungen += logic.abweichungBestimmen(currentPos, endvektor);
                int[] position = logic.getPosition(currentPos);

                origPoints = Pfeil.getInstance().points(position[0], position[1]); // Punkte zur Erzeugung des Shapes, position[0/1] sind Ausgangspunkt
                Point[] retPoint = new Point[origPoints.length];
                retPoint[0] = origPoints[0];
                for (int k = 1; k < origPoints.length; k++) {
                    retPoint[k] = Pfeil.getInstance().rotatePoint(origPoints[k], origPoints[0], 90 - logic.getWinkel(endvektor));//this.winkelberechnung(home, new Point2D.Double(i,j))); //origPoints[k]: Punkte die rotiert werden, origPoint[0]:Punkt um welchen rotiert wird, angleDeg: Winkel
                }

                if (i != 0 || j != 0) {
                    g2d.setColor(new Color(0, 0, 0));
                    g2d.draw(Pfeil.getInstance().rotateShape(retPoint));  // zeichnet das Shape
                    g2d.fill(Pfeil.getInstance().rotateShape(retPoint));  // fuellt das Shape
                } else { //Kreuz, dass bei 0/0 gezeichnet ist
                    g2d.setColor(new Color(255, 0, 0));
                    g2d.drawLine(position[0] + 13, position[1] + 13, position[0] + 21, position[1] + 21);
                    g2d.drawLine(position[0] + 13, position[1] + 21, position[0] + 21, position[1] + 13);
                }
            }

        }

        System.out.println("Die Abweichung betraegt durchschnittlich " + abweichungen / 224 + " Grad");
    }

    private Vector2D getVektorGeringsterAbstand(Point2D.Double M1, Point2D.Double M2, Point2D.Double M3, Vector2D vectorgeringsterAbstand) {
        if (logic.getLaenge(M1, M2) < logic.vergleichStreckeWeiß(logic.punktAddieren(M3, currentPos), snap)) {
            return vectorgeringsterAbstand.multiplikation(-1);
        } else return vectorgeringsterAbstand;
    }

    private Vector2D getOrthogonalenRichtungsvektor(Point2D.Double M, Snapshot snap, Vector2D vectorgeringsterAbstand) {
        if (logic.getWinkel(logic.richtungsvektor(currentPos, M), logic.richtungsvektor(currentPos, logic.VectorAbstandzumnaechstenPunktSchwarz(M, currentPos, snap))) > 180) {
            return logic.orthogonalerRichtungsvektor(vectorgeringsterAbstand);
        } else return logic.orthogonalerRichtungsvektor(vectorgeringsterAbstand).multiplikation(-1);
    }


    /**
     * Vergleicht den Punkt mit den ersten drei Punkten des Snaps
     *
     * @param p1
     * @param s
     * @return gibt das Landmark zurueck das den gerinsten Abstand zum Punkt hat
     */
    public Point2D.Double AbstandzumnaechstenPunktLM(Point2D.Double p1, Snapshot s) {
        if (s.getM1().equals(logic.kleinsterAbstand(p1, s.getM1(), s.getM2(), s.getM3()))) {
            return LM1;
        }
        if (s.getM2().equals(logic.kleinsterAbstand(p1, s.getM1(), s.getM2(), s.getM3()))) {
            return LM2;
        }
        return LM3;
    }
}
