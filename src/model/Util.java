package model;

import java.util.Scanner;

public class Util {

	@SuppressWarnings("resource")
	public static int menu(String mensaje, int nOpciones) {
		Scanner S = new Scanner(System.in);
		int opcion;
		do {
			System.out.println("Inserta el número de la opción elegida.\n" + mensaje);
			opcion = S.nextInt();
		} while(opcion < 1 || opcion > nOpciones);
		return opcion;
	}

	public static String leerTeclado(String mensaje, Scanner S)
	{
		System.out.println(mensaje);
		return S.nextLine();
	}
}
