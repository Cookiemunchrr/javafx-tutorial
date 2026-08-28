import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import quu.Quu;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Quu quu;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image quuImage = new Image(this.getClass().getResourceAsStream("/images/DaQuu.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Quu instance and shows its opening messages.
     *
     * <p>The greeting is shown here rather than in {@link #initialize()} because it comes
     * from the chatbot, which does not exist yet when the FXML is loaded.
     *
     * @param q the chatbot this window talks to
     */
    public void setQuu(Quu q) {
        quu = q;
        // No command has run yet, so getCommandType() is still "none" and these two
        // boxes keep the default reply colour.
        String commandType = quu.getCommandType();
        dialogContainer.getChildren().add(DialogBox.getQuuDialog(quu.getGreeting(), quuImage, commandType));
        if (!quu.getLoadMessage().isEmpty()) {
            dialogContainer.getChildren().add(DialogBox.getQuuDialog(quu.getLoadMessage(), quuImage, commandType));
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Quu's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = quu.getResponse(input);
        String commandType = quu.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getQuuDialog(response, quuImage, commandType)
        );
        userInput.clear();
    }
}
