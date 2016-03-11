/** COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package users;


import java.util.ArrayList;
import java.util.List;
import market.Stock;
import market.TransactionHistory;

import market.BuySell;

public class Portfolio {
    private double moneyBalance;
    private List<Stock> stocks = new ArrayList<Stock>();
    private List<TransactionHistory> transactionHistory = new ArrayList<TransactionHistory>();

    public Portfolio(double balance, List<Stock> stocks, List<TransactionHistory> historyList) {
        this.moneyBalance = balance;
        this.stocks = stocks;
        this.transactionHistory = historyList;
    }

    public boolean addStock(String name, double price, int qty) {
        Stock s = new Stock(name, price, qty);
        stocks.add(s);
        return true;
    }

    public boolean deleteStock(String name) {
        int len = stocks.size();
        boolean flag = false;
        for (Stock i : stocks) {
            String test = i.getStockName();
            if (test.equals(name)) {
                stocks.remove(i);
                flag = true;
                break;
            }
        }
        return flag;
    }

    public boolean setMoneyBalance(double money) {
        moneyBalance = money;
        return true;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public double getMoneyBalance() {
        return moneyBalance;
    }

    public boolean addTransaction(String stockName, double stockUnitPrice, int stockQuantity, boolean buyOrSell) {
        TransactionHistory t = new TransactionHistory(stockName, stockUnitPrice, stockQuantity, buyOrSell);
        transactionHistory.add(t);
        return true;
    }

    public List<TransactionHistory> getTransactionHistory() {
        return transactionHistory;
    }

    public boolean updatePortfolio(BuySell order) {

        if (order.isBuy()) {
            setMoneyBalance(getMoneyBalance() - (order.getQuantity() * order.getUnitPrice()));
            for (Stock s : stocks) {
                if (s.getStockName().equals(order.getStockName())) {
                    s.setStockQty(s.getStockQty() + order.getQuantity());
                    getTransactionHistory().add(new TransactionHistory(s.getStockName(), order.getUnitPrice(), order.getQuantity(), true));
                    break;
                }
            }
        } else {
            setMoneyBalance(getMoneyBalance() + (order.getQuantity() * order.getUnitPrice()));
            for (Stock s : stocks) {
                if (s.getStockName().equals(order.getStockName())) {
                    s.setStockQty(s.getStockQty() - order.getQuantity());
                    getTransactionHistory().add(new TransactionHistory(s.getStockName(), order.getUnitPrice(), order.getQuantity(), false));
                    break;
                }
            }

        }

        return true;
    }

    public int getTotalStockQuantity(){
        int c = 0;
        for (Stock s: stocks) {
            c += s.getStockQty();
        }
        return  c;
    }


}