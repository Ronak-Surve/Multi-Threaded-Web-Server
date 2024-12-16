import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    Consumer<Socket> getServer() {

        return (clientSocket) -> {
            try {
                PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader fromSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                toSocket.println("Hello from Server");
                toSocket.flush();
                String line = fromSocket.readLine();

                System.out.println("Message from Client : " + line);
                toSocket.close();
                fromSocket.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static void main(String[] args) {

        int port = 8000;
        Server server = new Server();
        try(ServerSocket socket = new ServerSocket(port)) {
            socket.setSoTimeout(10000);
            System.out.println("System is listening on port " + port);
            while (true) {
                Socket connection = socket.accept();
                Thread t = new Thread(() -> server.getServer().accept(connection));
                t.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
