package chat_app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author sithum
 */
public class Chat implements Initializable {
    public JFXButton uploadPhoto;
    public JFXButton sendMessage;
    public TextArea textArea;
    public JFXTextField txtMsg;
    private String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        uploadPhoto.setGraphic(new ImageView("chat_app/Untitled design.gif"));
    }

    public void uploadPhoto(ActionEvent actionEvent) {

    }

    public void sendMessageOnAction(ActionEvent actionEvent) {
        textArea.setText(username+" : "+txtMsg.getText());
    }

    public void setUserName(String username){
        this.username = username;
    }

}
