package QueueChat;

import ENTITY.SocketChat;
import java.util.HashMap;
import java.util.Set;

public class QueueSocketChat {
    HashMap<String, SocketChat> Queues;

    public QueueSocketChat(){
        this.Queues = new HashMap<String, SocketChat>();
    }

    public void addQueue(String name, SocketChat socket){
        this.Queues.put(name, socket);
    }

    public void removeQueue(String name){
        this.Queues.remove(name);
    }

    public HashMap getAll(){
        return Queues;
    }

    public SocketChat get1(String name){
        return Queues.get(name);
    }

    public boolean hasQueue(String name){
        return Queues.containsKey(name);
    }

    public Set<String> getAllKeys(){
        return Queues.keySet();
    }

    public String getAllNameWithString(){
        String line = "!@#";
        if(Queues!=null && Queues.size()>0) {
            for (String key : Queues.keySet()) {
                line = line + key + "!@#";
            }
        }
        return line;
    }

}
