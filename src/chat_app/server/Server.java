package chat_app.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * @author sithum
 */
public class Server {
    private static final int PORT = 3000;
    private static HashSet<PrintWriter> writers = new HashSet<>();
    private static HashSet<String> names = new HashSet<>();

    public static void main(String[] args) throws IOException {
        /*ServerSocket serverSocket = new ServerSocket(3000);
        Socket accept = serverSocket.accept();
        System.out.println("client connected");
        while(true) {
            InputStreamReader inputStream =
                    new InputStreamReader(accept.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStream);
            System.out.println(bufferedReader.readLine());
        }*/
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
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new PrintWriter(socket.getOutputStream(), true);

                writers.add(out);
                while (true){
                    out.println("NAME");
                    name = in.readLine();
//                    String input = in.readLine();
                    if (name == null){
                        return;
                    }
                    if (!names.contains(name)){
                        names.add(name);
                        break;
                    }
//                    for (PrintWriter writer: writers){
//                        writer.println("MESSAGE"+"saman: "+input);
//                    }
                }
                out.println("NAMEACCEPTED");
                writers.add(out);

//                while (true){
                    String input  = in.readLine();
                    if (input == null){
                        return;
                    }
                    for (PrintWriter writer: writers){
                        writer.println("MESSAGE"+name+": "+input);
                    }
//                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
