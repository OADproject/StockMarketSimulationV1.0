/** COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Enumeration;

import market.Market;
import users.Authentication;
import users.User;


public class NewConnectionThread implements Runnable {
	
	private static String ServerIP;          //IP address of the server
	private static int    ListenPORT = 9999; //default listening port
	private static ServerSocketChannel serverSocketChannel;
	private int StoCPort = 50000; //Server to Client data exchange port //Always Even
	private int CtoSPort = 50001; //Client to Server data exchange port //Always Odd
	
	private Thread t;
	private String threadName = "NewConnections Thread";
	
	private Market market;
	
	private Authentication autoObj;
	private User currentUser;
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	
	public NewConnectionThread(Market runningMarket) {
		this.market = runningMarket;
		this.autoObj = new Authentication();
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
		System.out.println("New incoming connections Thread Started");
		String GREETING = "Welcome from Nishant";
		ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
		
		ServerIP = getFilterIPAddresses();
		
		//String SampleBuyRequest1 = "stockName:amazon,userId:123,userName:surag,unitPrice:101,quantity:100,buySell:true";
		
		/*try {
			//saveServerIPToCloud();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		 try {
			 serverSocketChannel = ServerSocketChannel.open();
			 serverSocketChannel.socket().bind(new InetSocketAddress(ListenPORT));
			 serverSocketChannel.configureBlocking(false);
		  
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		  }
		
		while(true) {
			
			SocketChannel sc;
			try {
				sc = serverSocketChannel.accept();
				
				if (sc == null) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
				else {
					
			        System.out.println("Incoming connection from: " + sc.socket().getRemoteSocketAddress());
			        
			        
			        
			        sc.read(buffer);
	                buffer.flip(); // flip the buffer for reading
	                byte[] bytes = new byte[buffer.remaining()]; // create a byte array the length of the number of bytes written to the buffer
	                buffer.get(bytes); // read the bytes that were written
	                String incomintgString = new String(bytes);
	                System.out.println("String Incoming:" +incomintgString); // How do I know how much to read?
	                
	                String []  authdata = incomintgString.split(",");
	                String Username = authdata[0];
	                String Password = authdata[1];
	                
	                System.out.println("username" +Username);
	                
	                autoObj.setUsername(Username);
	                autoObj.setPassword(Password);
			        
	                currentUser = market.returnUser(autoObj);
	                
	                //System.out.println("From user Object " +currentUser.getAuth().getUsername());
	                		
			        String PortNumberToSend = Integer.toString(StoCPort);
			        buffer = ByteBuffer.wrap(PortNumberToSend.getBytes());
			        
			        new SendThread(StoCPort,market,currentUser).start();
			        new ReceiveThread(CtoSPort,market,currentUser).start();
			        //new ClientThread(StoCPort).start();
			        
			        System.out.println("started crossed");
			        
			        CtoSPort = CtoSPort + 2;
			        StoCPort = StoCPort + 2;
			      
			        buffer.rewind();
			        while(buffer.hasRemaining()) {
			            sc.write(buffer);
			        	//System.out.println("Outgoing Buffer " +buffer);
			        }		    
			        
			        sc.close();
				}
			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	//Filter IP address
	public static String getFilterIPAddresses() {
		String myIP = null;
		Enumeration<?> e;
		
		try {
			
			e = NetworkInterface.getNetworkInterfaces();
			
			//for each IP address check for the valid IP address
			while(e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				
				//get all the available IP addresses of the Server 
				Enumeration<?> ee = n.getInetAddresses();
				 
				//get the valid IP address 
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();	  
					if(i.getHostAddress().contains(".")) {
					  if(!i.getHostAddress().contains("127.0.")) {
						  myIP = i.getHostAddress();
						  System.out.println(myIP);
					  }
					}
				}
			}
		  } catch (SocketException e2) {
			// TODO Auto-generated catch block
				e2.printStackTrace();
		  }
		return myIP;
	}
	
	
	//save the IP Address and default port on the cloud 
	private static void saveServerIPToCloud() throws IOException {
		// set up the command and parameter
		String Port = Integer.toString(ListenPORT);
		String pythonScriptPath = "SaveServerIP.py";
		String[] cmd = new String[6];
		cmd[0] = "python"; // check version of installed python: python -V
		cmd[1] = pythonScriptPath;
		cmd[2] = "NishantServer";
		cmd[3] = ServerIP;
		cmd[4] = Port; //port
		cmd[5] = "true"; //running
		 
		// create runtime to execute external command
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(cmd);
		 
		// retrieve output from python script
		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		
		while((line = bfr.readLine()) != null) {
		// display each output line form python script
			System.out.println(line);
		}
	}

}
