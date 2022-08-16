package chat_app.server;

import chat_app.utils.CheckString;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

/**
 * @author sithum
 */
public class Server {
    private static final int PORT = 3000;
    private static final HashSet<PrintWriter> writers = new HashSet<>();
    private static final HashSet<OutputStream> outputStreams = new HashSet<>();

    public static void main(String[] args) throws IOException {

        System.out.println("chat server is running");
        ServerSocket listener = new ServerSocket(PORT);
        while (true) {
            Socket socket = listener.accept();
            Thread handlerThread = new Thread(new Handler(socket));
            handlerThread.start();
        }

    }

    private static class Handler implements Runnable {
        private final Socket socket;
        private String name;
        private BufferedReader in;
        private PrintWriter out;
        private OutputStream outputStream;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                outputStream = socket.getOutputStream();
                in = new BufferedReader(new InputStreamReader(inputStream));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
                out = new PrintWriter(outputStreamWriter, true);

                writers.add(out);
                outputStreams.add(outputStream);

                System.out.println("Reading: " + System.currentTimeMillis());


                while (true) {
               /*     String input = in.readLine();
                    System.out.println(CheckString.isAsciiPrintable(input));
                    if (CheckString.isAsciiPrintable(input)) {
                        for (PrintWriter writer : writers) {
                            writer.println(input);
                        }
                    } else {
                        System.out.println(inputStream);
                        InputStream inputStream1 = socket.getInputStream();
                        byte[] sizeAr = new byte[4];
                        inputStream1.read(sizeAr);
                        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                        byte[] imageAr = new byte[size];
                        inputStream1.read(imageAr);

                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

//                    System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());

                        for (OutputStream ou : outputStreams) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image, "jpg", byteArrayOutputStream);

                            byte[] size1 = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                            ou.write(size1);
                            ou.write(byteArrayOutputStream.toByteArray());
                            ou.flush();
                        }
                    }*/
                    String input = in.readLine();
                        for (PrintWriter writer : writers) {
                            writer.println(input);
                        }
                }


//                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
