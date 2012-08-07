package pdfdoc.builder;

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;

/**
 * @author gonka
 *         <p/>
 *         General interface to abstrac the drawing of elements into the pdflib lib.
 */
public interface IDrawable {

    void draw(pdflib pdf, String... params) throws PDFlibException;

}
