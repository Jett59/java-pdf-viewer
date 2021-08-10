package app.cleancode.java_pdf_viewer.ui.toolbar;

import app.cleancode.java_pdf_viewer.ui.pdf.PDFView;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ToolbarView extends HBox {
    public ToolbarView() {
        Button open = new Button("Open");
        open.setOnAction(evt -> PDFView.open());
        getChildren().add(open);
    }
}
