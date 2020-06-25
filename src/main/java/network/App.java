package network;

/**
 * Main class for the Santorini application
 * Parses command arguments and launches the Application with the desired settins
 */
public class App {

    public static void main(String[] args) {
        if(args.length == 0) {
            Client.startClient(true);
        } else {
            System.out.print(args[0]);
            switch (args[0]) {
                case "-cli":
                    Client.startClient(false);
                    break;
                case "-gui":
                    Client.startClient(true);
                    break;
                case "-asServer":
                    if (args.length == 2) {
                        try {
                            int port = Integer.parseInt(args[1]);
                            if (0 < port && port < 65535) {
                                Server.startServer(port);
                            } else {
                                System.out.println("ERROR: NOT A PORT NUMBER");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("NOT A NUMBER!");
                        }
                    } else {
                        Server.startServer();
                    }
                    break;
                case "-help":
                case "-h":
                    System.out.println("Santorini\n");
                    System.out.println("Command Options: \n");
                    System.out.println("-gui (or no options specified): Start Santorini client with a graphical user interface\n");
                    System.out.println("-cli: Starts Santorini client with a command line interface\n");
                    System.out.println("-asServer [PORT]: Starts a TCP Santorini server listening on [PORT]");
                    System.out.println("(If no port is specified the default 12345 port will be used)\n");
                    System.out.println("-help (or -h): Display this message");
                    break;
                default:
                    System.out.println("ERROR: Illegal Program Argument. Rerun this program with -h argument for a guide");
                    break;
            }
        }

    }
}
