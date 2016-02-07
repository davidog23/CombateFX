package net.davidog.tbcombat.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.davidog.tbcombat.view_controller.IGameController;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
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

	public static <T extends IGameController> T loadWindow(Class<T> controllerType, URL fxmlLocation) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        T controller = loader.getController();
        controller.getStage().setScene(new Scene(root));
        return controller;
    }

    public static <T extends IGameController> T loadWindowWithArgument(Class<T> controllerType, URL fxmlLocation, Object... argument) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        loader.setControllerFactory(clazz -> {
            if (clazz == controllerType) {
                try {
                    for(Constructor<?> c: controllerType.getConstructors()) {
                        if(c.getParameterCount() == argument.length) {
                            return ((Constructor<T>) c).newInstance(argument);
                        }
                    }
                    throw new IllegalArgumentException();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // default behavior:
                try {
                    return clazz.newInstance();
                } catch (Exception exc) {
                    throw new RuntimeException(exc);
                }
            }
        });

        Parent root = loader.load();
        T controller = loader.getController();
        controller.getStage().setScene(new Scene(root));
        return controller;
    }
}
