package chat_app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
//    Socket socket = null;
    private String username;
    BufferedReader in;
    PrintWriter out;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        uploadPhoto.setGraphic(new ImageView("chat_app/Untitled design.gif"));
        try {
            run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadPhoto(ActionEvent actionEvent) {

    }

    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
       /* textArea.appendText(username + " : " + txtMsg.getText() + "\n\n");
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println(txtMsg.getText());
        printWriter.flush();*/
        out.println(txtMsg.getText());
        txtMsg.setText("");
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void run() throws IOException {
        try {
            String serverAddress = "localhost";
            Socket socket = new Socket(serverAddress, 3000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            int i = 0;
            /*while (i < 100){*/
                String line = in.readLine();
                if (line.startsWith("SUBMITNAME")){
                    out.println(username);
                } else if (line.startsWith("MESSAGE")) {
                    textArea.appendText(line+"\n");
                }
           /* i++;
            }*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
