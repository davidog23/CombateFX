package net.davidog.tbcombat.utils;

import com.google.gson.internal.LinkedTreeMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Container for information of a server.
 * Created by David on 02/02/2016.
 */
public class ServerInfo implements Serializable{
    private StringProperty name;
    private StringProperty address;
    private IntegerProperty port;

    public ServerInfo(String name, String address, int port) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.port = new SimpleIntegerProperty(port);
    }

    public ServerInfo(LinkedTreeMap<String, LinkedTreeMap<String, Object>> server) {
        this((String)server.get("name").get("value"), (String)server.get("address").get("value"), ((Double) server.get("port").get("value")).intValue());
    }

    public ServerInfo() {
        this("", "", 0);
    }

    public String getName() {
        return this.name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public String getAddress() {
        return this.address.getValue();
    }

    public void setAddress(String address) {
        this.address.setValue(address);
    }

    public StringProperty addressProperty() {
        return this.address;
    }

    public int getPort() {
        return this.port.getValue();
    }

    public void setPort(int port) {
        this.port.setValue(port);
    }

    public IntegerProperty portProperty() {
        return this.port;
    }
}
