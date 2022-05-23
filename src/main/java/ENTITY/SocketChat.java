package ENTITY;

import SERCURITY.KhoaAES;

import java.net.Socket;

public class SocketChat {
    Socket socket;
    KhoaAES khoaAES;

    public SocketChat() {
    }

    public SocketChat(Socket socket, KhoaAES khoaAES) {
        this.socket = socket;
        this.khoaAES = khoaAES;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public KhoaAES getKhoaAES() {
        return khoaAES;
    }

    public void setKhoaAES(KhoaAES khoaAES) {
        this.khoaAES = khoaAES;
    }
}
