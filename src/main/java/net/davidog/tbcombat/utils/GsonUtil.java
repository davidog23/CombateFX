package net.davidog.tbcombat.utils;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * IO utilities for Gson library.
 * Created by David on 02/02/2016.
 */
public class GsonUtil {
    public static <T> List<T> leerGson(File path, Class<T> type) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type collectionType = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(new FileReader(path), collectionType);
    }

    public static <T> void writeGson(File path, List<T> list) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(list);
        FileWriter file = new FileWriter(path);
        file.write(json);
        file.close();
    }
}
