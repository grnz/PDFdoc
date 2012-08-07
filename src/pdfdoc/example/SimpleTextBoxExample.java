package pdfdoc.example;

import com.pdflib.PDFlibException;
import pdfdoc.builder.PDF;
import pdfdoc.builder.Page;
import pdfdoc.builder.TextBlock;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author gonka
 * @since 29/07/2012
 *        <p/>
 *        Simple Text Box example.
 *        How to create a document with some text.
 */
public class SimpleTextBoxExample {

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

        //Set the text box content
        text.setText("This is SimpleTextBoxExample text!");

        //Create the file on the disk
        pdf.makeFile("SimpleTextBoxExample.pdf");

        // or create an in memory stream
        InputStream inputStream = pdf.makeInputStream();

        System.out.println("done !");
    }

}
