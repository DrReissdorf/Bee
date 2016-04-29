import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.System.in;

public class Drawer extends java.awt.Canvas {
    private final Color arrowColor = new Color(255, 255, 255);
    private final Color centerColor = new Color(255,0,0);
    private final Color landmarkColor = new Color(102, 152, 248);

    private Point[] origPoints;
    private Punkt home;
    private Punkt LM1;
    private Punkt LM2;
    private Punkt LM3;
    private Punkt M1;
    private Punkt M2;
    private Punkt M3;
    private Punkt M4;
    private Punkt M5;
    private Punkt M6;

    private double abweichungen;
    private int numberOfArrows = -1;

    public Drawer() {
        home = new Punkt(0, 0);
        LM1 = new Punkt(3.5, 2);
        LM2 = new Punkt(3.5, -2);
        LM3 = new Punkt(0.3, -4);

        getInput();

        Punkt mittelPunktTemporary = Operations.kreisSchnittpunkt(home, LM1);
        M1 = new Punkt(mittelPunktTemporary.getX(),mittelPunktTemporary.getY());

        mittelPunktTemporary = Operations.kreisSchnittpunkt(home, LM2);
        M2 = new Punkt(mittelPunktTemporary.getX(),mittelPunktTemporary.getY());

        mittelPunktTemporary = Operations.kreisSchnittpunkt(home, LM3);
        M3 = new Punkt(mittelPunktTemporary.getX(),mittelPunktTemporary.getY());

        M4 = Operations.kreisZwischenSchnittpunkte(home, M1, M2);
        M5 = Operations.kreisZwischenSchnittpunkte(home, M3, M2);
        M6 = Operations.kreisZwischenSchnittpunkte(home, M1, M3);

        abweichungen = 0;
    }

    private void getInput() {
        String lm1_x;
        String lm1_y;
        String lm2_x;
        String lm2_y;
        String lm3_x;
        String lm3_y;

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

            LM1 = new Punkt(Double.valueOf(lm1_x), Double.valueOf(lm1_y));
            LM2 = new Punkt(Double.valueOf(lm2_x), Double.valueOf(lm2_y));
            LM3 = new Punkt(Double.valueOf(lm3_x), Double.valueOf(lm3_y));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int[] landmark1 = Operations.getRealPosition(LM1);
        int[] landmark2 = Operations.getRealPosition(LM2);
        int[] landmark3 = Operations.getRealPosition(LM3);

        int[] realPosition;

        g2d.setColor(landmarkColor);
        g2d.fillOval(landmark1[0], landmark1[1], 17 * 2, 17 * 2);
        g2d.fillOval(landmark2[0], landmark2[1], 17 * 2, 17 * 2);
        g2d.fillOval(landmark3[0], landmark3[1], 17 * 2, 17 * 2);

        Punkt currentPos;
        Punkt KP1;
        Punkt KP2;
        Punkt KP3;
        Punkt KP4;
        Punkt KP5;
        Punkt KP6;

        for (int i = -7; i <= 7; i++) {
            for (int j = 7; j >= -7; j--) {
                numberOfArrows++;
                currentPos = new Punkt(i, j);

                /********* SNAPSHOT SCHNITTPUNKTE BESTIMMEN **************/
                Punkt mittelPunktTemporary = Operations.kreisSchnittpunkt(currentPos, LM1);
                KP1 = new Punkt(mittelPunktTemporary.getX(), mittelPunktTemporary.getY());

                mittelPunktTemporary = Operations.kreisSchnittpunkt(currentPos, LM2);
                KP2 = new Punkt(mittelPunktTemporary.getX(), mittelPunktTemporary.getY());

                mittelPunktTemporary = Operations.kreisSchnittpunkt(currentPos, LM3);
                KP3 = new Punkt(mittelPunktTemporary.getX(), mittelPunktTemporary.getY());

                KP4 = Operations.kreisZwischenSchnittpunkte(currentPos, KP1, KP2);
                KP5 = Operations.kreisZwischenSchnittpunkte(currentPos, KP2, KP3);
                KP6 = Operations.kreisZwischenSchnittpunkte(currentPos, KP1, KP3);
                /***********************************************************/

                Vector2D endVektor = calculateEndVector(currentPos,KP1,KP2,KP3,KP4,KP5,KP6);
                abweichungen += Operations.abweichungBestimmen(currentPos, endVektor);
                realPosition = Operations.getRealPosition(currentPos);

                origPoints = Pfeil.getInstance().drawPoints(realPosition[0], realPosition[1]); // Punkte zur Erzeugung des Shapes, position[0/1] sind Ausgangspunkt
                Point[] retPoint = new Point[origPoints.length];
                retPoint[0] = origPoints[0];
                for (int k = 1; k < origPoints.length; k++) {
                    retPoint[k] = Pfeil.getInstance().rotatePoint(origPoints[k], origPoints[0], 90 - Operations.getWinkel(endVektor));
                }

                if (i != 0 || j != 0) {
                    g2d.setColor(arrowColor);
                    g2d.draw(Pfeil.getInstance().rotateShape(retPoint));
                    g2d.fill(Pfeil.getInstance().rotateShape(retPoint));
                } else { //Kreuz, das bei 0/0 gezeichnet ist
                    g2d.setColor(centerColor);
                    g2d.drawLine(realPosition[0] + 13, realPosition[1] + 13, realPosition[0] + 21, realPosition[1] + 21);
                    g2d.drawLine(realPosition[0] + 13, realPosition[1] + 21, realPosition[0] + 21, realPosition[1] + 13);
                }
            }
        }

        System.out.println("Die Abweichung betraegt durchschnittlich " + abweichungen / numberOfArrows + " Grad"+" ");
    }

    private Vector2D calculateEndVector(Punkt currentPos, Punkt KP1, Punkt KP2, Punkt KP3, Punkt KP4, Punkt KP5, Punkt KP6) {
        Punkt geringsterAbstandSchnittPunkt1 = Operations.geringsterAbstandPunkt(M1,KP1,KP2,KP3);
        Punkt geringsterAbstandSchnittPunkt2 = Operations.geringsterAbstandPunkt(M2,KP1,KP2,KP3);
        Punkt geringsterAbstandSchnittPunkt3 = Operations.geringsterAbstandPunkt(M3,KP1,KP2,KP3);
        Punkt geringsterAbstandSchnittPunkt4 = Operations.geringsterAbstandPunkt(M4,KP4,KP5,KP6);
        Punkt geringsterAbstandSchnittPunkt5 = Operations.geringsterAbstandPunkt(M5,KP4,KP5,KP6);
        Punkt geringsterAbstandSchnittPunkt6 = Operations.geringsterAbstandPunkt(M6,KP4,KP5,KP6);

        Vector2D turnVector1 = Operations.berechneTurnVector(currentPos,M1,geringsterAbstandSchnittPunkt1);
        Vector2D turnVector2 = Operations.berechneTurnVector(currentPos,M2,geringsterAbstandSchnittPunkt2);
        Vector2D turnVector3 = Operations.berechneTurnVector(currentPos,M3,geringsterAbstandSchnittPunkt3);
        Vector2D turnVector4 = Operations.berechneTurnVector(currentPos,M4,geringsterAbstandSchnittPunkt4);
        Vector2D turnVector5 = Operations.berechneTurnVector(currentPos,M5,geringsterAbstandSchnittPunkt5);
        Vector2D turnVector6 = Operations.berechneTurnVector(currentPos,M6,geringsterAbstandSchnittPunkt6).multiplikation(-1);
        Vector2D allTurnVectors = turnVector1.add(turnVector2).add(turnVector3).add(turnVector4).add(turnVector5).add(turnVector6);

        Vector2D positionVector1 = Operations.berechnePositionVector(currentPos,geringsterAbstandSchnittPunkt1);
        Vector2D positionVector2 = Operations.berechnePositionVector(currentPos,geringsterAbstandSchnittPunkt2);
        Vector2D positionVector3 = Operations.berechnePositionVector(currentPos,geringsterAbstandSchnittPunkt3);
        Vector2D positionVector4 = Operations.berechnePositionVector(currentPos,geringsterAbstandSchnittPunkt4);
        Vector2D positionVector5 = Operations.berechnePositionVector(currentPos,geringsterAbstandSchnittPunkt5);
        Vector2D positionVector6 = Operations.berechnePositionVector(currentPos,geringsterAbstandSchnittPunkt6);
        Vector2D allPositionsVector = positionVector1.add(positionVector2).add(positionVector3).add(positionVector4).add(positionVector5).add(positionVector6);

        return allPositionsVector.multiplikation(3).add(allTurnVectors);
    }
}
