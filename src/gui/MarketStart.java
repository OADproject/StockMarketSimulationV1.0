/** COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package gui;

import gui.FXMLDocumentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import connections.AIRequests;
import connections.NewConnectionThread;
import market.Market;

/**
 * @author nishant
 *
 */
public class MarketStart extends Application {

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
//		Market market = new Market();
//		market.start();
		
		NewConnectionThread newConnections = new NewConnectionThread(Market.getMarket()); 
		newConnections.start();
		
		AIRequests ai = new AIRequests(Market.getMarket()); 
		ai.start();
		
		
		//Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
		Parent root =  (Parent)fxmlLoader.load(); 
		
		FXMLDocumentController controller = fxmlLoader.<FXMLDocumentController>getController();
		
		Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Stock Market Admin Control Panel");
        stage.show();
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
