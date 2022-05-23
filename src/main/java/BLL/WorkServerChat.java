package BLL;

import ENTITY.SocketChat;
import QueueChat.QueueSocketChat;
import SERCURITY.KhoaAES;

import java.io.*;
import java.net.Socket;
import java.util.Set;
import java.util.StringTokenizer;

public class WorkServerChat implements Runnable{
    boolean bool = true;
    Socket socket;
    BufferedReader reader = null;
    BufferedWriter writer = null;
    KhoaAES khoaAES = null;

    QueueSocketChat queueSocketChat = null;
    QueueSocketChat queueSocketChatdoighep = null;
    boolean Signnamechat = false;
    String namechat="";

    SocketChat socketChat = null;


    public WorkServerChat(Socket socket, String key, QueueSocketChat queueSocketChat, QueueSocketChat queueSocketChatdoighep){
        this.khoaAES = new KhoaAES();
        this.khoaAES.setStringKey(key);
        this.socket = socket;
        this.queueSocketChat = queueSocketChat;
        this.queueSocketChatdoighep = queueSocketChatdoighep;
    }

    public void run(){
        System.out.println("Client " + socket.toString() + " accepted");
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (bool) {
                String line = receive();
                if (line.equalsIgnoreCase("!@#byeserverchat!@#")) break;
                work(line);
            }
            System.out.println("Client " + socket.toString() + " close");
            queueSocketChat.removeQueue(namechat);
            queueSocketChatdoighep.removeQueue(namechat);
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            //Lỗi
            System.out.println("Error -> WorkServerChat -> run -> "+e.getMessage());
            e.printStackTrace();
            bool = false;
        }
    }

    public void work(String line){
        if(!Signnamechat){
            if(!queueSocketChat.hasQueue(line)){
                SocketChat socketChat = new SocketChat();
                socketChat.setSocket(socket);
                socketChat.setKhoaAES(khoaAES);
                queueSocketChat.addQueue(line, socketChat);
                sent("!@#Success!@#");
                Signnamechat=true;
                namechat=line;
            }
            else{
                sent("!@#Fail!@#");
            }
        }
        else{
            if(line.equalsIgnoreCase("!@#ghepdoi!@#")) {
                int dk=0;
                int i=0;
                int n=queueSocketChatdoighep.getAll().size();
                String tmp = queueSocketChatdoighep.getAllNameWithString();
                StringTokenizer stringTokenizer = new StringTokenizer(tmp,"!@#");
                while (stringTokenizer.hasMoreTokens()){
                    String key = stringTokenizer.nextToken();
                    System.out.println(key);
                    String name = key;
                    sent(name);
                    String line1 = receive();
                    if (line1.equalsIgnoreCase("!@#yesghepdoi!@#")) {
                        socketChat = queueSocketChat.get1(name);
                        queueSocketChatdoighep.removeQueue(name);
                        sentchat(name, socketChat);
                        System.out.println(namechat+" -> "+name);
                        dk++;
                        break;
                    }
                    i++;
                }
                if (i == n){
                    sent("!@#hetghepdoi!@#");
                    if(dk==0) queueSocketChatdoighep.addQueue(namechat, queueSocketChat.get1(line));
                }
            }
            else{
                if(line.equalsIgnoreCase("!@#ghepdoi1Client!@#"))
                {
                    String line1 = receive();
                    socketChat = queueSocketChat.get1(line1);
                    queueSocketChatdoighep.removeQueue(namechat);
                    System.out.println(namechat+" -> "+line1);
                }
                else {
                    if(socketChat!=null){
                        System.out.println("sent chat cua "+namechat);
                        sentchat(line, socketChat);
                    }
                }
            }
        }
    }

    //Sent
    public void sent(String line){
        try {
            String lineMaHoa = khoaAES.encrypt(line);
            writer.write(lineMaHoa + "\n");
            writer.flush();
        } catch (IOException e) {
            //Lỗi
            System.out.println("Error -> WorkServerChat -> sent -> "+e.getMessage());
            e.printStackTrace();
            bool = false;
            //System.exit(0);
        }
    }

    //receive
    public String receive(){
        try {
            String lineMaHoa = reader.readLine();
            String line = khoaAES.decrypt(lineMaHoa);
            return line;
        }
        catch (IOException e){
            //Lỗi
            System.out.println("Error -> WorkServerChat -> receive -> "+e.getMessage());
            e.printStackTrace();
            bool = false;
            //System.exit(0);
            return "false";
        }
    }

    //Sent chat
    public void sentchat(String line, SocketChat socketChat){
        try {
            System.out.println("sent chat cua "+namechat+" toi socketChat "+socketChat.getSocket()+" noi dung: "+line);
            String linesent = namechat + ": " + line;
            BufferedWriter writersentchat = new BufferedWriter(new OutputStreamWriter(socketChat.getSocket().getOutputStream()));
            String linesentMaHoa = socketChat.getKhoaAES().encrypt(linesent);
            writersentchat.write(linesentMaHoa + "\n");
            writersentchat.flush();
            if(line.equalsIgnoreCase("!@#closeghepdoi!@#")) this.socketChat=null;
        }
        catch (IOException e){
            //Lỗi
            System.out.println("Error -> WorkServerChat -> sent -> "+e.getMessage());
            e.printStackTrace();
            bool = false;
            //System.exit(0);
        }
    }
}
