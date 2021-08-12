package app.cleancode.java_pdf_viewer.ui.dialog;

import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class ErrorDialog extends DialogViewBase {
    private static Throwable exception;

    public ErrorDialog(Throwable exception) {
        this(ErrorDialog.exception = exception, true);
    }

    private ErrorDialog(Throwable exception, boolean closable) {
        super("Error", "An error occured", closable ? ButtonType.CLOSE : null);
    }

    @Override
    protected Parent getRoot() {
        TextField errorText = new TextField(exception.toString());
        errorText.setEditable(false);
        return new BorderPane(errorText);
    }
}
