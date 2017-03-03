package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Объект хадлер
 */
public class ClientHandler implements Runnable {
    Server owner; // ссылка на сервер, на котором работает ClientHandler
    Socket s;
    DataOutputStream out;
    DataInputStream in;
    String name;

    public ClientHandler(Socket s, Server owner) {
        try {
            this.s = s;
            this.owner = owner;
            out = new DataOutputStream(s.getOutputStream());
            in = new DataInputStream(s.getInputStream());
            name = "";
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String w = in.readUTF();
                if (name.isEmpty()) {
                    String[] n = w.split("\t");
                    String t = SQLHandler.getNickByLoginPassword(n[1], n[2]);
                    if (t != null) {
                        name = t;
                    } else {
                        sendMsg("Ошибка авторизации");
                        owner.remove(this);
                        break;
                    }
                    w = null;
                }
                if (w != null) {
                    owner.broadcastMsg(name + ": " + w);
                    System.out.println(name + ": " + w);
                    if (w.equalsIgnoreCase("END")) break;
                }
                Thread.sleep(100);
            }
        } catch (IOException e) {
            System.out.println("Output Error");
        } catch (InterruptedException e) {
            System.out.println("Thread sleep error");
        }
        try {
            System.out.println("Клиент отключился");
            if (!name.equals("")) owner.remove(this);
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
        }
    }

}
