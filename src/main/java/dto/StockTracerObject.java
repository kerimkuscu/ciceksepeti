package dto;

public class StockTracerObject {

    private int marketId;
    private int productId;
    private int seconds;
    private String stockTime;
    private int stock;

    public StockTracerObject(int marketId, int productId, int seconds, String stockTime, int stock) {
        this.marketId = marketId;
        this.productId = productId;
        this.seconds = seconds;
        this.stockTime = stockTime;
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStockTime() {
        return stockTime;
    }

    public void setStockTime(String stockTime) {
        this.stockTime = stockTime;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return "StockTracerObject{" +
                "marketId=" + marketId +
                ", productId=" + productId +
                ", seconds=" + seconds +
                ", stockTime='" + stockTime + '\'' +
                '}';
    }
}
