package pdfdoc.example;

import com.pdflib.PDFlibException;
import pdfdoc.builder.ImageBlock;
import pdfdoc.builder.PDF;
import pdfdoc.builder.Page;

import java.io.IOException;

/**
 * @author gonka
 * @since 31/07/2012
 *        <p/>
 *        Simple image box example.
 *        How to insert images into a PDF document.
 */
public class SimpleImageBoxExample {

    public static void main(String[] args) throws PDFlibException, IOException {
        //Create a PDF
        PDF pdf = new PDF();

        //Create page
        Page page = pdf.createPage();
        page.setWidth(400);
        page.setHeight(400);

        //Create a image block
        ImageBlock image = page.createImageBlock();
        //Sets box dimensions
        image.setX(100);
        image.setY(100);
        image.setWidth(200);
        image.setHeight(200);

        //Set some image placement options
        image.setFitMethod("meet");
        image.setHorizontalAlignment("center");

        //Set the paths to the image file to be inserted
        image.setPath("resource/img/palm.jpg");

        //Generate the pdf file on the disc
        pdf.makeFile("SimpleImageBoxExample.pdf");

        System.out.println("done !");
    }

}
