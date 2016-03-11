/** COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package market;
public class Stock
{
    
    private String stockName;
    private double stockUnitPrice;
    private int stockQty;
    
    public Stock(String name,double price,int qty)
    {
        stockName = name;
        stockUnitPrice = price;
        stockQty = qty;
    }
    
    public boolean setStockName(String name)
    {
        stockName = name;
        return true;
    }
    public boolean setStockUnitPrice(double price)
    {
        stockUnitPrice = price;
        return true;
    }
    public boolean updateStockUnitPrice(double price)
    {
        stockUnitPrice = price;
        return true;
    }
    public boolean setStockQty(int qty)
    {
        stockQty = qty;
        return true;
    }
    public String getStockName()
    {
        return stockName;
    }
    public double getUnitPrice()
    {
        return stockUnitPrice;
    }
    public int getStockQty()
    {
        return stockQty;
    }
    public synchronized int buyStock(int quantity)
    {
        if(quantity > stockQty)
        {
            return 0;
        }
        else
        {
            stockQty -= quantity;
            return quantity;
        }
    }
        
    
}