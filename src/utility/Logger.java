/**
 * COEN 275 OOAD (Winter 2016)
 * Group Project
 * Team 8
 */
package utility;

import market.BuySell;
import users.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import market.Market;
import market.Stock;

public class Logger {
    private static long lastCallMarket;
    private static long lastCallUser;

    public Logger() {
        lastCallUser = System.currentTimeMillis() / 1000;
        lastCallMarket = System.currentTimeMillis() / 1000;
    }


    public void updateLogs(List<User> userList, Market market) throws IOException {
        updateUserLogs(userList);
        updateMarketLogs(market);
        //System.out.println("here1");
    }

    public void updateUserLogs(List<User> userList) throws IOException {
        //FileWriter fw = new FileWriter(fileName,true);
        long currentTime = System.currentTimeMillis() / 1000;
        if (currentTime - lastCallUser < 10) {
            return;
        }
        //System.out.println("here2");
        lastCallUser = System.currentTimeMillis() / 1000;

        if (checkFiles(userList) == false) {
            initFiles(userList);
        }
        for (User i : userList) {
            FileWriter fw = new FileWriter("USR" + i.getID() + ".csv", true);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String data = sdf.format(d) + "," + i.getPortfolio().getMoneyBalance() + ",";
            //System.out.println(data);
            fw.write(data);
            int x = 0;
            for (Stock s : i.getPortfolio().getStocks()) {
                String data2;
                if (x == 0) {
                    data2 = s.getStockName() + "," + s.getStockQty() + "\n";
                } else {
                    data2 = " , ," + s.getStockName() + "," + s.getStockQty() + "\n";
                }
                //System.out.println(data2);
                fw.write(data2);
                x++;
            }
            fw.close();
        }

    }

    public void updateMarketLogs(Market market) throws IOException {
        long currentTime = System.currentTimeMillis() / 1000;
        if (currentTime - lastCallMarket < 5) {
            return;
        }
        lastCallMarket = System.currentTimeMillis() / 1000;

        if (checkFiles(market) == false) {
            initFiles(market);
        }
        List<Stock> globalStocks = market.getMarketStocks();
        List<BuySell> buyRequest = market.getBuyRequest();
        List<BuySell> sellRequest = market.getSellRequest();
        // String header = "Time,Stock Name, Market Vale, Buy Requests, Sell Requests\n";
        FileWriter fw = new FileWriter("MarketDatabase.csv", true);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

        for (Stock s : globalStocks) {
            String data = sdf.format(d) + "," + s.getStockName() + "," + s.getUnitPrice() + ","
                    + getNumOfRequest(buyRequest, s) + "," + getNumOfRequest(sellRequest, s) + "\n";
            //System.out.println(data);
            fw.write(data);
        }
        fw.close();
    }

    private String getNumOfRequest(List<BuySell> sellRequest, Stock s) {
        int diff = 0;
        for (BuySell r : sellRequest) {
            if (r.getStockName().equals(s.getStockName())) {
                diff += r.getQuantity();
            }
        }
        return String.valueOf(diff);
    }


    public boolean checkFiles(List<User> userList) {
        for (User i : userList) {
            File f = new File("USR" + i.getID() + ".csv");
            if ((f.exists() && !f.isDirectory()) == false) {
                return false;
            }
        }
        return true;
    }

    public void initFiles(List<User> userList) throws IOException {
        for (User i : userList) {

            FileWriter fw = new FileWriter("USR" + i.getID() + ".csv", true);
            String header = "UserName:," + i.getAuth().getUsername() + "\n" + "Name:," + i.getName() + "\n" + "SSN:," + i.getSSN() + "\n" + "Address:," + i.getAddress() + "\n" + "Phone:," + i.getPhoneNumber() + "\n\n";
            //System.out.println(header);
            fw.write(header);
            String header2 = "Time,Balance,Stock Name,Stock Units\n";
            //System.out.println(header2);
            fw.write(header2);
            //System.out.println("userFiles Created");
            fw.close();
        }
    }

    public boolean checkFiles(Market m) {
        File f = new File("MarketDatabase.csv");
        if ((f.exists() && !f.isDirectory()) == false) {
            return false;
        }
        return true;

    }

    public void initFiles(Market m) throws IOException {
        FileWriter fw = new FileWriter("MarketDatabase.csv", true);
        String header = "Time,Stock Name, Market Value, Buy Requests, Sell Requests\n";
        //System.out.println(header);
        fw.write(header);
        //System.out.println("system files created");
        fw.close();
    }


}