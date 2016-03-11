/** COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import market.Market;
import users.User;

/**
 * @author nishant
 *
 */
public class ReceiveThread implements Runnable {

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	private Thread t;
	private String threadName = "Receive Thread";
	
	private int CtoSPort;
	private ServerSocket CtoSServerSocket;
	private Socket CtoSSocket;
	
	private OutputStream outStream;
	private InputStream inStream;
	private PrintWriter pWrite;
	private BufferedReader bRead;
	
	private String receivedMessage;
	
	private Market market;
	private User currentUser;
	
	public ReceiveThread (int port, Market runningMarket,User user) {
		this.CtoSPort = port;
		this.market = runningMarket;
		this.currentUser = user;
	}
	
	
	
	public void start ()
	{
		System.out.println("--Starting " +  threadName );
	    if (t == null)
	    {
	    	t = new Thread (this, threadName);
	        t.start ();
	     }
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Receive Thread Running");
		
		StartCtoSServerSocket();
		
		
		
		while(true) {
		    try {
		    	CtoSSocket = CtoSServerSocket.accept();
		    	
		    	
		    	// sending to client (pwrite object)
	            outStream = CtoSSocket.getOutputStream(); 
	            pWrite = new PrintWriter(outStream, true);
				
	            // receiving from server ( receiveRead  object)
				inStream = CtoSSocket.getInputStream();
				bRead = new BufferedReader(new InputStreamReader(inStream));
		    	
		    	
				if((receivedMessage = bRead.readLine()).equals(null))  {
					System.out.println("Waiting for new requests from client ..");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					System.out.println("Message from Client: " +receivedMessage);  
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		  }         
		
	}
	
	private void StartCtoSServerSocket () {
		try {
			CtoSServerSocket = new ServerSocket(CtoSPort);
			System.out.println("Receive Thread: Before Accept");
			//CtoSSocket = CtoSServerSocket.accept();
			System.out.println("Receive Thread: After Accept");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //Server socket
		
	}

}
