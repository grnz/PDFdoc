package pdfdoc.builder;

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;

import static pdfdoc.builder.PdfStatics.*;

/**
 * @author gonka
 *         <p/>
 *         A backround color can be added to a text or a image block.
 */
public class Background extends XmlNode {

    Background() {
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
        if (getType() != null && getType().equalsIgnoreCase(BACKGROUND_BOUNDRY)) {
            final Box fitbox = block.getFitBox();
            coords = block.getBoundingBox(pdf, fitbox);
        } else {
            coords = block.getFitBox();
        }
        if (coords != null) {
            pdf.save();
            Color color = getColor();
            if (color != null) {
                color.draw(pdf, "fstype=fillstroke");
            }
            int margin = 0;
            if (getLineJoin() != -1) {
                pdf.setlinejoin(getLineJoin());
                margin = -8;
                pdf.setlinewidth(-2 * margin);
                pdf.moveto(coords.getLeftX() - margin, coords.getLeftY() - margin);
                pdf.lineto(coords.getRightX() + margin, coords.getLeftY() - margin);
                pdf.lineto(coords.getRightX() + margin, coords.getRightY() + margin);
                pdf.lineto(coords.getLeftX() - margin, coords.getRightY() + margin);
                pdf.closepath();
                pdf.stroke();
            }
            pdf.moveto(coords.getLeftX() - margin, coords.getLeftY() - margin);
            pdf.lineto(coords.getRightX() + margin, coords.getLeftY() - margin);
            pdf.lineto(coords.getRightX() + margin, coords.getRightY() + margin);
            pdf.lineto(coords.getLeftX() - margin, coords.getRightY() + margin);
            pdf.closepath();
            pdf.fill();
            pdf.restore();
        }
    }

    public String toXml() {
        return toXml(XMLTAG_BACKGROUND);
    }
}
