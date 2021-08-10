package app.cleancode.java_pdf_viewer.ui.toolbar;

import app.cleancode.java_pdf_viewer.ui.pdf.PDFView;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfOutline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class ToolbarView extends HBox {
    public ToolbarView() {
        Button open = new Button("Open");
        open.setOnAction(evt -> PDFView.open());
        MenuButton outlineButton = new MenuButton("Document _Outline");
        PDFView.INSTANCE.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                outlineButton.getItems().clear();
                List<PdfOutline> outlines = newValue.getOutline().getAllChildren();
                for (PdfOutline outline : outlines) {
                    addOutlineElements(outlineButton, outline);
                }
            }
        });
        getChildren().add(open);
        getChildren().add(outlineButton);
        setAlignment(Pos.TOP_CENTER);
    }

    private void addOutlineElements(MenuButton outlineButton, PdfOutline outline) {
        MenuItem outlineItem = new MenuItem();
        int page = PDFView.INSTANCE.get()
                .getPageNumber((PdfDictionary) outline.getDestination().getDestinationPage(
                        PDFView.INSTANCE.get().getCatalog().getNameTree(PdfName.Dests).getNames()));
        outlineItem.setText(String.format("%s (Page %d)", outline.getTitle(), page));
        outlineItem.setOnAction(evt -> {
            PDFView.INSTANCE.get().selectPage(page);
        });
        outlineButton.getItems().add(outlineItem);
        List<PdfOutline> children = outline.getAllChildren();
        if (children != null) {
            for (PdfOutline child : children) {
                addOutlineElements(outlineButton, child);
            }
        }
    }
}
