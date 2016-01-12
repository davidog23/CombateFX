package model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientCombate {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		try {
			//if(args != null && args[0] != null && args[0].equals("-server")) {
			//	ServerCombate.main(null);
			//} else {
				int opcion = Util.menu("Elige modo: \n1. Online\n2. Offline", 2);
				if(opcion == 2) {
					Combate.runOffline();
				} else {
					Scanner S = new Scanner(System.in);
					System.out.println("Escribe la IP del servidor: ");
					String ip = S.nextLine();
					Combate.runOnline(ip);
				}
			//}
		} catch (ClassNotFoundException | IOException e) {
			if(e instanceof UnknownHostException)
				System.out.println("No se ha podido acceder a esa dirección.\n");
			e.printStackTrace();
		}
		

	}
}
