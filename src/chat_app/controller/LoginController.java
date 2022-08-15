package chat_app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * @author sithum
 */
public class LoginController {
    private static final HashSet<String> names = new HashSet<>();
    public TextField txtUserName;
    private static double x, y;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText() != null && !names.contains(txtUserName.getText())) {
            names.add(txtUserName.getText());
            System.out.println(txtUserName.getText() + " logged");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chat_app/views/Chat.fxml"));
            Parent load = fxmlLoader.load();
            Chat controller = fxmlLoader.getController();
            controller.setUserName(txtUserName.getText());
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            load.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            load.setOnMouseDragged(event -> {

                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);

            });
        }
    }
}
