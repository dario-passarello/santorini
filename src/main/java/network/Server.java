package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public final static int SERVER_SOCKET_PORT = 49231;

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(SERVER_SOCKET_PORT);
        } catch(IOException e) {
            System.out.println("Error: Can't Open Server Socket");
            System.exit(1);
            return;
        }
        while (true) {
            try {
                Socket client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler, "server_handler_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("Connection Dropped");
            }
        }
    }
}
