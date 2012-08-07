package pdfdoc.builder;

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;

import static pdfdoc.builder.PdfStatics.*;

/**
 * @author gonka
 *         <p/>
 *         A decorating frame which can be added to a text or a image block.
 */
public class Frame extends XmlNode {

    Frame() {
    }

    public void setColor(Color fillcolor) {
        attributes.put(COLOR, fillcolor.getColorcode());
    }

    public Color getColor() {
        final String attrib = attributes.get(COLOR);
        try {
            return attrib != null ? new Color(attrib) : null;
        } catch (PDFException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setMargin(double margin) {
        attributes.put(MARGIN, String.valueOf(margin));
    }

    public double getMargin() {
        final String attrib = attributes.get(MARGIN);
        return attrib != null ? Double.valueOf(attrib) : 0;
    }

    public void setLineWidth(double linewidth) {
        attributes.put(LINEWIDTH, String.valueOf(linewidth));
    }

    public double getLineWidth() {
        final String attrib = attributes.get(LINEWIDTH);
        return attrib != null ? Double.valueOf(attrib) : 0;
    }

    /**
     * Specifies the shape at the corners of paths that are stroked.
     */
    public void setLineJoin(int linejoin) {
        attributes.put(LINEJOIN, String.valueOf(linejoin));
    }

    public int getLineJoin() {
        final String attrib = attributes.get(LINEJOIN);
        return attrib != null ? Integer.valueOf(attrib) : 0;
    }

    /**
     * Controls the shape at the end of a path with respect to stroking.
     */
    public void setLineCap(int linecap) {
        attributes.put(LINECAP, String.valueOf(linecap));
    }

    public int getLineCap() {
        final String attrib = attributes.get(LINECAP);
        return attrib != null ? Integer.valueOf(attrib) : 0;
    }

    public void setType(String type) {
        if (type != null) {
            attributes.put(TYPE, type);
        }
    }

    public String getType() {
        return attributes.get(TYPE);
    }

    public void decorate(pdflib pdf, Block block) throws PDFlibException {
        final Box coords;
        if (getType() != null && getType().equalsIgnoreCase(FRAME_BOUNDRY)) {
            final Box fitbox = block.getFitBox();
            coords = block.getBoundingBox(pdf, fitbox);
        } else {
            coords = block.getFitBox();
        }
        if (coords != null) {
            pdf.save();
            Color color = getColor();
            if (color != null)
                color.draw(pdf, "fstype=fillstroke");
            if (getLineWidth() != 0)
                pdf.setlinewidth(getLineWidth());
            if (getLineCap() != -1)
                pdf.setlinecap(getLineCap());
            if (getLineJoin() != -1)
                pdf.setlinejoin(getLineJoin());
            pdf.moveto(coords.getLeftX() - getMargin(), coords.getLeftY() - getMargin());
            pdf.lineto(coords.getRightX() + getMargin(), coords.getLeftY() - getMargin());
            pdf.lineto(coords.getRightX() + getMargin(), coords.getRightY() + getMargin());
            pdf.lineto(coords.getLeftX() - getMargin(), coords.getRightY() + getMargin());
            pdf.closepath();
            pdf.stroke();
            pdf.restore();
        }
    }

    public String toXml() {
        return toXml(XMLTAG_FRAME);
    }
}
