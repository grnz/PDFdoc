package pdfdoc.builder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import static pdfdoc.builder.PdfStatics.*;

/**
 * @author gonka
 *         <p/>
 *         Utility class to transform parameters.
 */
public class ParametersUtil {

    private ParametersUtil() {
    }

    public static Properties toProperties(String args[]) {
        final StringBuilder strprops = new StringBuilder();
        final Properties prp = new Properties();
        final InputStream input;
        try {
            for (String str : args) {
                strprops.append(str);
                strprops.append("\n");
            }
            input = new ByteArrayInputStream(strprops.toString().getBytes());
            prp.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prp;
    }

    public static String toOptionsList(Hashtable<String, String> attributes, String[] selectedattribs) throws PDFException {
        final StringBuilder optionslist = new StringBuilder();
        for (String option : selectedattribs) {
            if (attributes.containsKey(option)) {
                if (optionslist.length() > 0) {
                    optionslist.append(" ");
                }
                String value = attributes.get(option);
                if (value != null) {
                    if (option.equalsIgnoreCase(FILL_COLOR) || option.equalsIgnoreCase(MACRO_STROKECOLOR)) {
                        optionslist.append(option);
                        optionslist.append("=");
                        optionslist.append(new Color(value).getOptionlist());
                    } else if (option.equalsIgnoreCase(MACRO_FONTNAME)) {
                        optionslist.append(option);
                        optionslist.append("=");
                        optionslist.append("{").append(value).append("}");
                    } else {
                        optionslist.append(option);
                        optionslist.append("=");
                        optionslist.append(value);
                    }

                }
            }
        }
        return optionslist.toString();
    }
}
