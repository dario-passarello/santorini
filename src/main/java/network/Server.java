package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port;
    private ServerSocket serverSocket;

    public Server(int port){
        this.port = port;
    }

    public  void startServer() throws IOException {

        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e){
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Server ready");
        while(true){
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new ServerClientHandler(socket));
            } catch (IOException e){
                break;
            }
        }
        executor.shutdown();
        serverSocket.close();
    }

}
