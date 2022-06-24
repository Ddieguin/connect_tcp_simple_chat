import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;

public class Server implements Runnable {
    
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;


    public Server() {
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while(!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        }catch(IOException e) {
            shutDown();
        }
    }

    public void shutDown() { 
        try {
            done = true;
            if(!server.isClosed()) {
                server.close();
            }
            for(ConnectionHandler ch : connections) {
                ch.shutDown();
            }
        }catch(IOException e) {
            shutDown();
        }
    }


    class ConnectionHandler implements Runnable {

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;


        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        public void broadcast(String msg) {
            for(ConnectionHandler ch : connections) {
                if(ch != null) {
                    ch.sendMessage(msg);
                }
            }
        }
    
        @Override
        public void run() { 
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please enter a nickname: ");
                nickname = in.readLine();
                System.out.println(nickname + " connected! -> IP: " + client.getInetAddress());
                broadcast(nickname + " joined the chat!");
                String msg;

                while((msg = in.readLine()) != null) {
                    if(msg.startsWith("/nick")) {
                        String[] messageSplit = msg.split(" ", 2);
                        if(messageSplit.length == 2) { 
                            broadcast(nickname + " renamed themselves to " + messageSplit[1]);
                            System.out.println(nickname + " renamed themselves to " + messageSplit[1]);
                            nickname = messageSplit[1];
                            out.println("Successfully changed nickname to " + nickname);
                        } else {
                            out.println("No nickname provided!");
                        }
                    } else if(msg.startsWith("/quit")) {
                        broadcast(nickname + " left the chat!");
                        System.out.println(nickname + " left the chat!");
                        shutDown();
                    } else {
                        broadcast(nickname + ": " + msg);
                    }
                }
            

            } catch (Exception e) {
                shutDown();
            }
        }


        //send message to chat 
        public void sendMessage(String msg) {
            out.println(msg);
        }

        public void shutDown() {
            try {
                in.close();
                out.close();
    
                if(!client.isClosed()) {
                    client.close();
                }
            }catch(Exception e) {
                //ignore
            }
        }

        
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}