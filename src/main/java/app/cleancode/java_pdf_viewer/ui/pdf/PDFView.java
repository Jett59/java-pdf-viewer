package app.cleancode.java_pdf_viewer.ui.pdf;

import app.cleancode.java_pdf_viewer.ui.Main;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class PDFView extends TextArea {
    public static ObjectProperty<PDFView> INSTANCE = new SimpleObjectProperty<>();

    public static void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new ExtensionFilter("PDF", List.of("pdf")));
        File file = fileChooser.showOpenDialog(Main.top);
        FileInputStream fileIn = null;
        PdfReader pdfReader = null;
        PdfDocument pdf = null;
        try {
            fileIn = new FileInputStream(file);
            pdfReader = new PdfReader(fileIn);
            pdf = new PdfDocument(pdfReader);
            INSTANCE.set(new PDFView(pdf));
        } catch (Exception e) {
            e.printStackTrace();
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (pdfReader != null) {
                    try {
                        pdfReader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    if (pdf != null) {
                        pdf.close();
                    }
                }
            }
        }
    }

    private final PdfDocument pdf;

    public PDFView(PdfDocument pdf) {
        this.pdf = pdf;
        System.out.println(pdf.getNumberOfPages());
        super.setEditable(false);
    }
}
