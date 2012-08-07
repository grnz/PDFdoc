package pdfdoc.builder;

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;

import javax.management.Attribute;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pdfdoc.builder.PdfStatics.*;

/**
 * @author gonka
 */
public class TextBlock extends Block implements IDrawable {

    private String text;
    private StyleMacro defaultMacro = new StyleMacro("regular", "Courier", 12, "unicode");
    final private ArrayList<StyleMacro> macros = new ArrayList<StyleMacro>();
    final private ArrayList<Attribute> fontparameters = new ArrayList<Attribute>();

    public TextBlock() {
    }

    public StyleMacro createMacro(String name, String fontname, double fontsize, String encoding) {
        final StyleMacro macro = new StyleMacro(name, fontname, fontsize, encoding);
        macros.add(macro);
        return macro;
    }

    public void addMacro(StyleMacro macro) {
        if (macro != null) {
            macros.add(macro);
        }
    }

    public StyleMacro getMacro(String name) {
        if (defaultMacro != null && defaultMacro.getUid().equalsIgnoreCase(name)) {
            return defaultMacro;
        } else {
            for (XmlNode macronode : macros) {
                final StyleMacro macro = (StyleMacro) macronode;
                if (macro != null && macro.getUid().equalsIgnoreCase(name)) {
                    return macro;
                }
            }
        }
        return null;
    }

    public StyleMacro getDefaultMacro() {
        return defaultMacro;
    }

    public void setDefaultMacro(StyleMacro defaultMacro) {
        this.defaultMacro = defaultMacro;
    }

    /**
     * pdf.set_parameter("FontOutline", "8010=/path/fonts/8010.ttf");
     *
     * @param param "8010=/path/fonts/8010.ttf"
     */
    public void addFontOutlineParameter(String param) {
        fontparameters.add(new Attribute("FontOutline", param));
    }

    /**
     * p.set_parameter("FontPFM", "/path/fonts/Foobar-Bold=foobb___.pfm");
     *
     * @param param /path/fonts/Foobar-Bold=foobb___.pfm
     */
    public void add_PFM_FontMetrics(String param) {
        fontparameters.add(new Attribute("FontPFM", param));
    }

    /**
     * p.set_parameter("FontAFM", "/path/fonts/Foobar-Bold=foobb___.afm");
     *
     * @param param /path/fonts/Foobar-Bold=foobb___.afm
     */
    public void add_AFM_FontMetrics(String param) {
        fontparameters.add(new Attribute("FontAFM", param));
    }

    public void setText(String txt) throws PDFException {
        text = txt;
    }

    /**
     * The keywords nofit, clip, auto.
     *
     * @param method fit method
     */
    public void setFitMethod(String method) {
        if (method != null) {
            attributes.put(FITMETHOD, method);
        }
    }

    /**
     * The keywords bottom, center, top.
     *
     * @param method vertical alignment method
     */
    public void setVerticalAlignment(String method) {
        if (method != null) {
            attributes.put(VERTICALALIGNMENT, method);
        }
    }

    private void setFontParameters(pdflib pdf) throws PDFlibException {
        for (Attribute fontparam : fontparameters) {
            pdf.set_parameter(fontparam.getName(), (String) fontparam.getValue());
        }
    }

    public Box getBoundingBox(pdflib pdf, Box fitbox) throws PDFlibException {
        setFontParameters(pdf);
        final String textflowcontent = toMacrosDefinition(text);
        final int textflow = pdf.create_textflow(textflowcontent, "errorpolicy=return");
        if (assertPdfElement(textflow, pdf)) {
            pdf.fit_textflow(textflow, fitbox.getLeftX(), fitbox.getLeftY(), fitbox.getRightX(), fitbox.getRightY(), getFitOptionsList() + " rewind=-1 blind=true");
            double lx = pdf.info_textflow(textflow, "x4");
            double ly = pdf.info_textflow(textflow, "y4");
            double rx = pdf.info_textflow(textflow, "x2");
            double ry = pdf.info_textflow(textflow, "y2");
            pdf.delete_textflow(textflow);
            return new Box(0, 0, lx, ly, rx, ry);
        }
        return null;
    }

    public void drawContent(pdflib pdf, Box fitbox) throws PDFlibException {
        setFontParameters(pdf);
        final String textflowcontent = toMacrosDefinition(text);
        final int textflow = pdf.create_textflow(textflowcontent, "errorpolicy=return");
        if (assertPdfElement(textflow, pdf)) {
            pdf.fit_textflow(textflow, fitbox.getLeftX(), fitbox.getLeftY(), fitbox.getRightX(), fitbox.getRightY(), getFitOptionsList() + " rewind=-1");
        }
    }

    /**
     * Expands all macros in the text and returns the parsed result to be fed into the pdf engine.
     *
     * @param text text containing macros
     * @throws PDFException
     */
    private String toMacrosDefinition(String text) throws PDFException {
        if (text == null || text.length() == 0) {
            return "";
        } else if (!text.contains("<")) {
            final StyleMacro firstadded = defaultMacro;
            final String macrodefinition = "<macro {\n" + firstadded.getMacroDefinitions() + "}>";
            return macrodefinition + "<&" + firstadded.getUid() + ">" + text;
        } else {
            //add default macro
            text = "<" + defaultMacro.getUid() + ">" + text + "</" + defaultMacro.getUid() + ">";
            //parse text with macros
            final StringBuilder formatedtext = new StringBuilder();
            final StringBuilder macrodefinitions = new StringBuilder();
            final Stack<StyleMacro> statestack = new Stack<StyleMacro>();
            StyleMacro current = new StyleMacro("current");
            int tagcounter = 0;
            macrodefinitions.append("<macro {\n");
            final Pattern pattern = Pattern.compile("<[/]{0,1}([A-Za-z0-9-_ ]*)>");
            final Matcher matcher = pattern.matcher(text);
            final String[] tokens = pattern.split(text);
            int i = 1;
            while (matcher.find()) {
                String token = "";
                if (tokens.length > i) {
                    token = tokens[i++];
                    token = token.replace("<", "&lt;");
                    token = token.replace(">", "&gt;");
                }
                final String tag = matcher.group();
                final String macroname = matcher.group(1);
                if (!tag.startsWith("</")) {
                    final String newtagname = String.valueOf(tagcounter++);
                    //record current state
                    final StyleMacro state = current.cloneit();
                    state.setUid("reset_" + newtagname);
                    statestack.push(state);
                    //
                    StyleMacro macrinho = getMacro(macroname);
                    if (macrinho != null) {//apply current macro
                        macrinho = macrinho.cloneit();
                        macrinho.setUid(newtagname);
                        macrodefinitions.append(macrinho.getMacroDefinitions()).append("\n");
                        formatedtext.append("<&").append(macrinho.getUid()).append(">");
                        //update current state
                        current.concatenate(macrinho);
                    } else {//tag definition missing
                        formatedtext.append("&lt;").append(macroname).append("&gt;");
                        state.setUid(macroname);
                    }
                } else {
                    try {
                        current = statestack.pop();
                    } catch (EmptyStackException ese) {
                        //Probably a close tag without correspondent open tag.
                        ese.printStackTrace();
                    }
                    if (current.getUid().startsWith("reset_")) {
                        //apply end macro
                        macrodefinitions.append(current.getMacroDefinitions()).append("\n");
                        formatedtext.append("<&").append(current.getUid()).append(">");
                    } else { //tag definition missing
                        formatedtext.append("&lt;/").append(current.getUid()).append("&gt;");
                    }
                }
                formatedtext.append(token);
            }
            macrodefinitions.append("}>");
            return macrodefinitions.toString() + formatedtext.toString();
        }
    }

    private String getFitOptionsList() throws PDFlibException {
        final StringBuilder optionslist = new StringBuilder();
        final String textflowoptionsattributes[] = new String[]{VERTICALALIGNMENT, ORIENTATE, FITMETHOD};
        optionslist.append(ParametersUtil.toOptionsList(attributes, textflowoptionsattributes));
        optionslist.append(" firstlinedist=ascender");
        optionslist.append(" lastlinedist=descender");
        return optionslist.toString();
    }

    public String toXml() {
        final StringBuilder xmlstring = new StringBuilder();
        final String nodename = XMLTAG_TEXT;
        xmlstring.append("\n");
        xmlstring.append("<");
        xmlstring.append(nodename);
        appendAttributes(attributes, xmlstring);
        xmlstring.append(">");
        //macros
        for (XmlNode aChildren : macros) {
            xmlstring.append(aChildren.toXml());
        }
        //content
        xmlstring.append("\n");
        xmlstring.append("<");
        xmlstring.append(XMLTAG_CONTENT);
        xmlstring.append(">");
        if (text != null) {
            xmlstring.append(scapexml(text));
        }
        xmlstring.append("</");
        xmlstring.append(XMLTAG_CONTENT);
        xmlstring.append(">");
        //frames and blocks
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
