package app.cleancode.java_pdf_viewer.ui.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public abstract class DialogViewBase {
    private Dialog<ButtonType> dialog;

    public DialogViewBase(String title, String content, ButtonType... buttonTypes) {
        try {
            dialog = new Dialog<>();
            dialog.setTitle(title);
            dialog.setContentText(content);
            dialog.getDialogPane().getButtonTypes().addAll(buttonTypes);
            dialog.getDialogPane().getChildren().add(getRoot());
            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract Parent getRoot();

    protected void registerButtonActionHandler(ButtonType button,
            EventHandler<ActionEvent> handler) {
        dialog.getDialogPane().lookupButton(button).addEventFilter(ActionEvent.ACTION, handler);
    }
}
