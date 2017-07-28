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
    private final Socket[] socketStore = new Socket[1];
	
    public ConnectionHandlerPool(int backlog) {
            for (int i = 0; i < backlog; i++) {
                    new Thread(new ConnectionHandler(socketStore)).start();
            }
    }

    public void addConnection(Socket socket) {
            synchronized (socketStore) {
                    socketStore[0] = socket;
                    socketStore.notify();
            }
    }
}
