package app.cleancode.java_pdf_viewer.ui.dialog;

import app.cleancode.java_pdf_viewer.ui.pdf.PDFView;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class GoToPageDialog extends DialogueViewBase {
    public static ButtonType GO_BUTTON = new ButtonType("Go", ButtonData.OK_DONE);

    public GoToPageDialog() {
        super("Go to Page", "Type a page here to jump to it:", ButtonType.CANCEL, GO_BUTTON);
    }

    @Override
    protected Parent getRoot() {
        TextField pageNumber = new TextField();
        registerButtonActionHandler(GO_BUTTON, evt -> {
            if (pageNumber.getText().matches("\\d*")) {
                PDFView.INSTANCE.get().selectPage(Integer.parseInt(pageNumber.getText()));
            } else {
                evt.consume();
            }
        });
        pageNumber.setText(Integer.toString(PDFView.INSTANCE.get().getCurrentPage()));
        return new BorderPane(pageNumber);
    }

}
