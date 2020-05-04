import network.Server;

import java.io.IOException;


public class ServerApp {
    public static void main( String[] args )
    {
        Server server;
        try {
            server = new
                    Server();
            server.run();
        } catch (Exception e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
