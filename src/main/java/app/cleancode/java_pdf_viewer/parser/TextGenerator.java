package app.cleancode.java_pdf_viewer.parser;

import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

public class TextGenerator {
    public String getText(PdfPage page) throws Exception {
        return PdfTextExtractor.getTextFromPage(page);
    }
}
