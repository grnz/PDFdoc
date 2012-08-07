package pdfdoc.builder;

/**
 * @author gonka
 *         Represents the dimensions of a content box.
 */
public class Box {

    private final double tx;
    private final double ty;
    private final double leftx;
    private final double lefty;
    private final double rightx;
    private final double righty;

    public Box(double tx, double ty, double lx, double ly, double rx, double ry) {
        this.tx = tx;
        this.ty = ty;
        this.leftx = lx;
        this.lefty = ly;
        this.rightx = rx;
        this.righty = ry;
    }

    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }

    public double getLeftX() {
        return leftx;
    }

    public double getLeftY() {
        return lefty;
    }

    public double getRightX() {
        return rightx;
    }

    public double getRightY() {
        return righty;
    }

    public String toString() {
        return tx + " " + ty + " : " + leftx + ", " + lefty + ", " + rightx + ", " + righty;
    }
}
