package Server;

/**
 * точка входа
 */
public class MainClassServer {

    public static void main(String[] args) {
        SQLHandler.connect();
        //Server w = new Server();
        try{
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SQLHandler.disconnect();
    }
}
