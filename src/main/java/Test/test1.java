package Test;

import ENTITY.SocketChat;
import QueueChat.QueueSocketChat;

import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;

public class test1 {

    HashMap<String, Socket> Queues;

    public test1(){
        this.Queues = new HashMap<String, Socket>();
    }

    public void addQueue(String name, Socket socket){
        Queues.put(name, socket);
    }

    public void  removeQueue(String name){
        Queues.remove(name);
    }

    public HashMap getQueue(){
        return Queues;
    }

    public Socket get1(String name){
        return  Queues.get(name);
    }

    public boolean hasQueue(String name){
        return Queues.containsKey(name);
    }

    public static void main(String[] args){
        /*
        test1 t1 = new test1();
        t1.addQueue("Hung1",new Socket());
        t1.addQueue("Hung2",new Socket());
        t1.addQueue("Hung3",new Socket());
        System.out.println(t1.getQueue());
        System.out.println("Hung1 = "+t1.hasQueue("Hung1"));
        test2 t2 = new test2();
        t2.addQeuesToTest1(t1);
        System.out.println(t1.getQueue());
        System.out.println("Hung1 = "+t1.hasQueue("Hung1"));
        t2.removeQeuesFromTest1(t1);
        System.out.println(t1.getQueue());
        System.out.println("Hung1 = "+t1.hasQueue("Hung1"));
        System.out.println(t1.get1("Hung2"));
        System.out.println(t1.getQueue());
        System.out.println("Hung1 = "+t1.hasQueue("Hung1"));
         */
        /*
        QueueSocketChat queueSocketChat = new QueueSocketChat();
        queueSocketChat.addQueue("Hung1",new SocketChat());
        queueSocketChat.addQueue("Hung2",new SocketChat());
        queueSocketChat.addQueue("Hung3",new SocketChat());
        System.out.println(queueSocketChat.getAll());
        test2 t2 = new test2();
        t2.addQueue(queueSocketChat);
        System.out.println(queueSocketChat.getAll());
         */
        /*
        StringTokenizer stringTokenizer = new StringTokenizer("khi564123","!@#");
        System.out.println(stringTokenizer.countTokens());
         */
    }

}
