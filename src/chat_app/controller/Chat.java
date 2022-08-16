package chat_app.controller;

import chat_app.utils.CheckString;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * @author sithum
 */
public class Chat implements Initializable {
    public static final HashSet<Socket> sockets = new HashSet<>();
    public JFXButton uploadPhoto;
    public JFXButton sendMessage;
    public TextArea textArea;
    public JFXTextField txtMsg;
    public JFXButton minimizeBtn;
    public TextFlow txtFlow;
    public ScrollPane scrollPane;
    BufferedReader in;
    PrintWriter out;
//    InputStream inputStream;
    BufferedInputStream bufferedInputStream;
    BufferedImage bufferedImage;
    private OutputStream outputStream;
    //    Socket socket = null;
    private String username;
    private FileChooser fileChooser;
    private File file;
    private FileInputStream fis;
    //    private Image image;
    private Socket socket;
    Thread thread;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        uploadPhoto.setGraphic(new ImageView("chat_app/Untitled design.gif"));
        minimizeBtn.setGraphic(new ImageView("chat_app/icons8-drop-down-30.png"));
        thread = new Thread(() -> {
            try {
                run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    public void uploadPhoto(ActionEvent actionEvent) throws IOException {
       /* outFile = socket.getOutputStream();
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
*/
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        Stage stage = (Stage) txtMsg.getScene().getWindow();

        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            outputStream = socket.getOutputStream();
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);

            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            outputStream.write(size);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            System.out.println("Flushed: " + System.currentTimeMillis());
        }
    }

    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
        String msg = txtMsg.getText();
        if (msg.equals("exit")){
            Stage stage = (Stage) txtMsg.getScene().getWindow();
            stage.close();
        }else {
            out.println(username + " : " + msg + "\n");
            out.flush();
            txtMsg.setText("");
        }
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void run() throws IOException {
        try {
            String serverAddress = "localhost";
            socket = new Socket(serverAddress, 3000);
            InputStream inputStream = socket.getInputStream();
            System.out.println(inputStream.markSupported());
            sockets.add(socket);
            in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
            /*    String line = in.readLine();
                if (CheckString.isAsciiPrintable(line)) {
                    Platform.runLater(() -> {
                        Text txt = new Text("\n\n" + line);
                        txtFlow.getChildren().add(txt);
                    });

                } else {
                    InputStream inputStream1 = socket.getInputStream();
                    byte[] sizeAr = new byte[4];
                    inputStream1.read(sizeAr);
                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                    byte[] imageAr = new byte[size];
                    inputStream1.read(imageAr);

                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

                    System.out.println(image == null);
                    Platform.runLater(() -> {

                        System.out.println("image");
                        Image chatImage = SwingFXUtils.toFXImage(image, null);
                        ImageView imageView = new ImageView();
                        imageView.setImage(chatImage);
                        txtFlow.getChildren().add(imageView);

                    });
                }*/
                String line = in.readLine();
                Platform.runLater(() -> {
                    Text txt = new Text("\n\n" + line);
                    txtFlow.getChildren().add(txt);
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

