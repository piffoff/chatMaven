package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Super server
 */
public class Server {

    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public Server(){
        ServerSocket server = null;
        Socket s = null;
        try {
            server = new ServerSocket(8189);
            System.out.println("Server created. Waiting clients...");
            while (true){
                s = server.accept(); //Замирает и ждет подключения
                System.out.println("Client connected");
                ClientHandler h = new ClientHandler(s, this);
                clients.add(h);
                new Thread(h).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                server.close();
                System.out.println("Server have biin closed");
                s.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void remove(ClientHandler o){
        clients.remove(o);
    }

    public void broadcastMsg(String text){
        for (ClientHandler o: clients){
            o.sendMsg(text);
        }
    }
}
