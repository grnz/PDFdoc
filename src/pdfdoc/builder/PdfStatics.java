package pdfdoc.builder;

/**
 * @author gonka
 */
public class PdfStatics {

    //xmltagnames
    public final static String XMLTAG_PDFDOCUMENT = "pdfdocument";
    public final static String XMLTAG_MACRO = "macro";
    public final static String XMLTAG_PAGE = "page";
    public final static String XMLTAG_TEXT = "text";
    public final static String XMLTAG_IMAGE = "image";
    public final static String XMLTAG_PDF = "pdf";
    public final static String XMLTAG_CONTENT = "content";
    public final static String XMLTAG_FRAME = "frame";
    public final static String XMLTAG_BACKGROUND = "background";

    //Document properties
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String SIZE = "size";
    public static final String TYPE = "type";
    public static final String ENCODING = "encoding";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String PDFTARGETVERSION = "pdftargetversion";
    public static final String BLEED = "bleed";
    public static final String COMPABILITY = "compatibility";
    public static final String OPENMODE = "openmode";
    public static final String PAGELAYOUT = "pagelayout";
    public static final String ERRORPOLICY = "errorpolicy";
    public static final String CREATOR = "Creator";
    public static final String SUBJECT = "Subject";
    public static final String AUTHOR = "Author";
    public static final String KEYWORDS = "Keywords";
    public static final String TITLE = "Title";
    //Access security
    public static final String SECURITY = "security";
    public static final String PERMISSIONS = "permissions";
    public static final String MASTERPASSWORD = "masterpassword";
    public static final String USERPASSWORD = "userpassword";
    public static final String SEC_NOPRINT = "noprint";
    public static final String SEC_NOMODIFY = "nomodify";
    public static final String SEC_NOCOPY = "nocopy";
    public static final String SEC_NOANNOTS = "noannots";
    public static final String SEC_NOFORMS = "noforms";
    public static final String SEC_NOACCESSIBLE = "noaccessible";
    public static final String SEC_NOASSEMBLE = "noassemble";
    public static final String SEC_NOHIRESPRINT = "nohiresprint";
    public static final String SEC_PLAINMETADATA = "plainmetadata";
    //Open mode
    public static final String OPEN_MODE_NONE = "none";
    public static final String OPEN_MODE_BOOKMARKS = "bookmarks";
    public static final String OPEN_MODE_THUMBNAILS = "thumbnails";
    public static final String OPEN_MODE_FULLSCREEN = "fullscreen";
    public static final String OPEN_MODE_LAYERS = "layers";
    //Document optmization
    public static final String LINEARIZE = "linearize";
    public static final String OPTIMIZE = "optimize";
    public static final String INMEMORY = "inmemory";

    //Positioning
    public static final String POS_X = "x";
    public static final String POS_Y = "y";
    public static final String ROTATE = "rotate";
    public static final String ROTATION = "rotation";
    public static final String OFFSETX = "offsetx";
    public static final String OFFSETY = "offsety";
    public static final String ORIENTATE = "orientate";
    public static final String ORIENTATION_NORTH = "north";
    public static final String ORIENTATION_SOUTH = "south";
    public static final String ORIENTATION_EAST = "east";
    public static final String ORIENTATION_WEST = "west";
    //Alignemt
    public static final String VERTICALALIGNMENT = "verticalalign";
    public static final String ALIGNMENT = "alignment";
    public static final String JUSTIFY = "justify";
    public static final String CENTER = "center";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String SCALE = "scale";
    //Color
    public static final String COLOR = "color";
    public static final String COLOR_SPACE_GRAY = "gray";
    public static final String COLOR_SPACE_CMYK = "cmyk";
    public static final String COLOR_SPACE_RGB = "rgb";
    public static final String COLOR_SPACE_LAB = "lab";
    public static final String COLOR_SPACE_SPOT = "spot";
    public static final String COLOR_SPACE_ICCGRAY = "iccbasedgray";
    public static final String COLOR_SPACE_ICCRGB = "iccbasedrgb";
    public static final String COLOR_SPACE_ICCCMYK = "iccbasedcmyk";
    public static final String COLOR_SPOT_PANTONE = "PANTONE";
    public static final String COLOR_SPOT_HKS = "HKS";

    //Fitmethod - Images
    public static final String FIT_METHOD_NOFIT = "nofit";
    public static final String FIT_METHOD_CLIP = "clip";
    public static final String FIT_METHOD_SMALLEST = "meet";
    public static final String FIT_METHOD_LARGEST = "slice";
    public static final String FIT_METHOD_FILL = "entire";
    public static final String FIT_METHOD_SCALE = "scale";
    public static final String FIT_METHOD_CUSTOM = "custom";

    //Macros
    public static final String MACRO_UID = "uid";
    public static final String MACRO_ADJUSTMETHOD = "adjustmethod";
    public static final String MACRO_ALIGNMENT = "alignment";
    public static final String MACRO_AVOIDEMPTYBEGIN = "avoidemptybegin";
    public static final String MACRO_CHARSPACING = "charspacing";
    public static final String MACRO_EMBEDDING = "embedding";
    public static final String MACRO_ENCODING = "encoding";
    public static final String MACRO_FIXEDLEADING = "fixedleading";
    public static final String MACRO_FILL_COLOR = "fillcolor";
    public static final String MACRO_FONTNAME = "fontname";
    public static final String MACRO_FONTSIZE = "fontsize";
    public static final String MACRO_HORTABSIZE = "hortabsize";
    public static final String MACRO_HORTABMETHOD = "hortabmethod";
    public static final String MACRO_HORIZSCALE = "horizscaling";
    public static final String MACRO_LASTALIGNMENT = "lastalignment";
    public static final String MACRO_LEADING = "leading";
    public static final String MACRO_LEFTINDENT = "leftindent";
    public static final String MACRO_MAXSPACING = "maxspacing";
    public static final String MACRO_MINSPACING = "minspacing";
    public static final String MACRO_MINLINECOUNT = "minlinecount";
    public static final String MACRO_OVERLINE = "overline";
    public static final String MACRO_RULER = "ruler";
    public static final String MACRO_NOFITLIMIT = "nofitlimit";
    public static final String MACRO_PARINDENT = "parindent";
    public static final String MACRO_RIGHTINDENT = "rightindent";
    public static final String MACRO_UNDERLINE = "underline";
    public static final String MACRO_STRIKEOUT = "strikeout";
    public static final String MACRO_STROKECOLOR = "strokecolor";
    public static final String MACRO_STROKEWIDTH = "strokewidth";
    public static final String MACRO_SHRINKLIMIT = "shrinklimit";
    public static final String MACRO_SPREADLIMIT = "spreadlimit";
    public static final String MACRO_TEXTRENDERING = "textrendering";
    public static final String MACRO_TEXTFORMAT = "textformat";
    public static final String MACRO_TEXTRISE = "textrise";
    public static final String MACRO_KERNING = "kerning";
    public static final String MACRO_WORDSPACING = "wordspacing";

    public static final String HORTABMETHOD_RELATIVE = "relative";
    public static final String HORTABMETHOD_TYPEWRITER = "typewriter";
    public static final String HORTABMETHOD_RULER = "ruler";

    //Fit method -Text flow
    public static final String FITMETHOD = "fitmethod";
    public static final String FITMETHOD_NOFIT = "nofit";
    public static final String FITMETHOD_CLIP = "clip";
    public static final String FITMETHOD_AUTO = "auto";
    //Image clip path
    public static final String CLIPPING = "clipping";

    //Frame
    public static final String FRAME_COLOR = "framecolor";
    public static final String MARGIN = "margin";
    public static final String LINEWIDTH = "linewidth";
    public static final String FRAME_BOUNDRY = "boundry";
    public static final String FRAME_BLOCK = "block";

    //Backgrounds
    public static final String FILL_COLOR = "fillcolor";
    public static final String BACKGROUND_BOUNDRY = "boundry";
    public static final String BACKGROUND_BLOCK = "block";

    //Linejoin
    public static final String LINEJOIN = "linejoin";
    public final static int LINEJOIN_MITTER = 0;
    public final static int LINEJOIN_ROUND = 1;
    public final static int LINEJOIN_BEVEL = 2;

    //Linecap
    public static final String LINECAP = "linecap";
    public final static int LINECAP_BUTT = 0;
    public final static int LINECAP_ROUND = 1;
    public final static int LINECAP_PROJECTING = 2;

    //Text rendering modes
    public static final int TR_FILL = 0;
    public static final int TR_STROKE = 1;
    public static final int TR_FILLSTROKE = 2;
    public static final int TR_INVISIBLE = 3;
    public static final int TR_FILLADDCLIP = 4;
    public static final int TR_STROKEADDCLIP = 5;
    public static final int TR_STROKEFILLADDCLIP = 6;
    public static final int TR_ADD_TO_CLIPPING_PATH = 7;

}
