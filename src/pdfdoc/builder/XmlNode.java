package pdfdoc.builder;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author gonka
 */
public abstract class XmlNode {

    protected final ArrayList<XmlNode> children = new ArrayList<XmlNode>();
    protected final Hashtable<String, String> attributes = new Hashtable<String, String>();

    public abstract String toXml();

    protected String toXml(String nodename) {
        final StringBuilder xmlstring = new StringBuilder();
        xmlstring.append("\n");
        xmlstring.append("<");
        xmlstring.append(nodename);
        appendAttributes(attributes, xmlstring);
        if (children.size() > 0) {
            xmlstring.append(">");
            for (XmlNode childrennode : children) {
                xmlstring.append(childrennode.toXml());
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

    protected void appendAttributes(Hashtable<String, String> attributes, StringBuilder sb) {
        final Enumeration attribsenum = attributes.keys();
        while (attribsenum.hasMoreElements()) {
            String attributeKey = (String) attribsenum.nextElement();
            String attributeValue = attributes.get(attributeKey);
            sb.append(' ');
            sb.append(attributeKey);
            sb.append("=\"");
            sb.append(scapexml(attributeValue));
            sb.append('"');
        }
    }

    protected String scapexml(String str) {
        final StringBuilder xmlstring = new StringBuilder();
        for (char c : str.toCharArray()) {
            switch (c) {
                case '<': {
                    xmlstring.append("&lt;");
                    break;
                }
                case '>': {
                    xmlstring.append("&gt;");
                    break;
                }
                case '&': {
                    xmlstring.append("&amp;");
                    break;
                }
                case '"': {
                    xmlstring.append("&quot;");
                    break;
                }
                case '\r':
                case '\n': {
                }
                default: {
                    xmlstring.append(c);
                }
            }
        }
        return xmlstring.toString();
    }

}
