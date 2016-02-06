package net.davidog.tbcombat.utils;

import java.io.File;

/**
 * Class for String references.
 * Created by David on 03/02/2016.
 */
public class Reference {
    public static final String SERVER_INFO_PATH = System.getProperty("user.dir") + File.separator + "cfg" + File.separator + "servers.json";

    /*
    Iniciar controladores con constructores con argumentos.
    loader.setControllerFactory(clazz -> {
        if (clazz == type) {
            try {
                return ((Constructor<T>) Array.get(type.getConstructors(), 0)).newInstance(stage);
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
    */
}
