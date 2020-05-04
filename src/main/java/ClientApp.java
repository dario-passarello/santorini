import network.Client;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args){
        Client client = new Client(); //Client("127.0.0.1", 12345);
        try{
            client.run();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
