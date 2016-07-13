package net.davidog.tbcombat.network;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.Vector;

/**
 * Cola de clientes.
 * Created by David on 21/02/2016.
 */
public class Cola extends Thread{
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Cola.class);

    private ServerSocket server;
    private Vector<SocketWrapper> clientes;

    public Cola(int port) {
        clientes = new Vector<>();
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            logger.warn("No se ha podido crear el ServerSocket", e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                clientes.add(new SocketWrapper(server.accept(), true));
            } catch (IOException e) {
                logger.warn("Ha fallado un intento de conexion por parte del cliente", e);
            }
        }
    }

    public SocketWrapper nextSocket() {
        Iterator<SocketWrapper> it = clientes.listIterator();
        SocketWrapper result = it.hasNext() ? it.next() : null;
        clientes.remove(result);
        return result;
    }
}
