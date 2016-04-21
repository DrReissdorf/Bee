/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.lang.Math;

/**
 * @author Kristine
 */
public class CanvasGUI extends Canvas {

    private Point[] point1 = new Point[6];
    private Point[] origPoints;
    private Polygon p;
    private double radius = 0.5;
    Logic l;
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
    private Point2D.Double KPHOME;
    private Point2D.Double KP1;
    private Point2D.Double KP2;
    private Point2D.Double KP3;
    private Point2D.Double KP4;
    private Point2D.Double KP5;
    private Point2D.Double KP6;
    private double abweichungen;


    CanvasGUI() {
        l = Logic.getInstance();
        home = new Point2D.Double(0, 0);
        LM1 = new Point2D.Double(3.5, 2);
        LM2 = new Point2D.Double(3.5, -2);
        LM3 = new Point2D.Double(0, -4);
        M1 = new Point2D.Double(l.mittelpunkt(home, LM1).getX(), l.mittelpunkt(home, LM1).getY());
        M2 = new Point2D.Double(l.mittelpunkt(home, LM2).getX(), l.mittelpunkt(home, LM2).getY());
        M3 = new Point2D.Double(l.mittelpunkt(home, LM3).getX(), l.mittelpunkt(home, LM3).getY());
        M4 = l.punktbestimmung(home, M1, M2, M3);
        M5 = l.punktbestimmung(home, M3, M2, M1);
        M6 = l.punktbestimmung(home, M1, M3, M2);
        abweichungen = 0;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int[] landmark1 = l.getPosition(new Point2D.Double(3.5, 2));
        int[] landmark2 = l.getPosition(new Point2D.Double(3.5, -2));
        int[] landmark3 = l.getPosition(new Point2D.Double(0, -4));

        g2d.setColor(new Color(89, 94, 248));
        g2d.fillOval(landmark1[0], landmark1[1], 17 * 2, 17 * 2);
        g2d.fillOval(landmark2[0], landmark2[1], 17 * 2, 17 * 2);
        g2d.fillOval(landmark3[0], landmark3[1], 17 * 2, 17 * 2);

        snapHome = new Snapshot(M1, M2, M3, M4, M5, M6);

        for (int i = -7; i <= 7; i++) {
            for (int j = 7; j >= -7; j--) {
                KPHOME = new Point2D.Double(i, j);
                KP1 = new Point2D.Double(l.mittelpunkt(KPHOME, LM1).getX(), l.mittelpunkt(KPHOME, LM1).getY());
                KP2 = new Point2D.Double(l.mittelpunkt(KPHOME, LM2).getX(), l.mittelpunkt(KPHOME, LM2).getY());
                KP3 = new Point2D.Double(l.mittelpunkt(KPHOME, LM3).getX(), l.mittelpunkt(KPHOME, LM3).getY());
                KP4 = l.punktbestimmung(KPHOME, KP1, KP2, KP3);
                KP5 = l.punktbestimmung(KPHOME, KP2, KP3, KP1);
                KP6 = l.punktbestimmung(KPHOME, KP1, KP3, KP2);
                snap = new Snapshot(KP1, KP2, KP3, KP4, KP5, KP6);

                Vector2D vectorgeringsterAbstand1 = l.VectorAbstandzumnaechstenPunktSchwarz(l.punktAddieren(snapHome.getM1(), KPHOME), KPHOME, snap);
                Vector2D vector11;
                if (l.getWinkel(l.richtungsvektor(KPHOME, M1), l.richtungsvektor(KPHOME, l.VectorAbstandzumnaechstenPunktSchwarz(M1, KPHOME, snap))) > 180) {
                    vector11 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand1);
                } else
                    vector11 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand1).multiplikation(-1);


                Vector2D vectorgeringsterAbstand2 = l.VectorAbstandzumnaechstenPunktSchwarz(l.punktAddieren(snapHome.getM2(), KPHOME), KPHOME, snap);
                Vector2D vector21;
                if (l.getWinkel(l.richtungsvektor(KPHOME, M2), l.richtungsvektor(KPHOME, l.VectorAbstandzumnaechstenPunktSchwarz(M2, KPHOME, snap))) > 180) {
                    vector21 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand2);
                } else
                    vector21 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand2).multiplikation(-1);

                Vector2D vectorgeringsterAbstand3 = l.VectorAbstandzumnaechstenPunktSchwarz(l.punktAddieren(snapHome.getM3(), KPHOME), KPHOME, snap);
                Vector2D vector31;
                if (l.getWinkel(l.richtungsvektor(KPHOME, M3), l.richtungsvektor(KPHOME, l.VectorAbstandzumnaechstenPunktSchwarz(M3, KPHOME, snap))) > 180) {
                    vector31 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand3);
                } else
                    vector31 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand3).multiplikation(-1);

                Vector2D vectorgeringsterAbstand4 = l.VectorAbstandzumnaechstenPunktWeiß(l.punktAddieren(snapHome.getM4(), KPHOME), KPHOME, snap);
                Vector2D vector41;
                if (l.getWinkel(l.richtungsvektor(KPHOME, M4), l.richtungsvektor(KPHOME, l.VectorAbstandzumnaechstenPunktWeiß(M4, KPHOME, snap))) > 180) {
                    vector41 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand4);
                } else
                    vector41 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand4).multiplikation(-1);

                Vector2D vectorgeringsterAbstand5 = l.VectorAbstandzumnaechstenPunktWeiß(l.punktAddieren(snapHome.getM5(), KPHOME), KPHOME, snap);
                Vector2D vector51;
                if (l.getWinkel(l.richtungsvektor(KPHOME, M5), l.richtungsvektor(KPHOME, l.VectorAbstandzumnaechstenPunktWeiß(M5, KPHOME, snap))) > 180) {
                    vector51 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand5);
                } else
                    vector51 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand5).multiplikation(-1);

                Vector2D vectorgeringsterAbstand6 = l.VectorAbstandzumnaechstenPunktWeiß(l.punktAddieren(snapHome.getM6(), KPHOME), KPHOME, snap);
                Vector2D vector61;
                if (l.getWinkel(l.richtungsvektor(KPHOME, M6), l.richtungsvektor(KPHOME, l.VectorAbstandzumnaechstenPunktWeiß(M6, KPHOME, snap))) > 180) {
                    vector61 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand6);
                } else
                    vector61 = l.orthogonalerRichtungsvektor(vectorgeringsterAbstand6).multiplikation(-1);

                double abstand1 = l.getAbstand(home, LM1);
                int vectorRichtung1 = l.richtungDesVectors(abstand1, KPHOME, this.AbstandzumnaechstenPunktLM(l.punktAddieren(M1, KPHOME), snap));
                Vector2D vector12 = vectorgeringsterAbstand1.multiplikation(vectorRichtung1);

                double abstand2 = l.getAbstand(home, LM2);
                int vectorRichtung2 = l.richtungDesVectors(abstand2, KPHOME, this.AbstandzumnaechstenPunktLM(l.punktAddieren(M2, KPHOME), snap));
                Vector2D vector22 = vectorgeringsterAbstand2.multiplikation(vectorRichtung2);

                double abstand3 = l.getAbstand(home, LM3);
                int vectorRichtung3 = l.richtungDesVectors(abstand3, KPHOME, this.AbstandzumnaechstenPunktLM(l.punktAddieren(M3, KPHOME), snap));
                Vector2D vector32 = vectorgeringsterAbstand3.multiplikation(vectorRichtung3);

                Vector2D vector42;
                if (l.getAbstand(snapHome.getM1(), snapHome.getM2()) < l.vergleichStreckeWeiß(l.punktAddieren(snapHome.getM4(), KPHOME), snap)) {
                    vector42 = vectorgeringsterAbstand4.multiplikation(-1);
                } else vector42 = vectorgeringsterAbstand4;


                Vector2D vector52;
                if (l.getAbstand(snapHome.getM2(), snapHome.getM3()) < l.vergleichStreckeWeiß(l.punktAddieren(snapHome.getM5(), KPHOME), snap)) {
                    vector52 = vectorgeringsterAbstand5.multiplikation(-1);
                } else vector52 = vectorgeringsterAbstand5;


                Vector2D vector62;
                if (l.getAbstand(snapHome.getM1(), snapHome.getM3()) < l.vergleichStreckeWeiß(l.punktAddieren(snapHome.getM6(), KPHOME), snap)) {
                    vector62 = vectorgeringsterAbstand6.multiplikation(-1);
                } else vector62 = vectorgeringsterAbstand6;


                Vector2D richtungsvektor = l.addVector(vector11, vector21, vector31, vector41, vector51, vector61);
                Vector2D naeherungsvektor = l.addVector(vector12, vector22, vector32, vector42, vector52, vector62);
                Vector2D endvektor = l.vectorenAddieren(naeherungsvektor, richtungsvektor);
                double winkel = l.getWinkel(endvektor);
                abweichungen += l.abweichungBestimmen(endvektor, KPHOME);
                g2d.setColor(Color.white);
                int[] position = l.getPosition(KPHOME);

                origPoints = Pfeil.getInstance().points(position[0], position[1]); // Punkte zur Erzeugung des Shapes, position[0/1] sind Ausgangspunkt
                Point[] retPoint = new Point[origPoints.length];
                retPoint[0] = origPoints[0];
                for (int k = 1; k < origPoints.length; k++) {
                    retPoint[k] = Pfeil.getInstance().rotatePoint(origPoints[k], origPoints[0], 90 - l.getWinkel(endvektor));//this.winkelberechnung(home, new Point2D.Double(i,j))); //origPoints[k]: Punkte die rotiert werden, origPoint[0]:Punkt um welchen rotiert wird, angleDeg: Winkel

                }


                if (i != 0 || j != 0) {
                    g2d.draw(Pfeil.getInstance().rotateShape(retPoint));  // zeichnet das Shape
                    g2d.fill(Pfeil.getInstance().rotateShape(retPoint));  // fuellt das Shape
                } else { //Kreuz, dass bei 0/0 gezeichnet ist
                    g2d.drawLine(position[0] + 13, position[1] + 13, position[0] + 21, position[1] + 21);
                    g2d.drawLine(position[0] + 13, position[1] + 21, position[0] + 21, position[1] + 13);
                }
            }

        }

        System.out.println("Die Abweichung betraegt durchschnittlich " + abweichungen / 224 + " Grad");
    }


    /**
     * Vergleicht den Punkt mit den ersten drei Punkten des Snaps
     *
     * @param p1
     * @param s
     * @return gibt das Landmark zurueck das den gerinsten Abstand zum Punkt hat
     */
    public Point2D.Double AbstandzumnaechstenPunktLM(Point2D.Double p1, Snapshot s) {
        if (s.getM1().equals(l.kleinsterAbstand(p1, s.getM1(), s.getM2(), s.getM3()))) {
            return LM1;
        }
        if (s.getM2().equals(l.kleinsterAbstand(p1, s.getM1(), s.getM2(), s.getM3()))) {
            return LM2;
        }
        return LM3;
    }
}
