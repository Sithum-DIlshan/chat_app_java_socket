package chat_app.controller;

import chat_app.db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author sithum
 */
public class LoginController {
    public TextField txtUserName;


    public void loginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {


                if (rst.getString(1).equals(txtUserName.getText())){
                    System.out.println(txtUserName.getText()+" logged");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chat_app/views/Chat.fxml"));
                    Parent load = (Parent) fxmlLoader.load();
                    Chat controller = fxmlLoader.<Chat>getController();
                    controller.setUserName(txtUserName.getText());
                    Scene scene = new Scene(load);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.show();
                }
            }

    }
}
