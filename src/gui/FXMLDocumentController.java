/**
 * COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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
                Thread.sleep(2222);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("updating market");
            updateChart();
            updateMarket();
        }
    }



    @FXML
    private Pane chartPane;

    @FXML
    private static LineChart lineChart;

    @FXML
    private Tab testTab;

    @FXML
    private Button chartUpdateButton;

    @FXML
    private LineChart marketChart;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab viewMarketTab;

    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

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


    private static List<XYChart.Series> seriesList = new ArrayList<>();

    public static List<XYChart.Series> getSeriesList() {
        return seriesList;
    }

    public static void setSeriesList(List<XYChart.Series> seriesList) {
        FXMLDocumentController.seriesList = seriesList;
    }

    public static boolean addSeries(XYChart.Series s){
        seriesList.add(s);
        return true;
    }

    private static XYChart.Series amazonSeries;// = new XYChart.Series();
    private static XYChart.Series faceBookSeries;// = new XYChart.Series();
    private static XYChart.Series googleSeries;// = new XYChart.Series();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentMarketStockPricesArea.textProperty().bind(currentMarketStockPricesTextAreaProp);
        createChart();
//        amazonSeries = createSeries("amazon");
//        addSeriesChart(amazonSeries);

        start();


    }

    private void createChart() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle(new SimpleDateFormat().format(new Date()));lineChart.setPrefHeight(500);
        lineChart.setPrefWidth(800);
        chartPane.getChildren().addAll(lineChart);

    }

    private void addSeriesChart(XYChart.Series series) {
        seriesList.add(series);
        lineChart.getData().add(series);
    }

    private XYChart.Series createSeries(String name) {
        Random random = new Random();
        XYChart.Series series = new XYChart.Series();
        series.setName(name);
        for (int i = 0; i < 20; i++) {
            series.getData().add(new XYChart.Data(i, 100));
        }
        return  series;
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

    public void updateChart(ActionEvent actionEvent) {
        int i = new Random().nextInt(22);
        try{
            for (XYChart.Series s: seriesList) {
                reduceSeriesXValue(s, i);
            }
        }catch (NumberFormatException ex){
            System.out.println(ex.toString());
        }
    }

    private void updateChart() {
        updateSeriesList();
        try{
            for (XYChart.Series s: seriesList) {
                double i = new Random().nextInt(22);
//                double i = getCurrentStockValue(s.getName());
                reduceSeriesXValue(s, i);
            }
        }catch (NumberFormatException ex){
            System.out.println(ex.toString());
        }
    }

    private void updateSeriesList() {
        if(Market.getMarket().getCurrentStockValues().size() != seriesList.size()){
            for (String s: Market.getMarket().getCurrentStockValues().keySet()) {
                if(!isInSeriesList(s)){
                    XYChart.Series ser =  createSeries(s);
                    addSeries(ser);
                }
            }
        }
    }

    private boolean isInSeriesList(String s) {
        for (XYChart.Series ser : seriesList) {
            if(s.equals(ser.getName().toLowerCase())){
               return true;
            }
        }
        return false;
    }

    private double getCurrentStockValue(String name) {
        return Market.getMarket().getCurrentStockValues().get(name);
    }

    public void reduceSeriesXValue(XYChart.Series series, double newValue){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lineChart.setTitle("");
                lineChart.setTitle(new SimpleDateFormat().format(new Date()));lineChart.setPrefHeight(500);
                series.getData().remove(0);
                int numOfPoint = series.getData().size();
                for(int i=0; i<numOfPoint; i++){
                    XYChart.Data<Number, Number> data =
                            (XYChart.Data<Number, Number>)series.getData().get(i);
                    int x = (int)data.getXValue();
                    data.setXValue(x-1);
                }
                series.getData().add(new XYChart.Data(numOfPoint, newValue));
            }
        });

    }
}
