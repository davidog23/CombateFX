package net.davidog.tbcombat.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

public class ServerCombate {
	
	public static void runServer(int port) throws IOException {
		Exception excep = null;
		Vector<SocketWrapper> excepteds = new Vector<>();
		Vector<MatchThread> matches = new Vector<>();

		DispairDaemon mygc = new DispairDaemon(excepteds, matches);
		mygc.setName("Match unregister thread");
		mygc.start();

		ServerSocket server = new ServerSocket();
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Servidor iniciado");
		do {

        } while(Boolean.FALSE);
		/*do {
			try {
				SocketWrapper socket1;
				SocketWrapper socket2;

				if(excepteds.isEmpty()) {
					socket1 = new SocketWrapper(server.accept(), true);
				} else {
					socket1 = mygc.nextSocketWrapper(server);
				}

				if (excepteds.isEmpty()) {
					socket2 = new SocketWrapper(server.accept(), true);
				} else {
					socket2 = mygc.nextSocketWrapper(server);
				}

				MatchThread match = new MatchThread(socket1, socket2);
				match.start();
				synchronized (matches) {
					matches.addElement(match);
				}
			} catch (IOException e) {
				if(!(e instanceof SocketException)) { excep = e; }
				e.printStackTrace();
			}
		} while(excep == null);*/
		server.close();
	}

}
