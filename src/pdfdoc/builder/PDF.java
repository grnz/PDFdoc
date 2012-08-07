package pdfdoc.builder;

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import static pdfdoc.builder.PdfStatics.*;

/**
 * @author gonka
 *         <p/>
 *         Top element in an PDF document structure.
 */
public class PDF extends XmlNode {

    public PDF() {
    }

    public PDF(String xml) {
        //TODO: parse xml and create new PDF instance
    }

    public Page createPage() {
        Page page = new Page();
        children.add(page);
        return page;
    }

    /**
     * Software used to create the document!
     * Acrobat will display this entry as »Application«
     */
    public void setCreator(String creator) {
        if (creator != null) {
            attributes.put(CREATOR, creator);
        }
    }

    public void setTitle(String title) {
        if (title != null) {
            attributes.put(TITLE, title);
        }
    }

    public void setAuthor(String author) {
        if (author != null) {
            attributes.put(AUTHOR, author);
        }
    }

    public void setSubject(String subject) {
        if (subject != null) {
            attributes.put(SUBJECT, subject);
        }
    }

    public void setKeywords(String keywords) {
        if (keywords != null) {
            attributes.put(KEYWORDS, keywords);
        }
    }

    public void setName(String name) {
        if (name != null) {
            attributes.put(NAME, name);
        }
    }

    public void setDescription(String desc) {
        attributes.put(DESCRIPTION, desc);
    }

    /**
     * Set open mode.
     * Set the appearance when the document is opened. Default: bookmarks if the
     * document contains any bookmarks, otherwise none:
     * <p/>
     * <B>none</B>         Open with no additional panel visible.
     * <B>bookmarks</B>    Open with the bookmark panel visible.
     * <B>thumbnails</B>   Open with the thumbnail panel visible
     * <B>fullscreen</B>   Open in fullscreen mode (does not work in the browser).
     * <B>layers</B>       (PDF 1.5) Open with the layer panel visible.
     *
     * @param openmode One of the following Strings "none", "bookmarks", "thumbnails", "fullscreen" and "layers".
     * @see PdfStatics
     */
    public void setOpenMode(String openmode) {
        if (openmode != null) {
            attributes.put(OPENMODE, openmode);
        }
    }

    /**
     * Set the pagelayout
     * The page layout to be used when the document is opened (default: singlepage):
     * <B>singlepage</B>       Display one page at a time.
     * <B>onecolumn</B>        Displays the pages continously in one column.
     * <B>twocolumnleft</B>    Display the pages in two columns, odd pages on the left.
     * <B>twocolumnright</B>   Display the pages in two columns, odd pages on the right.
     */
    public void setPagelayout(String pagelayout) {
        if (pagelayout != null) {
            attributes.put(PAGELAYOUT, pagelayout);
        }
    }

    public void setPdfVersion(String pdfversion) {
        if (pdfversion != null) {
            attributes.put(COMPABILITY, pdfversion);
        }
    }

    public void setMasterPassword(String password) {
        if (password != null) {
            attributes.put(MASTERPASSWORD, password);
        }
    }

    public void setUserPassword(String password) {
        if (password != null) {
            attributes.put(USERPASSWORD, password);
        }
    }

    public void setLinearize(boolean linearize, boolean inmemory) {
        attributes.put(LINEARIZE, String.valueOf(linearize));
        attributes.put(INMEMORY, String.valueOf(inmemory));
    }

    public void setOptimize(boolean optimize, boolean inmemory) {
        attributes.put(OPTIMIZE, String.valueOf(optimize));
        attributes.put(INMEMORY, String.valueOf(inmemory));
    }

    public void makeFile(String outputFilePath) throws PDFlibException {
        pdflib pdfl = PDFlibInstancePool.getInstance();
        makeFile(pdfl, outputFilePath);
        pdfl.delete();
    }

    public void makeFile(pdflib pdf, String outputFilePath) throws PDFException {
        try {
            pdf.begin_document(outputFilePath, getOptionsList());
            draw(pdf);
            pdf.end_document("");
        } catch (PDFlibException e) {
            throw new PDFException(e.getMessage());
        }
    }

    public InputStream makeInputStream() throws PDFlibException {
        pdflib pdfl = PDFlibInstancePool.getInstance();
        InputStream inputStream = makeInputStream(pdfl);
        pdfl.delete();
        return inputStream;
    }

    public InputStream makeInputStream(pdflib pdf) throws PDFException {
        try {
            pdf.begin_document("", getOptionsList());
            draw(pdf);
            pdf.end_document("");
            return new ByteArrayInputStream(pdf.get_buffer());
        } catch (PDFlibException e) {
            throw new PDFException(e.getMessage());
        }
    }

    private void draw(pdflib pdf) throws PDFlibException {
        //set document info
        final Hashtable<String, String> docinfo = getInformationAttribs();
        final Enumeration<String> enm = docinfo.keys();
        while (enm.hasMoreElements()) {
            String key = enm.nextElement();
            pdf.set_info(key, docinfo.get(key));
        }
        pdf.set_parameter("topdown", "true");
        pdf.set_parameter("charref", "true");
        //loop thru pages
        for (XmlNode pagenode : children) {
            Page page = (Page) pagenode;
            page.draw(pdf);
        }
    }

    public ArrayList<String> getErrorMessages() {
        final ArrayList<String> errors = new ArrayList<String>();
        for (XmlNode pagenode : children) {
            Page page = (Page) pagenode;
            for (XmlNode blocknode : page.getBlocks()) {
                Block block = (Block) blocknode;
                errors.addAll(block.getErrorMessages());
            }
        }
        return errors;
    }

    private String getOptionsList() throws PDFException {
        final String pdfoptionsattributes[] = new String[]{
                OPENMODE,
                COMPABILITY,
                PAGELAYOUT,
                MASTERPASSWORD,
                USERPASSWORD,
                LINEARIZE,
                OPTIMIZE,
                INMEMORY};
        return ParametersUtil.toOptionsList(attributes, pdfoptionsattributes);
    }

    private Hashtable<String, String> getInformationAttribs() {
        final Hashtable<String, String> docinfo = new Hashtable<String, String>();
        final String infoattributes[] = new String[]{
                CREATOR,
                TITLE,
                AUTHOR,
                SUBJECT,
                KEYWORDS};
        for (String option : infoattributes) {
            if (attributes.containsKey(option)) {
                docinfo.put(option, attributes.get(option));
            }
        }
        return docinfo;
    }

    public String toXml() {
        final StringBuilder xmlstring = new StringBuilder();
        final String nodename = XMLTAG_PDFDOCUMENT;
        xmlstring.append("<");
        xmlstring.append(nodename);
        appendAttributes(attributes, xmlstring);
        if (children.size() > 0) {
            xmlstring.append(">");
            for (XmlNode aChildren : children) {
                xmlstring.append(aChildren.toXml());
            }
            xmlstring.append("\n");
            xmlstring.append("</");
            xmlstring.append(nodename);
            xmlstring.append(">");
        } else {
            xmlstring.append("/>");
        }
        return xmlstring.toString();
    }

}
