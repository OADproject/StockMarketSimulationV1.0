/** COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package market;
import java.text.SimpleDateFormat;

public class TransactionHistory {
    String timeStamp;
    String stockName;
    double stockUnitPrice;
    int stockQuantity;
    boolean buyOrSell; //buy= true, sell = false;

    public TransactionHistory() {

    }

    public TransactionHistory(String stock_name, double stock_unit_price, int stock_quantity, boolean buy_sell) {
        timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        stockName = stock_name;
        stockUnitPrice = stock_unit_price;
        stockQuantity = stock_quantity;
        buyOrSell = buy_sell;

    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getStockName() {
        return stockName;
    }

    public double getStockUnitPrice() {
        return stockUnitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public boolean setStockName(String sn) {
        stockName = sn;
        return true;
    }

    public boolean setStockUnitPrice(double sp) {
        stockUnitPrice = sp;
        return true;
    }

    public boolean setStockQuantity(int qty) {
        stockQuantity = qty;
        return true;
    }

    public boolean setBuyOrSell(boolean bs) {
        buyOrSell = bs;
        return true;
    }

    public boolean isBuy() {
        return buyOrSell;
    }

    public boolean isSell() {
        return !buyOrSell;
    }


}