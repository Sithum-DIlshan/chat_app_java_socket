package chat_app.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashSet;

/**
 * @author sithum
 */
public class Server {
    private static final int PORT = 3000;
    private static HashSet<PrintWriter> writers = new HashSet<>();

    public static void main(String[] args) throws IOException {

        System.out.println("chat server is running");
        ServerSocket listener = new ServerSocket(PORT);
        while (true){
            Socket socket = listener.accept();
            Thread handlerThread = new Thread(new Handler(socket));
            handlerThread.start();
        }

    }

    private static class Handler implements Runnable{
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream1 = socket.getOutputStream();
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStreamWriter outputStream = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                out=new PrintWriter(outputStream, true);

                writers.add(out);

                while (true){
                    String input = in.readLine();

                    for (PrintWriter writer : writers){
                        writer.println(input);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                if (out != null){
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
