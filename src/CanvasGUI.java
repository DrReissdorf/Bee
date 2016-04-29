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
    private Punkt currentPos;
    private Punkt KP1;
    private Punkt KP2;
    private Punkt KP3;
    private Punkt KP4;
    private Punkt KP5;
    private Punkt KP6;
    private double abweichungen;
    private int numberOfArrows = -1;

    private String lm1_x;
    private String lm1_y;
    private String lm2_x;
    private String lm2_y;
    private String lm3_x;
    private String lm3_y;

    CanvasGUI() {
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

        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int[] landmark1 = Operations.getPosition(LM1);
        int[] landmark2 = Operations.getPosition(LM2);
        int[] landmark3 = Operations.getPosition(LM3);

        g2d.setColor(new Color(102, 152, 248));
        g2d.fillOval(landmark1[0], landmark1[1], 17 * 2, 17 * 2);
        g2d.fillOval(landmark2[0], landmark2[1], 17 * 2, 17 * 2);
        g2d.fillOval(landmark3[0], landmark3[1], 17 * 2, 17 * 2);

        for (int i = -7; i <= 7; i++) {
            for (int j = 7; j >= -7; j--) {
                numberOfArrows ++;
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
                Vector2D endVektor = allPositionsVector.multiplikation(3).add(allTurnVectors);

                abweichungen += Operations.abweichungBestimmen(currentPos, endVektor);
                int[] position = Operations.getPosition(currentPos);

                origPoints = Pfeil.getInstance().points(position[0], position[1]); // Punkte zur Erzeugung des Shapes, position[0/1] sind Ausgangspunkt
                Point[] retPoint = new Point[origPoints.length];
                retPoint[0] = origPoints[0];
                for (int k = 1; k < origPoints.length; k++) {
                    retPoint[k] = Pfeil.getInstance().rotatePoint(origPoints[k], origPoints[0], 90 - Operations.getWinkel(endVektor));//this.winkelberechnung(home, new Point2D.Double(i,j))); //origPoints[k]: Punkte die rotiert werden, origPoint[0]:Punkt um welchen rotiert wird, angleDeg: Winkel
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

        System.out.println("Die Abweichung betraegt durchschnittlich " + abweichungen / numberOfArrows + " Grad"+" ");
    }
}
