/**
 * COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package gui;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import market.Market;
import market.Stock;
import users.Authentication;
import users.User;

/**
 *
 * @author Prateek
 */
public class FXMLDocumentController extends Thread implements Initializable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5555);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("updating market");
            updateMarket();
        }
    }

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab viewMarketTab;

    @FXML
    private TextField usernameInput;
    StringProperty usernameInputProp = new SimpleStringProperty();

    @FXML
    private TextField passwordInput;
    StringProperty passwordInputProp = new SimpleStringProperty();

    @FXML
    private Label LoginError;

    @FXML
    private TextField usernameSearchField;

    @FXML
    private TextArea currentMarketStockPricesArea;
    private static StringProperty currentMarketStockPricesTextAreaProp = new SimpleStringProperty();

    @FXML
    private TextArea userInfoTextArea;
    @FXML
    private TextField usernameEdit;
    @FXML
    private TextField passwordEdit;
    @FXML
    private TextField balanceEdit;
    @FXML
    private TextField stockNameEdit;
    @FXML
    private TextField stockQtyEdit;
    @FXML
    private TextArea currentStockTextArea;
    @FXML
    private TextField stockNameField;
    @FXML
    private TextField stockPriceField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentMarketStockPricesArea.textProperty().bind(currentMarketStockPricesTextAreaProp);

    }

    public void validateLogin(ActionEvent actionEvent) {
        LoginError.setText("");
        if (usernameInput.getText().equals("admin") && passwordInput.getText().equals("admin")) {
            mainTabPane.getSelectionModel().select(viewMarketTab);
        } else {
            LoginError.setText("User Name or Password is Incorrect");
        }

    }

    public void deleteUser(ActionEvent actionEvent) {
    }

    public void editUsedInfo(ActionEvent actionEvent) {
        String usn = usernameEdit.getText();
        String pwd = passwordEdit.getText();
        double balance = Double.parseDouble(balanceEdit.getText());
        String stkname = stockNameEdit.getText();
        int stkqty = Integer.parseInt(stockQtyEdit.getText());

        Market m = Market.getMarket();
        List<User> userList = m.getUserList();
        User dispUser = null;
        for (User u : userList) {
            Authentication a = u.getAuth();
            if (a.getUsername().equals(usn)) {
                dispUser = u;
                break;
            }
        }

        if (usn.equals("") == false) {
            dispUser.getAuth().setUsername(usn);
        }
        if (pwd.equals("") == false) {
            dispUser.getAuth().setPassword(pwd);
        }
        if (balanceEdit.getText().equals("") == false) {
            dispUser.getPortfolio().setMoneyBalance(balance);
        }
        if (stockNameEdit.getText().equals("") == false) {
            List<Stock> userStocks = dispUser.getPortfolio().getStocks();
            Stock temp = null;
            for (Stock s : userStocks) {
                if (s.getStockName().equals(stkname)) {
                    temp = s;
                    break;
                }
            }
            temp.setStockName(stkname);
            if (stockQtyEdit.getText().equals("") == false) {
                temp.setStockQty(stkqty);
            }
        }
    }

    public void viewUserStocks(ActionEvent actionEvent) {
        String usn = usernameSearchField.getText();
        Market m = Market.getMarket();
        List<User> userList = m.getUserList();
        User dispUser = null;
        for (User u : userList) {
            Authentication a = u.getAuth();
            if (a.getUsername().equals(usn)) {
                dispUser = u;
                break;
            }
        }
        String output = "Name: " + dispUser.getName() + "\n" + "Phone: " + dispUser.getPhoneNumber() + "\n" + "Address: " + dispUser.getAddress() + "\n" + "Money Balance: " + dispUser.getPortfolio().getMoneyBalance() + "\n";
        String output2 = "=================\n";
        String pfolio = "Stocks Owned - >\n";
        List<Stock> userStocks = dispUser.getPortfolio().getStocks();
        String st = "";
        for (Stock s : userStocks) {
            st = st.concat("Stock Name: " + s.getStockName() + "\nStock Qty: " + s.getStockQty() + "\n\n");
        }
        userInfoTextArea.setText(output + output2 + pfolio + st);
    }

    public void viewUserBalance(ActionEvent actionEvent) {
    }

    public void viewCurrentStocks(ActionEvent actionEvent) {
        Market m = Market.getMarket();
        StringBuilder sb = new StringBuilder();
        Map<String, Double> vals = m.getCurrentStockValues();
        for (String s : vals.keySet()) {
            sb.append(s);
            sb.append(" : ");
            sb.append(vals.get(s));
            sb.append("\n");
        }
        currentStockTextArea.setText(sb.toString());
    }

    public void updateMarket(/*ActionEvent actionEvent*/) {
//        currentMarketStockPricesArea.setText("Market Started");
        Market m = Market.getMarket();
        StringBuilder sb = new StringBuilder();
        Map<String, Double> vals = m.getCurrentStockValues();
        for (String s : vals.keySet()) {
            sb.append(s);
            sb.append(" : ");
            sb.append(vals.get(s));
            sb.append("\n");
        }
        // currentMarketStockPricesArea.setText(sb.toString());
        setFXText(currentMarketStockPricesTextAreaProp, sb.toString());
    }

    public void startMarket(ActionEvent actionEvent) {
        Market m = Market.getMarket();
        System.out.println("marketStarted");
        //updateMarket(new ActionEvent());

    }

    public void stopMarket(ActionEvent actionEvent) {
        //currentMarketStockPricesArea.setText("Market Stopped");
        setFXText(currentMarketStockPricesTextAreaProp, "Market Stopped");
    }

    public void addNewStock(ActionEvent actionEvent) {
        String stkname = stockNameField.getText();
        double stkprice = Double.parseDouble(stockPriceField.getText());

        Market m = Market.getMarket();
        m.addStock(stkname, stkprice, 0);
    }

    public void editStock(ActionEvent actionEvent) {
        String stkname = stockNameField.getText();
        double stkprice = Double.parseDouble(stockPriceField.getText());

        Market m = Market.getMarket();
        Map<String, Double> stocks = m.getCurrentStockValues();
        List<Stock> global = m.getMarketStocks();
        stocks.put(stkname, stkprice);
        Stock s = null;
        for (Stock i : global) {
            if (i.getStockName().equals(stkname)) {
                s = i;
                break;
            }
        }
        s.setStockUnitPrice(stkprice);

    }

    public boolean setFXText(StringProperty sp, String s) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sp.set(s);
            }
        });
        return true;
    }
}
