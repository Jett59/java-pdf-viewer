package app.cleancode.java_pdf_viewer.ui.dialog;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;

public class FindInPageDialog extends DialogViewBase {

    public FindInPageDialog() {
        super("Find in page", "Type the text you want to find:", ButtonType.CANCEL,
                GoToPageDialog.GO_BUTTON);
    }

    @Override
    protected Parent getRoot() {
        Group root = new Group();
        return root;
    }

}
