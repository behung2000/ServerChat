package BLL;

import QueueChat.QueueSocketChat;
import SERCURITY.KhoaRSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerChat {
    int port = 50000;
    int numThread = 4;
    ServerSocket serverSocket = null;
    ExecutorService executorService = null;
    BufferedReader reader = null;
    KhoaRSA rsa = null;
    boolean running = true;
    QueueSocketChat queueSocketChat = null;
    QueueSocketChat queueSocketChatdoighep = null;

    public ServerChat(){
        try {
            rsa = new KhoaRSA();
            rsa.initFromStrings();
            executorService = Executors.newFixedThreadPool(numThread);
            queueSocketChat = new QueueSocketChat();
            queueSocketChatdoighep = new QueueSocketChat();
            serverSocket = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            while (true) {
                System.out.println("1-->"+queueSocketChat.getAll());
                Socket socket = serverSocket.accept();
                System.out.println("1-->"+queueSocketChat.getAll());
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String keyMaHoa = reader.readLine();
                String key = rsa.decrypt(keyMaHoa);
                if (key.equalsIgnoreCase("ServerClose")) break;
                executorService.execute(new WorkServerChat(socket, key, queueSocketChat, queueSocketChatdoighep));
            }
            executorService.shutdownNow();
            reader.close();
            serverSocket.close();
            System.out.println("Server Close");
        }
        catch (IOException e){
            System.out.println("Error -> ServerChat -> ServerChat -> "+e.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args){
        ServerChat serverchat = new ServerChat();
    }

}
