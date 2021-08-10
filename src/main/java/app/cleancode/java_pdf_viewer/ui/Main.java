package app.cleancode.java_pdf_viewer.ui;

import app.cleancode.java_pdf_viewer.ui.pdf.PDFView;
import app.cleancode.java_pdf_viewer.ui.toolbar.ToolbarView;
import javafx.application.Application;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
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
        ToolbarView toolbar = new ToolbarView();
        toolbar.setAccessibleRole(AccessibleRole.TOOL_BAR);
        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        PDFView.INSTANCE.addListener((observable, newValue, oldValue) -> {
            root.setCenter(newValue);
        });
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Pdf Viewer");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}
