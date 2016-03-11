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
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import market.Market;
import users.User;

/**
 * @author nishant
 *
 */
public class SendThread implements Runnable {

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	
	private Thread t;
	private String threadName = "Send Thread";
	
	private int StoCPort;
	private ServerSocket StoCServerSocket;
	private Socket StoCSocket;
	
	private OutputStream outStream;
	private InputStream inStream;
	private PrintWriter pWrite;
	private BufferedReader bRead;
	private String newData;
	
	private int sentMessageCounter = 0;
	
	private Market market;
	
	private User currentUser;
	
	private static final String NO_NEW_DATA = "noUpdates";
	//Constructor
	public SendThread (int port,Market runningMarket, User user) {
		this.StoCPort = port;
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
		System.out.println("Send Thread Running");
		
		StartStoCServerSocket();
		
		//newData = "This is from SendThread..." + sentMessageCounter;
		
		while(true) {		
			newData = "This is from SendThread..." + sentMessageCounter;
			if(newData.equals(NO_NEW_DATA))  {
				//System.out.println("No updates for this client at this time ..");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
			
			else {
				pWrite.println(newData);             
				pWrite.flush();
				System.out.println("Data Sent" +newData);
				sentMessageCounter++;
				try {
					Thread.sleep(5000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//newData = NO_NEW_DATA;
			}
		   
		 }         
	
	}
	
	private void StartStoCServerSocket () {
		
		/*try {
			StoCSocketChannel = ServerSocketChannel.open();
			StoCSocketChannel.socket().bind(new InetSocketAddress(StoCPort));
			StoCSocketChannel.configureBlocking(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {	
			StoCServerSocket = new ServerSocket(StoCPort);
			System.out.println("Send Thread: Before Accept");
			StoCSocket = StoCServerSocket.accept();
			System.out.println("Send Thread: After Accept");
			// sending to client (pwrite object)
            outStream = StoCSocket.getOutputStream(); 
            pWrite = new PrintWriter(outStream, true);
			
            // receiving from server ( receiveRead  object)
			inStream = StoCSocket.getInputStream();
			bRead = new BufferedReader(new InputStreamReader(inStream));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //Server socket
		
	}

}
