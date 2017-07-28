/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author apu
 */
public class ConnectionHandler implements Runnable{
    private final Socket[] socketStore;
	
    public ConnectionHandler(Socket[] socketStore) {
            this.socketStore = socketStore;
    }

    public void run() {
            synchronized (socketStore) {
                    try {
                            socketStore.wait();
                            handleSocket(socketStore[0]);
                    } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                    }
            }
    }

    private void handleSocket(Socket socket) {
        try {
            // do anything you need
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            DataInputStream in = new DataInputStream(is);
            DataOutputStream out = new DataOutputStream(os);
            String line = null;
            while(true) {
                line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
                System.out.println(line);
                out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
                out.flush(); // заставляем поток закончить передачу данных.
                System.out.println();
            }    
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
