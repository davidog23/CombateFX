package model;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Vector;

public class DispairDaemon extends Thread {

	private Vector<SocketWrapper> excepteds;
	private Vector<MatchThread> matches;
	
	public DispairDaemon(Vector<SocketWrapper> excepteds, Vector<MatchThread> matches)
	{
		this.excepteds = excepteds;
		this.matches = matches;
		this.setDaemon(true);
	}
	
	/**
	 * Método de acceso al buffer. Lee el primer socket aceptado que no ha sido leido todavía.
	 * @return ClientSocketWrapper El primer socket aceptado que todavia no haya sido leido.
	 * @throws IOException
	 */
	public SocketWrapper nextSocketWrapper(ServerSocket server) throws IOException
	{
		synchronized (excepteds) {
			if (excepteds.firstElement() != null) {
                SocketWrapper res = excepteds.firstElement();
                excepteds.remove(res);
                return res;
            } else {
                return new SocketWrapper(server.accept(), true);
            }
		}
	}

	public void run()
	{
		while(true)
		{
			synchronized (matches) {
				ListIterator<MatchThread> iterator = matches.listIterator();
				MatchThread match;
				try {
                    match = iterator.next();
                    synchronized (excepteds) {
                        if(match.getOnlineExcepted() != null) {
                            excepteds.addElement(match.getOnlineExcepted());
                            matches.remove(match);
                        } else {
                            if(!match.isAlive()) { matches.remove(match); }
                        }
                    }
                } catch (NoSuchElementException e1) {
                    //Checked
                }
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("No se ha podido interrumpir el eliminador de partidas.");
				e.printStackTrace();
			}
		}
	}
}
