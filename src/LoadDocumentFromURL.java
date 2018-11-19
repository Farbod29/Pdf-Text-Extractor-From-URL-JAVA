import com.gnostice.pdfone.PdfDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

public class LoadDocumentFromURL {

    public static void main(String[] args) throws IOException {

        URL url1 = new URL("https://arxiv.org/pdf/1811.04152.pdf");
        byte[] ba1 = new byte[1024];
        int baLength;
        FileOutputStream fos1 = new FileOutputStream("download.pdf");
        try {
            // Contacting the URL
            // System.out.print("Connecting to " + url1.toString() + " ... ");
            URLConnection urlConn = url1.openConnection();
            // Checking whether the URL contains a PDF
            if (!urlConn.getContentType().equalsIgnoreCase("application/pdf")) {
                System.out.println("FAILED.\n[Sorry. This is not a PDF.]");
            } else {
                try {
                    // Read the PDF from the URL and save to a local file
                    InputStream is1 = url1.openStream();
                    while ((baLength = is1.read(ba1)) != -1) {
                        fos1.write(ba1, 0, baLength);
                    }
                    fos1.flush();
                    fos1.close();
                    is1.close();

                    // Load the PDF document and display its page count
                    //System.out.print("DONE.\nProcessing the PDF ... ");
                    PdfDocument doc = new PdfDocument();
                    try {
                        doc.load("download.pdf");
                        // System.out.println("DONE.\nNumber of pages in the PDF is " + doc.getPageCount());
                        // System.out.println(doc.getAuthor());
                        // System.out.println(doc.getKeywords());
                        // System.out.println(doc.toString());
                        doc.close();
                    } catch (Exception e) {
                        System.out.println("FAILED.\n[" + e.getMessage() + "]");
                    }

                } catch (ConnectException ce) {
                    //System.out.println("FAILED.\n[" + ce.getMessage() + "]\n");
                }
            }
        } catch (NullPointerException npe) {
            //System.out.println("FAILED.\n[" + npe.getMessage() + "]\n");
        }
        File file = new File("/Users/farbodaprin/Desktop/MASTER THISES/other/Master resources/SALMON/SALMON-BACKEND/TextAlanyizerMicroServise/ExtractPdf/download.pdf");
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        System.out.println(text);
        document.close();
    }
}