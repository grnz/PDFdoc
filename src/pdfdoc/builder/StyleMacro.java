package pdfdoc.builder;

import java.util.Hashtable;

import static pdfdoc.builder.PdfStatics.*;

/**
 * @author gonka
 *         Macro are sets of text styles shich can be used to format text.
 */
public class StyleMacro extends XmlNode {

    public StyleMacro(String uid, String fontname, double fontsize, String encoding) {
        setUid(uid);
        setFontName(fontname);
        setFontSize(fontsize);
        setEncoding(encoding);
        setEmbedding(true);
    }

    public StyleMacro(String uid) {
        setUid(uid);
        setEmbedding(true);
    }

    private String getOptionsList() throws PDFException {
        String pdfoptionsattributes[] = new String[]{
                MACRO_ADJUSTMETHOD,
                MACRO_ALIGNMENT,
                MACRO_AVOIDEMPTYBEGIN,
                MACRO_CHARSPACING,
                MACRO_EMBEDDING,
                MACRO_ENCODING,
                MACRO_FIXEDLEADING,
                MACRO_FILL_COLOR,
                MACRO_FONTNAME,
                MACRO_FONTSIZE,
                MACRO_HORTABSIZE,
                MACRO_HORTABMETHOD,
                MACRO_HORIZSCALE,
                MACRO_LASTALIGNMENT,
                MACRO_LEADING,
                MACRO_LEFTINDENT,
                MACRO_MAXSPACING,
                MACRO_MINSPACING,
                MACRO_MINLINECOUNT,
                MACRO_NOFITLIMIT,
                MACRO_OVERLINE,
                MACRO_PARINDENT,
                MACRO_RIGHTINDENT,
                MACRO_RULER,
                MACRO_UNDERLINE,
                MACRO_STRIKEOUT,
                MACRO_STROKECOLOR,
                MACRO_SHRINKLIMIT,
                MACRO_SPREADLIMIT,
                MACRO_TEXTRENDERING,
                MACRO_TEXTFORMAT,
                MACRO_TEXTRISE,
                MACRO_KERNING,
                MACRO_WORDSPACING,
                MACRO_STROKEWIDTH
        };
        return ParametersUtil.toOptionsList(attributes, pdfoptionsattributes);
    }

    public String getMacroDefinitions() throws PDFException {
        StringBuilder str = new StringBuilder();
        str.append(getUid());
        str.append(" {");
        str.append(getOptionsList());
        str.append("}");
        return str.toString();
    }

    public String getInlineOptionList() throws PDFException {
        StringBuilder str = new StringBuilder();
        str.append("<");
        str.append(getOptionsList());
        str.append(">");
        return str.toString();
    }

    public void setUid(String name) {
        attributes.put(MACRO_UID, name);
    }

    public String getUid() {
        return attributes.get(MACRO_UID);
    }

    public void setStrokeWidth(String value) {
        if (value != null) {
            attributes.put(MACRO_STROKEWIDTH, value);
        }
    }

    /**
     * Method used to adjust a line when a text portion doesn't
     * fit into a line after compressing or expanding the
     * distance between words subject to the limits specified
     * by the minspacing and maxspacing options. Default: auto
     * auto        The following methods are applied in order:
     * shrink, spread, nofit, split.
     * clip        Same as nofit, except that the long part at
     * the right edge of the fit box (taking into
     * account the rightindent option) will be
     * clipped.
     * nofit       The last word will be moved to the next line
     * provided the remaining (short) line will not
     * be shorter than the percentage specified in
     * the nofitlimit option. Even justified
     * paragraphs may look slightly ragged.
     * shrink      If a word doesn't fit in the line the text
     * will be compressed subject to shrinklimit. If
     * it still doesn't fit the nofit method will be
     * applied.
     * split       The last word will not be moved to the next
     * line, but will forcefully be hyphenated. For
     * text fonts a hyphen character will be
     * inserted, but not for symbol fonts.
     * spread      The last word will be moved to the next line
     * and the remaining (short)line will be
     * justified by increasing the distance between
     * characters in a word, subject to spreadlimit.
     * If justification still cannot be achieved the
     * nofit method will be applied.
     *
     * @param method String, one of the following: "auto", "clip", "nofit", "shrink", "split" and "spread".
     */
    public void setAdjustMethod(String method) {
        if (method != null)
            attributes.put(MACRO_ADJUSTMETHOD, method);
    }

    /**
     * Specifies formatting for lines in a paragraph.
     * (default: left):
     * left        left-aligned, starting at leftindent
     * center      centered between leftindent and rightindent
     * right       right-aligned, ending at rightindent
     * justify     left- and right-aligned
     *
     * @param align String one of the following: "left", "center", "right" and "justify".
     */
    public void setAlignment(String align) {
        if (align != null) {
            attributes.put(MACRO_ALIGNMENT, align);
        }
    }

    /**
     * Set avoid emty begin.
     *
     * @param avoid true or false.
     */
    public void setAvoidEmptyBegin(boolean avoid) {
        attributes.put(MACRO_AVOIDEMPTYBEGIN, Boolean.toString(avoid));
    }

    /**
     * Set the Character spacing.  Percentages are based on fontsize. Default: 0
     * <p/>
     * See Table 8.16 in the PDFlib manual.
     * <p/>
     * Set or get the character spacing, i.e., the shift of the current point after placing
     * individual characters in a string. It is specified points, and is reset to the default of
     * 0 at the beginning and end of each page. In order to spread characters apart use positive
     * values for horizontal writing mode, and negative values for vertical writing mode.
     *
     * @param charspacing The character spacing to be used, either in points or in percent.
     */
    public void setCharSpacing(double charspacing) {
        attributes.put(MACRO_CHARSPACING, Double.toString(charspacing));
    }


    /**
     * Enable font embedding. If enabled the font defined will be embedded in
     * the final pdf document. An exception to this, is when a font is not
     * "Embeddable" due to usage conditions of some sort.
     *
     * @param embedding true/false
     */
    public void setEmbedding(boolean embedding) {
        attributes.put(MACRO_EMBEDDING, Boolean.toString(embedding));
    }

    /**
     * Set the font encoding.
     * <p/>
     * See Table 4.2 in the PDFlib manual.
     * <p/>
     * typically:
     * <p/>
     * "winansi"
     * "unicode"
     * "iso8859-1"
     * "macroman"
     * "pdfdoc"
     *
     * @param encoding The encoding to be used when using a font.
     */
    public void setEncoding(String encoding) {
        if (encoding != null) {
            attributes.put(MACRO_ENCODING, encoding);
        }
    }

    /**
     * Set fixed leading. If true, the first leading value found in each line will be used. Otherwise the
     * maximum of all leading values in the line will be used. Default: false
     *
     * @param fixedleading Set to String "true" of "false".
     */
    public void setFixedLeading(boolean fixedleading) {
        attributes.put(MACRO_FIXEDLEADING, Boolean.toString(fixedleading));
    }

    /**
     * Set the fontname (Must be used with the encoding option).
     *
     * @param fontname The name of the font.
     * @see #setEncoding(String)
     */
    public void setFontName(String fontname) {
        if (fontname != null) {
            attributes.put(MACRO_FONTNAME, fontname);
        }
    }

    /**
     * Set the fontsize.
     *
     * @param fontsize The fontsize in points as a double value.
     */
    public void setFontSize(double fontsize) {
        attributes.put(MACRO_FONTSIZE, Double.toString(fontsize));
    }

    /**
     * Set the horizontal tab size.
     * Width of a horizontal tab in points, or as a percentage of the width of the fit box.
     * The interpretation depends on the hortabmethod option. Default: 7.5%
     *
     * @param hortabsize The horizontal tab size in points or percentage.
     */
    public void setHortabSize(float hortabsize) {
        attributes.put(MACRO_HORTABSIZE, Float.toString(hortabsize));
    }

    /**
     * Treatment of horizontal tabs in the text.
     * If the determined position is to the left of the
     * current text position, the tab will be ignored
     * (default: relative):
     * relative        The position will be advanced by
     * the amount specified in hortabsize.
     * typewriter      The position will be advanced to
     * the next multiple of hortabsize.
     * ruler           The position will be advanced to
     * the n-th tab value in the ruler
     * option, where n is the number of
     * tabs found in the line so far. If n
     * is larger than the number of tab
     * positions the relative method will
     * be applied.
     *
     * @param method String: one of the following flags: "relative" (default), "typewriter" and "ruler".
     */
    public void setHortabMethod(String method) {
        if (method != null) {
            attributes.put(MACRO_HORTABMETHOD, method);
        }
    }

    /**
     * The maximum or minimum distance between words (in user points, or as a
     * percentage of the width of the space character). The calculated word spacing is
     * limited by the provided values (but the wordspacing option will still be added).
     * Defaults: minspacing=50%, maxspacing=500%
     *
     * @param maxspacing the max spacing in points(float) or percentage.
     */
    public void setMaxSpacing(float maxspacing) {
        attributes.put(MACRO_MAXSPACING, Float.toString(maxspacing));
    }

    /**
     * The maximum or minimum distance between words (in user points, or as a
     * percentage of the width of the space character). The calculated word spacing is
     * limited by the provided values (but the wordspacing option will still be added).
     * Defaults: minspacing=50%, maxspacing=500%.
     *
     * @param minspacing the min spacing in points(float) or percentage.
     * @see #setMinSpacing(float)
     */
    public void setMinSpacing(float minspacing) {
        attributes.put(MACRO_MINSPACING, Float.toString(minspacing));
    }

    /**
     * Set or get the horizontal text scaling to the given percentage. Text scaling shrinks
     * or expands the text by a given percentage. It is set to the default of 100 at the
     * beginning and end of each page. Text scaling always relates to the horizontal
     * coordinate.
     *
     * @param scaling The scaling factors in percent.
     */
    public void setHorizScaling(double scaling) {
        attributes.put(MACRO_HORIZSCALE, Double.toString(scaling));
    }

    /**
     * Set the leading.
     *
     * @param leading The leading value to be used.
     */
    public void setLeading(double leading) {
        attributes.put(MACRO_LEADING, Double.toString(leading));
    }

    /**
     * Overline mode.
     * Default: the global overline parameter.
     *
     * @param overline boolean
     */
    public void setOverline(boolean overline) {
        attributes.put(MACRO_OVERLINE, Boolean.toString(overline));
    }

    /**
     * Underline mode.
     * Default: the global underline parameter.
     *
     * @param underline boolean true of false
     */
    public void setUnderline(boolean underline) {
        attributes.put(MACRO_UNDERLINE, Boolean.toString(underline));
    }

    /**
     * Strikeout mode.
     * Default: the global strikeout parameter.
     *
     * @param strikeout boolean true or false
     */
    public void setStrikeout(boolean strikeout) {
        attributes.put(MACRO_STRIKEOUT, Boolean.toString(strikeout));
    }

    /**
     * Set or get the current text rendering mode.
     * It is set to the default of 0 at the beginning of each page.
     * Scope: page, pattern, output, glyph.
     * 0 fill text
     * 1 stroke text (outline)
     * 2 fill and stroke text
     * 3 invisible text
     * 4 fill text and add it to the clipping path
     * 5 stroke text and add it to the clipping path
     * 6 fill and stroke text and add it to the clipping path
     * 7 add text to the clipping path
     *
     * @param rendering Textrendering flag.
     */
    public void setTextRenderingMode(int rendering) {
        attributes.put(MACRO_TEXTRENDERING, Integer.toString(rendering));
    }

    /**
     * Enable kerning values.
     *
     * @param kerning true or false
     */
    public void setKerning(boolean kerning) {
        attributes.put(MACRO_KERNING, Boolean.toString(kerning));
    }

    /**
     * Set the fonts fillcolor.
     * <p/>
     * Colorspace "gray"   GRAY(100)
     * Colorspace "rgb":   RGB(255,255,255)
     * Colorspace "cmyk":  CMYK(100,100,100,100)
     * Colorspace "lab":   LAB(100,127,127)
     * Colorspace "spot":  SPOT(PANTONE 300 U,0) or SPOT(HKS 43 N,0)
     *
     * @param color String of type: "none".
     */
    public void setFillColor(String color) {
        if (color != null) {
            attributes.put(MACRO_FILL_COLOR, color);
        }
    }

    /**
     * Set the fonts strokecolor
     *
     * @param color String of type:
     *              <p/>
     *              "none"
     *              <p/>
     *              Colorspace "gray"   GRAY(100)
     *              Colorspace "rgb":   RGB(255,255,255)
     *              Colorspace "cmyk":  CMYK(100,100,100,100)
     *              Colorspace "lab":   LAB(100,127,127)
     *              Colorspace "spot":  SPOT(PANTONE 300 U,0) or SPOT(HKS 43 N,0)
     */
    public void setStrokeColor(String color) {
        if (color != null) {
            attributes.put(MACRO_STROKECOLOR, color);
        }
    }

    /**
     * Text Format for Unicode Strings.
     * The Unicode standard supports several transformation
     * formats for storing the actual byte values which comprise
     * a Unicode string. These vary in the number of bytes per
     * character and the ordering of bytes within a character.
     * Unicode strings in PDF-engine can be supplied in
     * UTF-8 or UTF-16 formats with any byte ordering. This can
     * be controlled with the textformat parameter for all text
     * on page descriptions, and the hypertextformat parameter
     * for all hypertext elements
     * <p/>
     * bytes       One byte in the string corresponds to one
     * character. This is mainly useful for 8-bit
     * encodings.
     * utf8        Strings are expected in UTF-8 format.
     * ebcdicutf8  Strings are expected in EBCDIC-coded UTF-8
     * format (only on iSeries and zSeries).
     * utf16       Strings are expected in UTF-16 format. A
     * Unicode Byte Order Mark (BOM) at the start of
     * the string will be evaluated and then removed.
     * If no BOM is present the string is expected in
     * the machine's native byte ordering (on Intel
     * x86 architectures the native byte order is
     * little-endian, while on Sparc and PowerPC
     * systems it is big-endian).
     * utf16be     Strings are expected in UTF-16 format in
     * big-endian byte ordering. There is no special
     * treatment for Byte Order Marks.
     * utf16le     Strings are expected in UTF-16 format in
     * little-endian byte ordering. There is no special
     * treatment for Byte Order Marks.
     * auto        Equivalent to bytes for 8-bit encodings, and utf16
     * or wide-character addressing (unicode, glyphid, or
     * a UCS2 or UTF16 CMap). This setting will provide
     * proper text interpretation in most environments
     * which do not use Unicode natively.
     *
     * @param textformat one of the following flags: bytes, utf8, ebcdicutf8, utf16, utf16be, utf16le and auto
     */
    public void setTextFormat(String textformat) {
        if (textformat != null) {
            attributes.put(MACRO_TEXTFORMAT, textformat);
        }
    }

    /**
     * The text rise mode (float).
     * Defines the textrise. Default: the global text rise parameter.
     *
     * @param textrise float defines points
     */
    public void setTextrise(float textrise) {
        attributes.put(MACRO_TEXTRISE, Float.toString(textrise));
    }

    /**
     * Set the word spacing, i.e., the shift of the current point after placing individual
     * words in a line. In other words, the current point is moved horizontally after
     * each space character (0x20). The spacing value is given in text space units, and is
     * reset to the default of 0 at the beginning and end of each page.
     *
     * @param wordspace The amount of wordspace to be used in points.
     */
    public void setWordSpacing(double wordspace) {
        attributes.put(MACRO_WORDSPACING, Double.toString(wordspace));
    }

    /**
     * Formatting for the last line in a paragraph. All keywords of the alignment option
     * are supported, plus the following (default: auto):
     * auto Use the value of the alignment option unless it is justify. In the latter
     * case left will be used.
     *
     * @param alignment An alignment flag.
     * @see #setAlignment(String)
     */
    public void setLastAlignment(String alignment) {
        if (alignment != null) {
            attributes.put(MACRO_LASTALIGNMENT, alignment);
        }
    }

    /**
     * Set the minimum line count. The minimum number of lines in the last paragraph in the fit box. If there are
     * fewer lines they will be placed in the next fit box. The value 2 can be used to
     * prevent single lines of a paragraph at the end of a fit box ("orphans"). Default: 1
     *
     * @param linecount number of lines in the last paragraph.
     */
    public void setMinlineCount(int linecount) {
        attributes.put(MACRO_MINLINECOUNT, Integer.toString(linecount));
    }

    /**
     * Set the parindent paramter. Left indent of the first line of a paragraph in points,
     * or as a percentage of the width of the fit box. The amount will be added to leftindent.
     * Specifying this option within a line will act like a tab. Default: 0
     *
     * @param parindent The parindent value in percentage or points.
     */
    public void setParIndent(float parindent) {
        attributes.put(MACRO_PARINDENT, Float.toString(parindent));
    }

    /**
     * Set the right indent value. Right indent of all text lines in points, or as
     * a percentage of the width of the fit box.
     *
     * @param rightindent the right indent value in float(points) or percentage.
     */
    public void setRightIndent(float rightindent) {
        attributes.put(MACRO_RIGHTINDENT, Float.toString(rightindent));
    }

    /**
     * (List of floats or percentages) List of absolute tab positions for hortabmethod=ruler2. The list may contain
     * up to 32 non-negative entries in ascending order. Default: integer multiples of hortabsize
     */
    public void setRuler(String ruler) {
        attributes.put(MACRO_RULER, ruler);
    }

    /**
     * Set the left indent value. Left indent of all text lines in points, or as
     * a percentage of the width of the fit box. If leftindent is specified within a line and the
     * determined position is to the left of the current text position, this option will be
     * ignored for the current line. Default: 0
     *
     * @param leftindent the left indent value in float(points) or percentage.
     */
    public void setLeftIndent(float leftindent) {
        attributes.put(MACRO_LEFTINDENT, Float.toString(leftindent));
    }

    /**
     * Set nofit limit. Lower limit for the length of a line with the nofit method (in points or
     * as a percentage of the width of the fitbox). Default: 75%.
     *
     * @param limit the fitlimit in points or percentage
     * @see #setNoFitLimit(float)
     */
    public void setNoFitLimit(float limit) {
        attributes.put(MACRO_NOFITLIMIT, Float.toString(limit));
    }

    /**
     * Set the shrinklimit. Lower limit for compressing text with the shrink method; the calculated shrinking
     * factor is limited by the provided value, but will be multiplied with the value of the
     * horizscaling option. Default: 85%
     *
     * @param limit the shrinklimit in percent.
     */
    public void setShrinkLimit(float limit) {
        attributes.put(MACRO_SHRINKLIMIT, Float.toString(limit));
    }

    /**
     * Set spreadlimit. Upper limit for the distance between two characters for the spread method (in
     * points or as a percentage of the font size); the calculated character
     * distance will be added to the value of the charspacing option. Default: 0
     *
     * @param limit the spreadlimit in points (float) or as a percentage of the font size.
     */
    public void setSpreadLimit(float limit) {
        attributes.put(MACRO_SPREADLIMIT, Float.toString(limit));
    }

    public void setAll(Hashtable<String, String> attributes) {
        this.attributes.putAll(attributes);
    }

    public void concatenate(StyleMacro macro) {
        //font
        add(MACRO_FONTNAME, macro, "Helvetica");
        add(MACRO_FONTSIZE, macro, "12");
        add(MACRO_ENCODING, macro, "unicode");
        add(MACRO_FILL_COLOR, macro, "cmyk(0,0,0,100)");
        add(MACRO_STROKECOLOR, macro, "cmyk(0,0,0,100)");
        add(MACRO_STROKEWIDTH, macro, "auto");
        //
        add(MACRO_ADJUSTMETHOD, macro, "auto");
        add(MACRO_ALIGNMENT, macro, "left");
        add(MACRO_CHARSPACING, macro, "0");
        add(MACRO_HORTABSIZE, macro, "7.5%");
        add(MACRO_HORTABMETHOD, macro, "relative");
        add(MACRO_HORIZSCALE, macro, "100");
        add(MACRO_LASTALIGNMENT, macro, "auto");
        add(MACRO_LEADING, macro, "100%");
        add(MACRO_LEFTINDENT, macro, "0");
        add(MACRO_MAXSPACING, macro, "500%");
        add(MACRO_MINSPACING, macro, "50%");
        add(MACRO_MINLINECOUNT, macro, "1");
        add(MACRO_NOFITLIMIT, macro, "75%");
        add(MACRO_PARINDENT, macro, "0");
        add(MACRO_RIGHTINDENT, macro, "0");
        add(MACRO_SHRINKLIMIT, macro, "85%");
        add(MACRO_SPREADLIMIT, macro, "0");
        add(MACRO_TEXTRENDERING, macro, "0");
        add(MACRO_TEXTRISE, macro, "0");
        add(MACRO_WORDSPACING, macro, "0");
        //boolean
        or(MACRO_KERNING, macro);
        or(MACRO_UNDERLINE, macro);
        or(MACRO_OVERLINE, macro);
        or(MACRO_UNDERLINE, macro);
        or(MACRO_STRIKEOUT, macro);
        or(MACRO_AVOIDEMPTYBEGIN, macro);
        or(MACRO_FIXEDLEADING, macro);
        //left out
        /*
        PdfStatics.MACRO_RULER,
        PdfStatics.MACRO_TEXTFORMAT,
        */
    }

    private void add(String attrname, StyleMacro other, String defaultvalue) {
        final String othervalue = other.attributes.get(attrname);
        final String thisvalue = attributes.get(attrname);
        if (othervalue != null) {
            attributes.put(attrname, othervalue);
        } else if (thisvalue == null) {
            attributes.put(attrname, defaultvalue);
        }
    }

    private void or(String attrname, StyleMacro other) {
        final String othervalue = other.attributes.get(attrname);
        final String thisvalue = attributes.get(attrname);
        boolean a = othervalue != null && Boolean.parseBoolean(othervalue);
        boolean b = thisvalue != null && Boolean.parseBoolean(thisvalue);
        attributes.put(attrname, String.valueOf(a || b));
    }

    public StyleMacro cloneit() {
        final StyleMacro newobj = new StyleMacro(this.getUid() + "_copy");
        newobj.attributes.putAll(attributes);
        return newobj;
    }

    public String toXml() {
        return toXml(XMLTAG_MACRO);
    }

}
