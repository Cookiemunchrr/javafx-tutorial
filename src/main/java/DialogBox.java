import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Returns a dialog box for something the user typed.
     *
     * @param text the user's input
     * @param img the user's avatar
     * @return the dialog box
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Returns a dialog box for Quu's reply to a command, coloured according to the
     * kind of command it answered.
     *
     * @param text the reply
     * @param img Quu's avatar
     * @param commandType the category reported by {@code Quu.getCommandType()}
     * @return the dialog box
     */
    public static DialogBox getQuuDialog(String text, Image img, String commandType) {
        var db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }

    /**
     * Adds the style class that colours this dialog box for a given kind of command.
     *
     * <p>The class is added on top of {@code reply-label} rather than replacing it, so the
     * mirrored corner shape survives. Anything unrecognised, including the greeting's
     * {@code "none"}, is left with the default colour on purpose.
     *
     * @param commandType the category reported by {@code Quu.getCommandType()}
     */
    private void changeDialogStyle(String commandType) {
        switch (commandType) {
        case "add":
            dialog.getStyleClass().add("add-label");
            break;
        case "mark":
            dialog.getStyleClass().add("marked-label");
            break;
        case "unmark":
            dialog.getStyleClass().add("unmarked-label");
            break;
        case "delete":
            dialog.getStyleClass().add("delete-label");
            break;
        case "list":
            dialog.getStyleClass().add("list-label");
            break;
        case "find":
            dialog.getStyleClass().add("find-label");
            break;
        case "error":
            dialog.getStyleClass().add("error-label");
            break;
        default:
            // "none" and "exit" keep the default reply colour.
        }
    }
}
