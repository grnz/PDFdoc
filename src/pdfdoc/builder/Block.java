package pdfdoc.builder;

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;

import java.util.ArrayList;

import static pdfdoc.builder.PdfStatics.*;

/**
 * @author gonka
 */
public abstract class Block extends XmlNode implements IDrawable {

    private Page page;
    private final ArrayList<String> errormessages = new ArrayList<String>();

    public Block() {
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Frame createFrame() {
        final Frame frame = new Frame();
        try {
            frame.setColor(new Color("cmyk(0,0,0,100)"));
        } catch (PDFException e) {
            e.printStackTrace();
        }
        children.add(frame);
        return frame;
    }

    public Background createBackground() {
        final Background background = new Background();
        try {
            background.setColor(new Color("cmyk(40,40,40,0)"));
        } catch (PDFException e) {
            e.printStackTrace();
        }
        children.add(background);
        return background;
    }

    /**
     * @param x left upper corner coordinate.
     */
    public void setX(double x) {
        attributes.put(POS_X, Double.toString(x));
    }

    public double getX() {
        final String attrib = attributes.get(POS_X);
        return attrib != null ? Double.valueOf(attrib) : 0;
    }

    /**
     * @param x left upper corner coordinate.
     */
    public void setY(double x) {
        attributes.put(POS_Y, Double.toString(x));
    }

    public double getY() {
        final String attrib = attributes.get(POS_Y);
        return attrib != null ? Double.valueOf(attrib) : 0;
    }

    public void setWidth(double width) {
        attributes.put(WIDTH, Double.toString(width));
    }

    public double getWidth() {
        final String attrib = attributes.get(WIDTH);
        return attrib != null ? Double.valueOf(attrib) : 0;
    }

    public void setHeight(double height) {
        attributes.put(HEIGHT, Double.toString(height));
    }

    public double getHeight() {
        final String attrib = attributes.get(HEIGHT);
        return attrib != null ? Double.valueOf(attrib) : 0;
    }

    /**
     * Set orientation of the textblock. north is normal left-right, east is
     * top-down, south is right-left-upside-down and west is down-up.
     *
     * @param orientation String of the following type: north, east, south, west.
     */
    public void setOrientation(String orientation) {
        if (orientation != null) {
            attributes.put(ORIENTATE, orientation);
        }
    }

    public String getOrientation() {
        return attributes.get(ORIENTATE);
    }

    public void setRotation(double rotation) {
        attributes.put(ROTATION, Double.toString(rotation));
    }

    public double getRotation() {
        final String attrib = attributes.get(ROTATION);
        return attrib != null ? Double.valueOf(attrib) : 0;
    }

    public void setRotationOffset_X(double rotationoffsetx) {
        attributes.put(OFFSETX, Double.toString(rotationoffsetx));
    }

    public double getRotationOffset_X() {
        final String attrib = attributes.get(OFFSETX);
        return attrib != null ? Double.valueOf(attrib) : 0;
    }

    public void setRotationOffset_Y(double rotationoffsety) {
        attributes.put(OFFSETY, Double.toString(rotationoffsety));
    }

    public double getRotationOffset_Y() {
        final String attrib = attributes.get(OFFSETY);
        return attrib != null ? Double.valueOf(attrib) : 0;
    }

    public abstract void drawContent(pdflib pdf, Box fitbox) throws PDFlibException;

    public abstract Box getBoundingBox(pdflib pdf, Box fitbox) throws PDFlibException;

    public Box getFitBox() {
        double tx = 0;
        double ty = 0;
        double llx = getX();
        double lly = getY();
        double urx = getX() + getWidth();
        double ury = getY() + getHeight();
        if (getRotation() != 0.0f) {
            tx = getX() + getRotationOffset_X();
            ty = getY() + getRotationOffset_Y();
            llx = getX() - tx;
            lly = getY() - ty;
            urx = llx + getWidth();
            ury = lly + getHeight();
        }
        return new Box(tx, ty, llx, lly, urx, ury);
    }

    public void draw(pdflib pdf, String... params) throws PDFlibException {
        pdf.save();
        final Box fitboxcoords = getFitBox();
        if (getRotation() != 0.0f) {
            pdf.translate(fitboxcoords.getTx(), fitboxcoords.getTy());
            pdf.rotate(getRotation());
        }
        pdf.set_parameter("errorpolicy", "return");
        //background
        for (XmlNode blocknode : children) {
            if (blocknode instanceof Background) {
                Background background = (Background) blocknode;
                background.decorate(pdf, this);
            }
        }
        //content
        drawContent(pdf, fitboxcoords);
        //frame
        for (XmlNode blocknode : children) {
            if (blocknode instanceof Frame) {
                Frame frame = (Frame) blocknode;
                frame.decorate(pdf, this);
            }
        }
        pdf.set_parameter("errorpolicy", "exception");
        pdf.restore();
    }

    protected boolean assertPdfElement(int pdfelement, pdflib pdf) {
        if (pdfelement == -1) {
            this.errormessages.add(pdf.get_errmsg());
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<String> getErrorMessages() {
        return errormessages;
    }
}
