package chat_app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

/**
 * @author sithum
 */
public class Chat implements Initializable {
    public JFXButton uploadPhoto;
    public JFXButton sendMessage;
    public TextArea textArea;
    public JFXTextField txtMsg;
    public JFXButton minimizeBtn;
    //    Socket socket = null;
    private String username;
    BufferedReader in;
    PrintWriter out;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            Font f  = Font.loadFont(new FileInputStream(new File("./src/chat_app/fonts/OpenSansEmoji    .ttf")), 12);
//            txtMsg.setFont(f);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        uploadPhoto.setGraphic(new ImageView("chat_app/Untitled design.gif"));
        minimizeBtn.setGraphic(new ImageView("chat_app/icons8-drop-down-30.png"));
        new Thread(() ->{
            try {
                run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void uploadPhoto(ActionEvent actionEvent) {

    }

    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
       /* textArea.appendText(username + " : " + txtMsg.getText() + "\n\n");
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println(txtMsg.getText());
        printWriter.flush();*/
        out.println(username + " : " + txtMsg.getText()+"\n\n");
        out.flush();
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void run() throws IOException {
        try {
            String serverAddress = "localhost";
            Socket socket = new Socket(serverAddress, 3000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new PrintWriter(socket.getOutputStream(), true);

            while(true){
                String line = in.readLine();
                Platform.runLater(()->{
                    textArea.appendText(line);
                    textArea.appendText("\n");
                    byte[] emojiByteCode = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x81};
                    String emoji = new String(emojiByteCode, Charset.forName("UTF-8"));

                });

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void minimizeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) txtMsg.getScene().getWindow();
        stage.setIconified(true);
    }
}
