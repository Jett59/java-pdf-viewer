package app.cleancode.java_pdf_viewer.ui.pdf;

import app.cleancode.java_pdf_viewer.parser.TextGenerator;
import app.cleancode.java_pdf_viewer.ui.Main;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfCatalog;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfReader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class PDFView extends HBox {
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
            INSTANCE.setValue(new PDFView(pdf));
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

    private final TextArea pdfText;
    private final PdfDocument pdf;
    private final TextGenerator textGenerator;
    private int currentPage;

    public PDFView(PdfDocument pdf) throws Exception {
        this.pdfText = new TextArea();
        this.pdf = pdf;
        textGenerator = new TextGenerator();
        pdfText.setEditable(false);
        selectPage(1);
        Button previous = new Button("_Previous Page");
        Button next = new Button("_Next Page");
        previous.setOnAction(evt -> {
            pdfText.requestFocus();
            selectPage(currentPage - 1);
        });
        next.setOnAction(evt -> {
            pdfText.requestFocus();
            selectPage(currentPage + 1);
        });
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(previous);
        getChildren().add(pdfText);
        getChildren().add(next);
    }

    public void selectPage(int page) {
        if (pdf.getNumberOfPages() >= page && page > 0) {
            try {
                pdfText.setText(textGenerator.getText(pdf.getPage(page)));
                currentPage = page;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getNumberOfPages() {
        return pdf.getNumberOfPages();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public PdfOutline getOutline() {
        return pdf.getOutlines(true);
    }

    public PdfCatalog getCatalog() {
        return pdf.getCatalog();
    }

    public int getPageNumber(PdfDictionary dictionary) {
        return pdf.getPageNumber(dictionary);
    }
}
