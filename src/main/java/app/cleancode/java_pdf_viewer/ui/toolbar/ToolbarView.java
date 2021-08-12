package app.cleancode.java_pdf_viewer.ui.toolbar;

import app.cleancode.java_pdf_viewer.ui.Main;
import app.cleancode.java_pdf_viewer.ui.dialog.GoToPageDialog;
import app.cleancode.java_pdf_viewer.ui.pdf.PDFView;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfOutline;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class ToolbarView extends MenuBar {
    public ToolbarView() {
        MenuItem open = new MenuItem();
        open.setText("Open");
        open.setOnAction(evt -> PDFView.open());
        Main.top.getScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN), () -> open.fire());
        Menu outlineMenu = new Menu();
        outlineMenu.setText("Document Outline");
        PDFView.INSTANCE.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                outlineMenu.getItems().clear();
                List<PdfOutline> outlines = newValue.getOutline().getAllChildren();
                for (PdfOutline outline : outlines) {
                    addOutlineElements(outlineMenu, outline);
                }
            }
        });
        Menu goTo = new Menu();
        goTo.setText("Go to");
        MenuItem goToPage = new MenuItem();
        goToPage.setText("Page");
        goToPage.setOnAction(evt -> {
            new GoToPageDialog();
        });
        Main.top.getScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.G, KeyCombination.SHORTCUT_DOWN),
                () -> goToPage.fire());
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().add(open);
        Menu viewMenu = new Menu("View");
        viewMenu.getItems().add(outlineMenu);
        viewMenu.getItems().add(goTo);
        goTo.getItems().add(goToPage);
        super.getMenus().add(fileMenu);
        super.getMenus().add(viewMenu);
        super.setFocusTraversable(true);
    }

    private void addOutlineElements(Menu outlineMenu, PdfOutline outline) {
        Menu outlineSubMenu = null;
        MenuItem outlineItem = new MenuItem();
        if (outline.getAllChildren() != null && !outline.getAllChildren().isEmpty()) {
            outlineSubMenu = new Menu();
            outlineMenu.getItems().add(outlineSubMenu);
            outlineSubMenu.getItems().add(outlineItem);
            outlineSubMenu.textProperty().bind(outlineItem.textProperty());
        } else {
            outlineMenu.getItems().add(outlineItem);
        }
        int page = PDFView.INSTANCE.get()
                .getPageNumber((PdfDictionary) outline.getDestination().getDestinationPage(
                        PDFView.INSTANCE.get().getCatalog().getNameTree(PdfName.Dests).getNames()));
        outlineItem.setText(String.format("%s (Page %d)", outline.getTitle(), page));
        outlineItem.setOnAction(evt -> {
            PDFView.INSTANCE.get().selectPage(page);
        });
        List<PdfOutline> children = outline.getAllChildren();
        if (outlineSubMenu != null) {
            for (PdfOutline child : children) {
                addOutlineElements(outlineSubMenu, child);
            }
        }
    }
}
