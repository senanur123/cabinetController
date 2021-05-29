package cabinetController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	Socket socket;
	PrintWriter toServer;
	BufferedReader fromServer;
	
	private final static Client instance = new Client();

    public static Client getInstance() {
        return instance;
    }
	public Client() {
		try {
			socket = new Socket(InetAddress.getLocalHost(), 17);
			toServer = new PrintWriter(socket.getOutputStream(), true);
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return socket;
	}
	 public void setSocket(Socket socket) {
		this.socket = socket;
	}
	 public BufferedReader getFromServer() {
		return fromServer;
	}
	 public PrintWriter getToServer() {
		return toServer;
	}
	 public void setFromServer(BufferedReader fromServer) {
		this.fromServer = fromServer;
	}
	 public void setToServer(PrintWriter toServer) {
		this.toServer = toServer;
	}
	 
}
		
    
  

