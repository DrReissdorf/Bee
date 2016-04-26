import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Pfeil {
    private static Pfeil instance;

    private Pfeil() {}

    public static Pfeil getInstance() {
        if (instance == null) {
            instance = new Pfeil();
        }
        return instance;
    }

    /**
     * @param x
     * @param y
     * @return PointArray mit allen Punkten des Polygons
     */
    public Point[] points(int x, int y) {
        Point[] originalpoint = new Point[6];
        originalpoint[0] = new Point(x + 17, y + 22); // Zentrum
        originalpoint[1] = new Point(x + 17, y + 13);
        originalpoint[2] = new Point(x + 14, y + 13);
        originalpoint[3] = new Point(x + 17, y + 8);
        originalpoint[4] = new Point(x + 20, y + 13);
        originalpoint[5] = new Point(x + 17, y + 13);

        return originalpoint;
    }

    /**
     * @param polyPoints Punkte des Polygons
     * @return erstelltes Polygon
     */
    public Polygon polygonize(Point[] polyPoints) {
        Polygon tempPoly = new Polygon();

        for (int i = 0; i < polyPoints.length; i++) {
            tempPoly.addPoint(polyPoints[i].x, polyPoints[i].y);
        }
        return tempPoly;
    }


    public Point rotatePoint(Point pt, Point center, double angleDeg) {
        double angleRad = ((angleDeg / 180) * Math.PI);
        double cosAngle = Math.cos(angleRad);
        double sinAngle = Math.sin(angleRad);
        double dx = (pt.x - center.x);
        double dy = (pt.y - center.y);

        pt.x = center.x + (int) (dx * cosAngle - dy * sinAngle);
        pt.y = center.y + (int) (dx * sinAngle + dy * cosAngle);
        return pt;
    }

    public Shape rotateShape(Point[] rotPoints) {
        AffineTransform at = new AffineTransform();
        Polygon p = this.polygonize(rotPoints);
        return at.createTransformedShape(p);
    }
}
