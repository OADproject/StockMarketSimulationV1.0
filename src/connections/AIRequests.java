/** COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package connections;

import java.util.Random;

import market.Market;

/**
 * @author nishant
 *
 */
public class AIRequests implements Runnable {
	
	private Thread t;
	private String threadName = "NewConnections Thread";
	private Market market;

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	
	public AIRequests (Market runningMarket) {
		
		this.market = runningMarket;
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
	
	private void AICreatedRequests () {
		String SampleBuyRequest1 = "amazon,123,surag,101,100,true"; //stockname, userId, userName,unitPrice,quantity
		String SampleBuyRequest2 = "google,123,surag,101,110,true";
		String SampleBuyRequest3 = "facebook,123,surag,101,80,true";
		String SampleBuyRequest4 = "google,123,surag,101,70,false";
		String SampleBuyRequest5 = "facebook,123,surag,101,80,false";
		String SampleBuyRequest6 = "amazon,123,surag,101,95,false";
		String SampleBuyRequest7 = "amazon,123,surag,101,120,true";
		String SampleBuyRequest8 = "google,123,surag,101,85,true";
		String SampleBuyRequest9 = "facebook,123,surag,101,90,false";
		String SampleBuyRequest10 = "amazon,123,surag,101,100,false";
		
                
                
		Random rn = new Random();
    	int answer = rn.nextInt(10);
    	
    	switch (answer) {
        case 1:  market.createBuySell(SampleBuyRequest1);
                 break;
        case 2:  market.createBuySell(SampleBuyRequest2);;
                 break;
        case 3:  market.createBuySell(SampleBuyRequest3);;
                 break;
        case 4:  market.createBuySell(SampleBuyRequest4);;
                 break;
        case 5:  market.createBuySell(SampleBuyRequest5);;
                 break;
        case 6:  market.createBuySell(SampleBuyRequest6);;
                 break;
        case 7:  market.createBuySell(SampleBuyRequest7);;
                 break;
        case 8:  market.createBuySell(SampleBuyRequest8);;
        	 break;
        case 9:  market.createBuySell(SampleBuyRequest9);;
        	 break;
        case 10: market.createBuySell(SampleBuyRequest10);;
        	 break;
        default: market.createBuySell(SampleBuyRequest4);;
        		
    	}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
                
               
                
                
		while(true) {
			AICreatedRequests();
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

	}

}
