package pdfdoc.builder;

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;

import java.util.Properties;

import static pdfdoc.builder.PdfStatics.*;

/**
 * @author gonka
 */
public class Color implements IDrawable {

    final private String colorcode;
    final private String colorspace;
    final private double colorvalues[] = new double[4];
    private String spotcolorid;

    /**
     * @param colorcode String of type:
     *                  <p/>
     *                  Colorspace "gray"   GRAY(100)
     *                  Colorspace "rgb":   RGB(255,255,255)
     *                  Colorspace "cmyk":  CMYK(100,100,100,100)
     *                  Colorspace "lab":   LAB(100,127,127)
     *                  Colorspace "spot":  SPOT(PANTONE 300 U,0) or SPOT(HKS 43 N,0)
     */
    public Color(String colorcode) throws PDFException {
        try {
            this.colorcode = colorcode;
            colorspace = colorcode.substring(0, colorcode.indexOf('(')).trim();
            if (colorspace.equalsIgnoreCase(COLOR_SPACE_SPOT)) {
                spotcolorid = colorcode.substring(colorcode.indexOf('(') + 1, colorcode.indexOf(','));
                String tint = colorcode.substring(colorcode.indexOf(',') + 1, colorcode.indexOf(')'));
                colorvalues[0] = Double.parseDouble(tint);
            } else {
                String values = colorcode.substring(colorcode.indexOf('(') + 1, colorcode.indexOf(')'));
                String[] valuesarray = values.split(",");
                for (int i = 0; i < valuesarray.length; i++) {
                    colorvalues[i] = Double.parseDouble(valuesarray[i]);
                }
            }
        } catch (Exception e) {
            throw new PDFException("Malformed color string : " + colorcode);
        }
    }

    public String getColorcode() {
        return colorcode;
    }

    /**
     * @param 'fstype' One of fill, stroke, or fillstroke to specify that the color is set for filling, stroking,or both.
     * @param params   list of parameters
     * @throws com.pdflib.PDFlibException
     */
    public void draw(pdflib p, String... params) throws PDFlibException {
        final Properties prp = ParametersUtil.toProperties(params);
        final String fstype = prp.getProperty("fstype");
        double c1;
        double c2;
        double c3;
        double c4;
        if (colorspace.equalsIgnoreCase(COLOR_SPACE_CMYK)) {
            c1 = colorvalues[0] / 100f;
            c2 = colorvalues[1] / 100f;
            c3 = colorvalues[2] / 100f;
            c4 = colorvalues[3] / 100f;
            p.setcolor(fstype, colorspace, c1, c2, c3, c4);
        } else if (colorspace.equalsIgnoreCase(COLOR_SPACE_RGB)) {
            c1 = colorvalues[0] / 255f;
            c2 = colorvalues[1] / 255f;
            c3 = colorvalues[2] / 255f;
            p.setcolor(fstype, colorspace, c1, c2, c3, 0);
        } else if (colorspace.equalsIgnoreCase(COLOR_SPACE_LAB)) {
            c1 = colorvalues[0];
            c2 = colorvalues[1];
            c3 = colorvalues[2];
            p.setcolor(fstype, colorspace, c1, c2, c3, 0);
        } else if (colorspace.equalsIgnoreCase(COLOR_SPACE_GRAY)) {
            c1 = colorvalues[0] / 100f;
            p.setcolor(fstype, colorspace, c1, 0, 0, 0);
        } else if (colorspace.equalsIgnoreCase(COLOR_SPACE_SPOT)) {
            double spotcolor = p.makespotcolor(spotcolorid);
            c1 = colorvalues[0] / 100;
            p.setcolor(fstype, colorspace, spotcolor, c1, 0, 0);
        }
    }

    public String getOptionlist() {
        final StringBuffer sb = new StringBuffer();
        sb.append("{");
        if (colorspace.equalsIgnoreCase(COLOR_SPACE_CMYK)) {
            sb.append(colorspace);
            sb.append(" ");
            sb.append(colorvalues[0] / 100f);
            sb.append(" ");
            sb.append(colorvalues[1] / 100f);
            sb.append(" ");
            sb.append(colorvalues[2] / 100f);
            sb.append(" ");
            sb.append(colorvalues[3] / 100f);
        } else if (colorspace.equalsIgnoreCase(COLOR_SPACE_RGB)) {
            sb.append(colorspace);
            sb.append(" ");
            sb.append(colorvalues[0] / 255f);
            sb.append(" ");
            sb.append(colorvalues[1] / 255f);
            sb.append(" ");
            sb.append(colorvalues[2] / 255f);
        } else if (colorspace.equalsIgnoreCase(COLOR_SPACE_LAB)) {
            sb.append(colorspace);
            sb.append(" ");
            sb.append(colorvalues[0]);
            sb.append(" ");
            sb.append(colorvalues[1]);
            sb.append(" ");
            sb.append(colorvalues[2]);
        } else if (colorspace.equalsIgnoreCase(COLOR_SPACE_GRAY)) {
            sb.append(colorspace);
            sb.append(" ");
            sb.append(colorvalues[0] / 100f);
        } else if (colorspace.equalsIgnoreCase(COLOR_SPACE_SPOT)) {
            sb.append("spotname {");
            sb.append(spotcolorid);
            sb.append("} ");
            sb.append(colorvalues[0] / 100f);
        }
        sb.append("}");
        return sb.toString();
    }

}
