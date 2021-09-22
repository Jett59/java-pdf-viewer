package app.cleancode.java_pdf_viewer.ui.dialog;

import app.cleancode.java_pdf_viewer.ui.pdf.PDFView;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class FindInPageDialog extends DialogViewBase {

    public FindInPageDialog() {
        super("Find in page", "Type the text you want to find:", ButtonType.CANCEL,
                GoToPageDialog.GO_BUTTON);
    }

    @Override
    protected Parent getRoot() {
        TextField text = new TextField();
        registerButtonActionHandler(GoToPageDialog.GO_BUTTON, evt -> {
            int position = PDFView.INSTANCE.get().getText().indexOf(text.getText());
            if (position >= 0) {
                PDFView.INSTANCE.get().setPosition(position);
            } else {
                evt.consume();
                new ErrorDialog(
                        new Exception("The string '" + text.getText() + "' could not be found"));
            }
        });
        return new BorderPane(text);
    }

}
