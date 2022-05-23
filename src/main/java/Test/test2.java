package Test;

import ENTITY.SocketChat;
import QueueChat.QueueSocketChat;

import java.net.Socket;

public class test2 {

    public void addQeuesToTest1(test1 test1){
        test1.addQueue("hungtest2", new Socket());
    }

    public void removeQeuesFromTest1(test1 test1){
        test1.removeQueue("Hung1");
    }

    public void addQueue(QueueSocketChat queueSocketChat){
        queueSocketChat.addQueue("Hungtes2", new SocketChat());
    }

}
