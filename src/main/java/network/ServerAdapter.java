package network;

import view.ClientView;

import java.io.IOException;
import java.net.Socket;

public class ServerAdapter implements Runnable{

    private final ClientView view;
    private Socket socketToServer;

    public ServerAdapter(ClientView view, String serverIp, int port) {
        this.view = view;
        try{
            socketToServer = new Socket(serverIp,port);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

    }

    private void listen() {

    }



}
