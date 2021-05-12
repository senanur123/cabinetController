

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
    	
    	//get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();

        int port = 17;
        
        System.out.println( "Sending message to the host: " + host );

        try
        {
        	 Socket echoSocket = new Socket(host.getHostName(), port);        // 1st statement
        	    PrintWriter toServer =                                            // 2nd statement
        	        new PrintWriter(echoSocket.getOutputStream(), true);
        	    BufferedReader fromServer =                                          // 3rd statement 
        	        new BufferedReader(
        	            new InputStreamReader(echoSocket.getInputStream()));
        	    BufferedReader stdIn =                                       // 4th statement 
        	        new BufferedReader(
        	            new InputStreamReader(System.in));
        	    
        	    String userInput;
        	    String message="";
        	    System.out.print("Start the server with format: STRT|<Cabinet>|<Full Name>|<Failure rate>|<Ping> : ");
        	    userInput = stdIn.readLine();
        	    if(userInput!=null) {
        	    	toServer.println(userInput);
        	        message = fromServer.readLine();
        	        if(message!=null) {
        	        	
        	        	System.out.println("After STRT - Message from server: " + message);
	        	        if(message.contains("examinees")) {
	        	        	System.out.print("Initialise cabinet with format: INIT|<Slot number>|<Examinee ID> : ");
	        	        	userInput = stdIn.readLine();
	        	        	toServer.println(userInput);
	        	        	message = fromServer.readLine();
	        	        	if(message!=null) {
	        	        		System.out.println("After INIT - Message from server: " + message);
	        	        		if(message.contains("started")) {
	        	        			System.out.print("End INIT with: ENDINIT : ");
	        	        			userInput = stdIn.readLine();
	        	        			toServer.println(userInput);
	        	        		}
	        	        	}else {
	        	        		System.out.println("Problem with INIT command!");
	        	        	}
	        	        	
	        	        }
        	        }
        	        else
        	        	System.out.println("Problem with STRT command!");
        	    }else {
        	    	System.out.println("You didnt give a command! Streams closing...");
        	    	toServer.close();
    	        	fromServer.close();
    	        	//socket.close();
        	    }
           
    	       
        	    System.out.println("Disconnected somehow!");
			
			

        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    	
    	
    	
    	
      
    }
    
    
    private static boolean processMessage(String line, PrintWriter toClient) throws InterruptedException {
		boolean retVal = true;
		if (line.length() <= 0)
			return false;
		
		System.out.println("Message from server received: " + line);
		return true;
		
	}
}
        
        
        
        
        
        
        
        
      
    
