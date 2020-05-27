package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable{

    public final static Logger logger = Logger.getLogger(Server.class.getName());

    public final static int SERVER_SOCKET_PORT = 12345;

    public static void main(String[] args) {
        logger.setLevel(Level.INFO);
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(SERVER_SOCKET_PORT);
        } catch(IOException e) {
            System.out.println("Error: Can't Open Server Socket");
            System.exit(1);
            return;
        }
        Thread lobby2 = new Thread(Lobby.getLobbyInstance(2),"server_lobby2");
        Thread lobby3 = new Thread(Lobby.getLobbyInstance(3),"server_lobby3");
        lobby2.start();
        lobby3.start();
        while (true) {
            try {
                Socket client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                logger.info(client.getInetAddress() + " connected to the server");
                Thread thread = new Thread(clientHandler, "server_handler_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("Connection Dropped");
            }
        }
    }

    @Override
    public void run() {
        main(new String[0]);
    }
}
