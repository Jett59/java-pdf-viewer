package app.cleancode.java_pdf_viewer.ui.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfCatalog;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfReader;
import app.cleancode.java_pdf_viewer.parser.TextGenerator;
import app.cleancode.java_pdf_viewer.prefs.PreferenceManager;
import app.cleancode.java_pdf_viewer.ui.Main;
import app.cleancode.java_pdf_viewer.ui.dialog.ErrorDialog;
import javafx.application.Platform;
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

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (INSTANCE.get() != null) {
                PreferenceManager.put(PreferenceManager.LAST_DOCUMENT, INSTANCE.get().path);
                PreferenceManager.put(
                        String.format(PreferenceManager.LAST_PAGE_NUMBER, INSTANCE.get().path),
                        INSTANCE.get().currentPage);
            }
        }));
    }

    public static void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new ExtensionFilter("PDF", List.of("pdf")));
        File file = fileChooser.showOpenDialog(Main.top);
        open(file);
    }

    public static void open(File file) {
        FileInputStream fileIn = null;
        PdfReader pdfReader = null;
        PdfDocument pdf = null;
        try {
            fileIn = new FileInputStream(file);
            pdfReader = new PdfReader(fileIn);
            pdf = new PdfDocument(pdfReader);
            INSTANCE.setValue(new PDFView(pdf, file.getAbsolutePath()));
            Main.top.setTitle(file.getName() + " - PDF Viewer");
        } catch (Throwable e) {
            e.printStackTrace();
            new ErrorDialog(e);
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
    private String path;

    public PDFView(PdfDocument pdf, String path) throws Exception {
        this.pdfText = new TextArea();
        this.path = path;
        this.pdf = pdf;
        textGenerator = new TextGenerator();
        pdfText.setEditable(false);
        Platform.runLater(() -> {
            selectPage(PreferenceManager
                    .getInt(String.format(PreferenceManager.LAST_PAGE_NUMBER, path)));
        });
        Button previous = new Button("_Previous Page");
        Button next = new Button("_Next Page");
        previous.setOnAction(evt -> {
            selectPage(currentPage - 1);
        });
        next.setOnAction(evt -> {
            selectPage(currentPage + 1);
        });
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(previous);
        getChildren().add(pdfText);
        getChildren().add(next);
    }

    public void selectPage(int page) {
        page = page > 0 ? page : 1;
        if (pdf.getNumberOfPages() >= page) {
            try {
                pdfText.requestFocus();
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

    public String getText() {
        return pdfText.getText();
    }

    public void setPosition(int charPosition) {
        pdfText.positionCaret(charPosition);
    }
}
