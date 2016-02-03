package net.davidog.tbcombat.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Container for information of a server.
 * Created by David on 02/02/2016.
 */
public class ServerInfo {
    private StringProperty name;
    private StringProperty address;
    private SimpleIntegerProperty port;

    public ServerInfo(String name, String address, int port) {
        this.name.setValue(name);
        this.address.setValue(address);
        this.port.set(port);
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
