import java.awt.geom.Point2D;

public class Snapshot {
    private Punkt m1, m2, m3, m4, m5, m6;

    /**
     * @param m1 Mittelpunkt der ersten schwarzen Flaeche
     * @param m2 Mittelpunkt der zweiten schwarzen Flaeche
     * @param m3 Mittelpunkt der dritten schwarzen Flaeche
     * @param m4 Mittelpunkt der ersten gruenen Flaeche
     * @param m5 Mittelpunkt der zweiten gruenen Flaeche
     * @param m6 Mittelpunkt der dritten gruenen Flaeche
     */
    public Snapshot(Punkt m1, Punkt m2, Punkt m3, Punkt m4, Punkt m5, Punkt m6) {
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.m5 = m5;
        this.m6 = m6;
    }

    public Punkt getM1() {
        return m1;
    }

    public Punkt getM2() {
        return m2;
    }

    public Punkt getM3() {
        return m3;
    }

    public Punkt getM4() {
        return m4;
    }

    public Punkt getM5() {
        return m5;
    }

    public Punkt getM6() {
        return m6;
    }
}
