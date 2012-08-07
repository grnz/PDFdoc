package pdfdoc.example;

import com.pdflib.PDFlibException;
import pdfdoc.builder.PDF;
import pdfdoc.builder.Page;
import pdfdoc.builder.StyleMacro;
import pdfdoc.builder.TextBlock;

import java.io.IOException;

/**
 * @author gonka
 * @since 31/07/2012
 *        <p/>
 *        A text inline formatting example.
 *        How to format text and combine styles.
 */
public class InlineTextFormattingExample {

    public static void main(String[] args) throws PDFlibException, IOException {
        //Create a PDF
        PDF pdf = new PDF();

        //Create page
        Page page = pdf.createPage();
        page.setWidth(400);
        page.setHeight(400);

        //Create a text block
        TextBlock text = page.createTextBlock();
        //Set block left upper corner position
        text.setX(20);
        text.setY(20);
        //Set box dimensions
        text.setWidth(360);
        text.setHeight(200);

        //Create some styles:
        StyleMacro regular = new StyleMacro("regular");
        regular.setFontName("Arial");
        regular.setFontSize(10);
        regular.setEncoding("unicode");
        regular.setFillColor("spot(PANTONE 286 C, 100)");
        //Set as default macro
        text.setDefaultMacro(regular);

        StyleMacro big = new StyleMacro("big");
        big.setFontSize(20);
        big.setFillColor("cmyk(0,100,100,0)");
        big.setStrokeColor("cmyk(0,100,100,0)");
        //Add to the list of macros
        text.addMacro(big);

        StyleMacro green = new StyleMacro("green");
        green.setFillColor("cmyk(100,0,100,0)");
        text.addMacro(green);

        //Set the content with inline formatting tags
        text.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " <big>Donec <green>luctus</green> elit eu quam congue interdum.</big>" +
                " Ut vitae ligula nec augue accumsan pretium. Morbi ultrices eros et " +
                " ligula commodo ullamcorper.Curabitur a arcu quis tellus feugiat faucibus." +
                " <green>Proin et libero massa.</green> Vivamus facilisis sodales mollis");

        //Create the file on the disk
        pdf.makeFile("InlineTextFormattingExample.pdf");

        System.out.println("done !");
    }

}
