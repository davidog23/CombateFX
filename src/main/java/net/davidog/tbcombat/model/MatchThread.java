package net.davidog.tbcombat.model;

import java.io.IOException;
import java.net.SocketException;

public class MatchThread extends Thread {

	private SocketWrapper conexion1;
	private SocketWrapper conexion2;
	private SocketWrapper onlineExcepted;

	public MatchThread(SocketWrapper c1, SocketWrapper c2)
	{
		conexion1 = c1;
		conexion2 = c2;
		onlineExcepted = null;
	}
	
	public SocketWrapper getOnlineExcepted()
	{
		return onlineExcepted;
	}

	public void run()
	{
		try {
			System.out.println("Ha comenzado un partido");
			conexion1.writeData("send");
			conexion2.writeData("send");
			Jugador j1 = (Jugador) conexion1.readObject();
			/*Cuando conexion2 es un excepted lanza un java.io.StreamCorruptedException: invalid type code: 00*/
			Jugador j2 = (Jugador) conexion2.readObject();
			//La comunicación funciona con pares (orden --> respuesta)
			do {
				
				conexion1.writeData("update");
				conexion2.writeData("update");
				conexion1.sendObject(j1);
				conexion2.sendObject(j2);
				conexion1.sendObject(j2);
				conexion2.sendObject(j1);

				conexion1.writeData("select");
				conexion2.writeData("select");
				int ataqueSeleccionado1 = conexion1.readInt();
				int ataqueSeleccionado2 = conexion2.readInt();
				Combate.ejecutarAtaque(j1, j2, ataqueSeleccionado1, ataqueSeleccionado2);
				
				j1.checkState();
				j2.checkState();
			} while(j1.getHp() > 0 || j2.getHp() > 0);

			if (j1.getHp() > 0) {
				conexion1.writeData("WIN");
				conexion2.writeData("LOST");
			} else if (j2.getHp() > 0) {
				conexion1.writeData("LOST");
				conexion2.writeData("WIN");
			} else {
				conexion1.writeData("DRAW");
				conexion2.writeData("DRAW");
			}
			
		} catch (IOException e) {
			if(e instanceof SocketException) {
				if(!conexion1.socket.isClosed()) { onlineExcepted = conexion1; }
				if(!conexion2.socket.isClosed()) { onlineExcepted = conexion2; }
				onlineExcepted.writeData("reset");
				System.out.println("Se ha desconectado un jugador del servidor");
			} else {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
