package network;

import network.messages.Message;
import network.messages.toclient.MatchFoundMessage;
import network.messages.toserver.LoginDataMessage;
import org.junit.Assert;
import org.junit.Test;
import view.RemoteView;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class EchoTest {

    public void networkTest() {
        Server s = new Server();
        Thread serverThread = new Thread(s);
        serverThread.start();
        try{
            TimeUnit.MILLISECONDS.sleep(100);

        }catch (InterruptedException e){
            e.printStackTrace();
        }


        ClientDriver client1 = new ClientDriver("127.0.0.1",Server.SERVER_SOCKET_PORT);
        Thread listenerThread1 = new Thread(client1);
        ClientDriver client2 = new ClientDriver("127.0.0.1",Server.SERVER_SOCKET_PORT);
        Thread listenerThread2 = new Thread(client2);
        listenerThread1.start();
        listenerThread2.start();

        client1.sendMessage(new LoginDataMessage("Dario",2));
        client2.sendMessage(new LoginDataMessage("Luigi",2));
        client1.receiveMessage().forEach(System.out::println);
        Set<String> gods = Set.of("Zeus","Apollo");
        client1.sendMessage((Message<RemoteView> & Serializable) target -> target.getController().game().submitGodList(target, gods));
        client1.receiveMessage().forEach(System.out::println);
        client2.receiveMessage().forEach(System.out::println);
    }

}
