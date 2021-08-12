package app.cleancode.java_pdf_viewer.ui;

import app.cleancode.java_pdf_viewer.prefs.PreferenceManager;
import app.cleancode.java_pdf_viewer.ui.dialog.ErrorDialog;
import app.cleancode.java_pdf_viewer.ui.pdf.PDFView;
import app.cleancode.java_pdf_viewer.ui.toolbar.ToolbarView;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage top;

    private static String commandLineDocumentPath = null;

    public static void main(String[] args) {
        for (String arg : args) {
            if (new File(arg).exists()) {
                commandLineDocumentPath = arg;
                break;
            }
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        top = primaryStage;
        BorderPane root = new BorderPane();
        primaryStage.setScene(new Scene(root));
        try {
            ToolbarView toolbar = new ToolbarView();
            root.setTop(toolbar);
            PDFView.INSTANCE.addListener((observable, oldValue, newValue) -> {
                root.setCenter(newValue);
            });
            primaryStage.setTitle("Pdf Viewer");
            primaryStage.setMaximized(true);
            primaryStage.show();
            String documentPath = null;
            if (PreferenceManager.isPresent(PreferenceManager.LAST_DOCUMENT)) {
                documentPath = PreferenceManager.getString(PreferenceManager.LAST_DOCUMENT);
            }
            if (commandLineDocumentPath != null) {
                if (documentPath != commandLineDocumentPath) {
                    documentPath = commandLineDocumentPath;
                }
            }
            if (documentPath != null && new File(documentPath).exists()) {
                PDFView.open(new File(documentPath));
            }
        } catch (Throwable e) {
            new ErrorDialog(e);
            System.exit(-1);
        }
    }

}
