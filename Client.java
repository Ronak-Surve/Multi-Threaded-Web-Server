import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    Runnable task = () -> {
            int port = 8000;

            try {
                InetAddress address = InetAddress.getLocalHost();
                Socket socket = new Socket(address,port);
                try {
                    PrintWriter toServer = new PrintWriter(socket.getOutputStream());
                    BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    toServer.println("Hello from Client");
                    toServer.flush();
                    String line = fromServer.readLine();
                    System.out.println("Message from Server : " + line);
                    toServer.close();
                    fromServer.close();
                    socket.close();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

    public static void main(String[] args) {

        Client client = new Client();
        for(int i = 0; i < 1000; i++) {
                Thread thread = new Thread(client.task);
                thread.start();
        }
    }
}
