package chat_app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    public TextFlow txtFlow;
    public ScrollPane scrollPane;
    BufferedReader in;
    PrintWriter out;
    OutputStream outFile;
    InputStream inputStream;
    BufferedInputStream bufferedInputStream;
    BufferedImage bufferedImage;
    //    Socket socket = null;
    private String username;
    private FileChooser fileChooser;
    private File file;
    private FileInputStream fis;
    //    private Image image;
    private Socket socket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        uploadPhoto.setGraphic(new ImageView("chat_app/Untitled design.gif"));
        minimizeBtn.setGraphic(new ImageView("chat_app/icons8-drop-down-30.png"));
        new Thread(() -> {
            try {
                run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void uploadPhoto(ActionEvent actionEvent) throws IOException {
        outFile = socket.getOutputStream();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        Stage stage = (Stage) txtMsg.getScene().getWindow();

        file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Desktop desktop = Desktop.getDesktop();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outFile);
            Image image = ImageIO.read(file);
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bufferedImage.createGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();

            ImageIO.write(bufferedImage, "png", bufferedOutputStream);

        }

    }

    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
        out.println(username + " : " + txtMsg.getText() + "\n");
        out.flush();
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void run() throws IOException {
        try {
            String serverAddress = "localhost";
            socket = new Socket(serverAddress, 3000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String line = in.readLine();
                Platform.runLater(() -> {
                    Text txt = new Text("\n\n" + line);
                    txtFlow.getChildren().add(txt);
                    scrollPane.setVvalue(1.0);
                    scrollPane.setFitToWidth(true);

                });
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void minimizeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) txtMsg.getScene().getWindow();
        stage.setIconified(true);
    }
}
