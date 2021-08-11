package app.cleancode.java_pdf_viewer.ui;

import app.cleancode.java_pdf_viewer.prefs.PreferenceManager;
import app.cleancode.java_pdf_viewer.ui.pdf.PDFView;
import app.cleancode.java_pdf_viewer.ui.toolbar.ToolbarView;
import java.io.File;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage top;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        top = primaryStage;
        BorderPane root = new BorderPane();
        primaryStage.setScene(new Scene(root));
        ToolbarView toolbar = new ToolbarView();
        root.setTop(toolbar);
        PDFView.INSTANCE.addListener((observable, oldValue, newValue) -> {
            root.setCenter(newValue);
        });
        primaryStage.setTitle("Pdf Viewer");
        primaryStage.setMaximized(true);
        primaryStage.show();
        if (PreferenceManager.isPresent(PreferenceManager.LAST_DOCUMENT)) {
            PDFView.open(new File(PreferenceManager.getString(PreferenceManager.LAST_DOCUMENT)));
            PDFView.INSTANCE.get()
                    .selectPage(PreferenceManager.getInt(PreferenceManager.LAST_PAGE_NUMBER));
        }
    }

}
