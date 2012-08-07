package pdfdoc.example;

import com.pdflib.PDFlibException;
import pdfdoc.builder.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author gonka
 * @since 31/07/2012
 *        An advanced text box example.
 *        How to add several box decorations, rotation, frame and background.
 */
public class AdvancedTextBoxExample {

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
        text.setX(120);
        text.setY(200);
        //Set box dimensions
        text.setWidth(100);
        text.setHeight(100);

        //Alignment options
        text.setVerticalAlignment("center");
        text.setFitMethod("nofit");

        //Rotation options
        text.setRotation(45);

        //Add a frame to the box
        Frame fsc = text.createFrame();
        fsc.setType(PdfStatics.FRAME_BLOCK);
        fsc.setColor(new Color("rgb(255,0,0)"));
        fsc.setLineJoin(1);
        fsc.setLineCap(1);
        fsc.setLineWidth(2);

        Background back = text.createBackground();
        back.setType(PdfStatics.BACKGROUND_BLOCK);
        back.setColor(new Color("cmyk (60,6,11,0)"));

        //Set the text box content
        text.setText("This is AdvancedTextBoxExample text!");

        //Create the file on the disk
        pdf.makeFile("AdvancedTextBoxExample.pdf");

        // or create an in memory stream
        InputStream inputStream = pdf.makeInputStream();

        System.out.println("done !");
    }

}
