package pdfdoc.builder;

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;

import static pdfdoc.builder.PdfStatics.*;

/**
 * @author gonka
 */
public class ImageBlock extends Block implements IDrawable {

    private String path;

    ImageBlock() {
    }

    public void setPath(String imagepath) {
        path = imagepath;
    }

    public String getPath() {
        return path;
    }

    /**
     * nofit: Position the object only, without any scaling or clipping.
     * clip: Position the object, and clip it at the edges of the box.
     * meet: Position the object according to the position option, and scale it so that it entirely fits into the box while preserving its aspect ratio.
     * slice: Position the object according to the position option, and scale it such that it entirely covers the box,
     * while preserving the aspect ratio and making sure that at least one dimension of the object is fully contained in the box
     * entire: Position the object according to the position option, and scale it such that it entirely covers the box.
     * <p/>
     * scale: scale image to setScaling() and clip it at the edges of the box.
     * custom: crop image to setClip() and position the result as meet.
     */
    public void setFitMethod(String method) {
        if (method != null) {
            attributes.put(FITMETHOD, method);
        }
    }

    public String getFitMethod() {
        return attributes.get(FITMETHOD);
    }

    /**
     * The keywords bottom, center, top.
     *
     * @param method aligment method
     */
    public void setVerticalAlignment(String method) {
        if (method != null) {
            attributes.put(VERTICALALIGNMENT, method);
        }
    }

    public String getVerticalAlignment() {
        return attributes.get(VERTICALALIGNMENT);
    }

    /**
     * The keywords left, center, right.
     *
     * @param method aligment method
     */
    public void setHorizontalAlignment(String method) {
        if (method != null) {
            attributes.put(ALIGNMENT, method);
        }
    }

    public String getHorizontalAlignment() {
        return attributes.get(ALIGNMENT);
    }

    /**
     * @param scale in percentage 0-100
     */
    public void setScaling(double scale) {
        attributes.put(SCALE, String.valueOf(scale / 100));
    }

    public Double getScaling() {
        final String scale = attributes.get(SCALE);
        return scale != null ? new Double(scale) : null;
    }

    /**
     * Values in pixels
     */
    public void setClip(double width, double height, double leftx, double lefty, double rightx, double righty) {
        double lx = (100.0 * leftx) / width;
        double ly = (100.0 * lefty) / height;
        double rx = (100.0 * rightx) / width;
        double ry = (100.0 * righty) / height;
        setClip(lx, ly, rx, ry);
    }

    /**
     * Values in porcentage
     */
    public void setClip(double leftx, double lefty, double rightx, double righty) {
        //convert to bottom top
        double YA = 100.0 - righty;
        double YB = 100.0 - lefty;
        attributes.put(CLIPPING, leftx + "% " + YA + "% " + rightx + "% " + YB + "%");
    }

    public String getClip() {
        return attributes.get(CLIPPING);
    }

    public Box getBoundingBox(pdflib pdf, Box fitbox) throws PDFlibException {
        //temporary pdf used to get the coordinates only
        if (path != null && path.length() > 0) {
            final pdflib pdftemp = new pdflib();
            pdftemp.begin_document("", "");
            pdftemp.set_parameter("topdown", "true");
            double bleed = getPage().getBleed();
            pdftemp.begin_page_ext(getPage().getWidth() + 2 * bleed, getHeight() + 2 * bleed, "");
            if (bleed > 0.0f) {
                pdftemp.translate(bleed, bleed);
            }
            if (path.endsWith(".pdf")) {
                int pdfdoc = pdftemp.open_pdi_document(path, "");
                if (assertPdfElement(pdfdoc, pdftemp)) {
                    int pdfelement = pdftemp.open_pdi_page(pdfdoc, 1, "");
                    pdftemp.fit_pdi_page(pdfelement, fitbox.getLeftX(), fitbox.getRightY(), getFitOptionsList(fitbox) + " matchbox={name=coordinates} blind=false");
                    pdftemp.close_pdi_page(pdfelement);
                    pdftemp.close_pdi_document(pdfdoc);
                }
            } else {
                int image = pdftemp.load_image("auto", path, "");
                if (assertPdfElement(image, pdf)) {
                    pdftemp.fit_image(image, fitbox.getLeftX(), fitbox.getRightY(), getFitOptionsList(fitbox) + " matchbox={name=coordinates} blind=false");
                    pdftemp.close_image(image);
                }
            }
            double lx = pdftemp.info_matchbox("coordinates", 1, "x4");
            double ly = pdftemp.info_matchbox("coordinates", 1, "y4");
            double rx = pdftemp.info_matchbox("coordinates", 1, "x2");
            double ry = pdftemp.info_matchbox("coordinates", 1, "y2");
            pdftemp.end_page_ext("");
            pdftemp.end_document("");
            pdftemp.delete();
            return new Box(0, 0, lx, ly, rx, ry);
        } else {
            return null;
        }
    }

    public void drawContent(pdflib pdf, Box fitbox) throws PDFlibException {
        if (path != null && path.length() > 0) {
            if (path.endsWith(".pdf")) {
                int pdfdoc = pdf.open_pdi_document(path, "errorpolicy=return");
                if (assertPdfElement(pdfdoc, pdf)) {
                    int pdfelement = pdf.open_pdi_page(pdfdoc, 1, "");
                    pdf.fit_pdi_page(pdfelement, fitbox.getLeftX(), fitbox.getRightY(), getFitOptionsList(fitbox));
                    pdf.close_pdi_page(pdfelement);
                    pdf.close_pdi_document(pdfdoc);
                }
            } else {
                int image = pdf.load_image("auto", path, "");
                if (assertPdfElement(image, pdf)) {
                    pdf.fit_image(image, fitbox.getLeftX(), fitbox.getRightY(), getFitOptionsList(fitbox));
                    pdf.close_image(image);
                }
            }
        }
    }

    private String getFitOptionsList(Box fitbox) throws PDFlibException {
        final StringBuilder optionslist = new StringBuilder();
        final String optionattributes[] = new String[]{ORIENTATE};
        optionslist.append(ParametersUtil.toOptionsList(attributes, optionattributes));
        //box size
        double boxw = fitbox.getRightX() - fitbox.getLeftX();
        double boxh = fitbox.getRightY() - fitbox.getLeftY();
        optionslist.append(" boxsize={").append(boxw).append(" ").append(boxh).append("}");
        //position
        final String posh = getHorizontalAlignment() != null ? getHorizontalAlignment() : LEFT;
        final String posv = getVerticalAlignment() != null ? getVerticalAlignment() : TOP;
        optionslist.append(" position={").append(posh).append(" ").append(posv).append("} ");
        //fit method
        if (getFitMethod() != null && getFitMethod().equalsIgnoreCase(FIT_METHOD_SCALE)) {
            optionslist.append(" scale={").append(getScaling()).append("}");
            optionslist.append(" fitmethod=clip");
        } else if (getFitMethod() != null && getFitMethod().equalsIgnoreCase(FIT_METHOD_CUSTOM) && getClip() != null) {
            optionslist.append(" matchbox={clipping={").append(getClip()).append("} }");
            optionslist.append(" fitmethod=meet");
        } else {
            final String fitmethodattribute[] = new String[]{FITMETHOD};
            optionslist.append(ParametersUtil.toOptionsList(attributes, fitmethodattribute));
        }
        return optionslist.toString();
    }

    public String toXml() {
        final StringBuilder xmlstring = new StringBuilder();
        final String nodename = XMLTAG_IMAGE;
        xmlstring.append("\n");
        xmlstring.append("<");
        xmlstring.append(nodename);
        appendAttributes(attributes, xmlstring);
        xmlstring.append(">");
        xmlstring.append("\n");
        xmlstring.append("<");
        xmlstring.append(XMLTAG_CONTENT);
        xmlstring.append(">");
        if (path != null) {
            xmlstring.append(scapexml(path));
        }
        xmlstring.append("</");
        xmlstring.append(XMLTAG_CONTENT);
        xmlstring.append(">");
        if (children.size() > 0) {
            XmlNode children[] = this.children.toArray(new XmlNode[]{});
            for (XmlNode aChildren : children) {
                xmlstring.append(aChildren.toXml());
            }
        }
        xmlstring.append("\n");
        xmlstring.append("</");
        xmlstring.append(nodename);
        xmlstring.append(">");
        return xmlstring.toString();
    }
}
