import java.awt.geom.Point2D;

public class Snapshot {
    private Point2D.Double m1, m2, m3, m4, m5, m6;

    /**
     * @param m1 Mittelpunkt der ersten schwarzen Flaeche
     * @param m2 Mittelpunkt der zweiten schwarzen Flaeche
     * @param m3 Mittelpunkt der dritten schwarzen Flaeche
     * @param m4 Mittelpunkt der ersten gruenen Flaeche
     * @param m5 Mittelpunkt der zweiten gruenen Flaeche
     * @param m6 Mittelpunkt der dritten gruenen Flaeche
     */
    public Snapshot(Point2D.Double m1, Point2D.Double m2, Point2D.Double m3, Point2D.Double m4, Point2D.Double m5, Point2D.Double m6) {
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.m5 = m5;
        this.m6 = m6;
    }

    public Point2D.Double getM1() {
        return m1;
    }

    public void setM1(Point2D.Double m1) {
        this.m1 = m1;
    }

    public Point2D.Double getM2() {
        return m2;
    }

    public void setM2(Point2D.Double m2) {
        this.m2 = m2;
    }

    public Point2D.Double getM3() {
        return m3;
    }

    public void setM3(Point2D.Double m3) {
        this.m3 = m3;
    }

    public Point2D.Double getM4() {
        return m4;
    }

    public void setM4(Point2D.Double m4) {
        this.m4 = m4;
    }

    public Point2D.Double getM5() {
        return m5;
    }

    public void setM5(Point2D.Double m5) {
        this.m5 = m5;
    }

    public Point2D.Double getM6() {
        return m6;
    }

    public void setM6(Point2D.Double m6) {
        this.m6 = m6;
    }


}
