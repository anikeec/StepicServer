/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;

/**
 *
 * @author apu
 */
public class ConnectionHandlerPool {
    private int SOCKET_STORE_SIZE = 10;
    private Socket[] socketStore;
    private Integer currentSocket = 0;
	
    public ConnectionHandlerPool(int backlog) {
        socketStore = new Socket[backlog];
        for(int i=0;i<socketStore.length;i++) {
            socketStore[i] = null;
        }
            for (int i = 0; i < backlog; i++) {
                    new Thread(new ConnectionHandler(socketStore, currentSocket)).start();
            }
    }

    public void addConnection(Socket socket) {
            synchronized (socketStore) {
                for(int i=0;i<socketStore.length;i++) {
                    if(socketStore[i] == null) {
                        currentSocket = i;
                        socketStore[i] = socket;
                        socketStore.notify();
                        break;
                    }
                }                        
            }
    }
}
