package net.davidog.tbcombat.model;

import java.io.*;
import java.net.Socket;

public class SocketWrapper {

	public Socket socket;
	private DataInputStream dataFromClient;
	private DataOutputStream dataToClient;
	private ObjectInputStream objectFromClient;
	private ObjectOutputStream objectToClient;

    private boolean vacio;
	
	public SocketWrapper(Socket socket, boolean server) throws IOException
	{
		this.socket = socket;
		initialize(socket, server);
	}

	public SocketWrapper() {
        this.vacio = true;
    }

	public void initialize(Socket socket, boolean server) throws IOException {
		this.socket = socket;
		if (server) {
			dataFromClient = new DataInputStream(socket.getInputStream());
			dataToClient = new DataOutputStream(socket.getOutputStream());
			objectFromClient = new ObjectInputStream(socket.getInputStream());
			objectToClient = new ObjectOutputStream(socket.getOutputStream());
		} else {
			dataToClient = new DataOutputStream(socket.getOutputStream());
			dataFromClient = new DataInputStream(socket.getInputStream());
			objectToClient = new ObjectOutputStream(socket.getOutputStream());
			objectFromClient = new ObjectInputStream(socket.getInputStream());
		}
        this.vacio = false;
	}
	
	public Object readObject() throws ClassNotFoundException, IOException
	{
		return objectFromClient.readUnshared();
	}
	
	public String readData() throws IOException
	{
		return dataFromClient.readUTF();
	}
	
	public int readInt() throws IOException
	{
		return dataFromClient.readInt();
	}
	
	public boolean sendObject(Object o)
	{
		try {
			objectToClient.writeUnshared(o);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean writeData(String data)
	{
		try {
			dataToClient.writeUTF(data);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean writeInt(int num)
	{
		try {
			dataToClient.writeInt(num);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

    public boolean isVoid() {
        return vacio;
    }
	public void setVoid(boolean vacio) {
		this.vacio = vacio;
	}
}
