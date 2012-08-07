package pdfdoc.builder;

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;

import java.util.ArrayList;

import static pdfdoc.builder.PdfStatics.*;

/**
 * @author gonka
 */
public class Page extends XmlNode implements IDrawable {

    Page() {
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
     * Set bleed value to applied to this document. Value in points.
     */
    public void setBleed(double bleed) {
        attributes.put(BLEED, Double.toString(bleed));
    }

    public double getBleed() {
        final String attrib = attributes.get(BLEED);
        return attrib != null ? Double.valueOf(attrib) : 0;
    }

    public TextBlock createTextBlock() {
        final TextBlock textblock = new TextBlock();
        textblock.setPage(this);
        children.add(textblock);
        return textblock;
    }

    public ImageBlock createImageBlock() {
        final ImageBlock imageblock = new ImageBlock();
        imageblock.setPage(this);
        children.add(imageblock);
        return imageblock;
    }

    public String toXml() {
        return toXml(XMLTAG_PAGE);
    }

    private String getOptionsList() {
        if (getBleed() > 0) {
            return "bleedbox {" + getBleed() + " " + getBleed() + " " + (getWidth() + getBleed()) + " " + (getHeight() + getBleed()) + "}";
        } else {
            return "";
        }
    }

    public void draw(pdflib pdf, String... params) throws PDFlibException {
        final double bleed = getBleed();
        pdf.begin_page_ext(getWidth() + 2 * bleed, getHeight() + 2 * bleed, getOptionsList());
        pdf.save();
        if (bleed > 0.0f) {
            pdf.translate(bleed, bleed);
        }
        for (XmlNode blocknode : children) {
            Block block = (Block) blocknode;
            block.draw(pdf);
        }
        pdf.restore();
        pdf.end_page_ext("");
    }

    public ArrayList<XmlNode> getBlocks() {
        return children;
    }

}
