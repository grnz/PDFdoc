package pdfdoc.builder;

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;

/**
 * @author gonka
 */
public class PDFlibInstancePool {

    private PDFlibInstancePool() {
    }

    public static pdflib getInstance() throws PDFlibException {
        return createNew();
    }

    public static pdflib getInstance(String instanceid) throws PDFlibException {
        return createNew();
    }

    private static pdflib createNew() throws PDFlibException {
        pdflib pdf = new pdflib();
        //TODO: set additional resources
        //pdf.set_parameter("license", "your pdf license here");
        pdf.set_parameter("errorpolicy", "exception");
        //OBS: systemfont is expected to have the char 0x25CF.
        //Geometric shape used as the bullet point.
        //pdf.set_parameter("FontOutline", "systemfont="+ "your path to the fonts fodler here");
        return pdf;
    }

}
