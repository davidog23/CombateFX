package model;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * Script de combate.
 * WIP || UNDER HEAVY DEVELOPMENT. (IA & ONLINE MULTIPLAYER)
 * @author David Olmos
 * Date: 14/08/2015
 */
public abstract class Combate {
	public static void runOffline()
	{
		//Seleccion de modo
		int modo = Util.menu("Seleccione modo de juego: \n1. Un jugador\n2. Dos jugadores", 2);

		//Creacion de personaje
		Jugador j1 = Jugador.init();
		Jugador j2 = modo == 1 ? Jugador.initRandom() : Jugador.init();

		//Bucle principal
		do {
			j1.checkState();
			j2.checkState();
			int ataqueSeleccionado = j1.seleccionAtaque(j2);
			int ataqueSeleccionadoAdversario = modo == 1 ? j2.seleccionAtaqueIA(j1) : j2.seleccionAtaque(j1); /*IA WIP*/
			ejecutarAtaque(j1, j2, ataqueSeleccionado, ataqueSeleccionadoAdversario);
		} while (j1.getHp() > 0 && j2.getHp() > 0);

		//Mensaje de finalizaci�n
		if(j1.getHp() > 0)
		{
			System.out.println("Player 1 WIN");
		}
		else if(j2.getHp() > 0)
		{
			System.out.println("Player 1 DEFEAT");
		}else{
			System.out.println("DRAW");
		}
	}

	public static void runOnline(String ip) throws IOException, ClassNotFoundException
	{
		//Inicia el personaje
		Jugador player = Jugador.init();
		Jugador adversario = null;

		if (player != null) {
			//Establece conexión
			SocketWrapper conexion = new SocketWrapper(new Socket(ip, 5336), false);

			boolean reset = false;
			while (player != null || reset) {
				boolean continua = true;
				String orden = "";
				//Bucle del cliente
				while (continua) {
					boolean condicion = true;
					while (condicion) {
						orden = conexion.readData();
						condicion = !(orden.equals("send") || orden.equals("select") || orden.equals("update")
								|| orden.equals("WIN") || orden.equals("LOST") || orden.equals("DRAW")
								|| orden.equals("reset"));
						if (condicion) {
							System.out.println(orden);
						}
					}
                    switch(orden)
                    {
                        case "select":
                            int select = 0;
                            if (player != null) {
                                select = player.seleccionAtaque(adversario);
                            }
                            conexion.writeInt(select);
                            break;
                        case "update":
                            player = (Jugador) conexion.readObject();
                            adversario = (Jugador) conexion.readObject();
                            break;
                        case "send":
                            conexion.sendObject(player);
                            break;
                        case "WIN":
                        case "LOST":
                        case "DRAW":
                        case "reset":
                            continua = false;
                            break;
                    }
				}
				if (orden.equals("reset")) {
					System.out.println("El adversario ha perdido la conexi�n con el servidor.");
					reset = true;
				} else {
					System.out.println(orden);
					int opcion = Util.menu("�Quieres jugar de nuevo?\n1.S�\n2.No", 2);
					reset = opcion == 1;
					player = reset ? Jugador.init() : null;
				} 
			}
			conexion.socket.close();
		} else {
			System.out.println("Hasta la pr�xima!");
		}
	}
	
	public static void ejecutarAtaque(Jugador j1, Jugador j2, int ataqueSeleccionado, int ataqueSeleccionadoAdversario)
	{
		if(j1.totalVel > j2.totalVel)
		{
			j1.attack(ataqueSeleccionado, j2);
			j2.attack(ataqueSeleccionadoAdversario, j1);
		}else if(j1.totalVel < j2.totalVel){
			j2.attack(ataqueSeleccionadoAdversario, j1);
			j1.attack(ataqueSeleccionado, j2);
		}else{
			Random rand = new Random(System.nanoTime());
			int aux = rand.nextInt(2);
			if(aux == 0)
			{
				j1.attack(ataqueSeleccionado, j2);
				j2.attack(ataqueSeleccionadoAdversario, j1);
			}else{
				j2.attack(ataqueSeleccionadoAdversario, j1);
				j1.attack(ataqueSeleccionado, j2);
			}
		}
	}
	

}
