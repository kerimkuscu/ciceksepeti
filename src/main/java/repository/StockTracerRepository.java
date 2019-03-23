package repository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockTracerRepository extends BaseRepository {

    public JSONArray getStockInfo(int marketId){
        Statement statement = null;
        JSONArray list = new JSONArray();

        try {
            statement = this.createStatement();

            ResultSet rs = statement.executeQuery("select Market.NAME, Product.Name, StockCounts.STOCK, StockCounts.APPROXIMATEENDTIME " +
                    "from StockCounts inner join Market on Market.ID = StockCounts.MARKETID\n" +
                    "inner join Product on StockCounts.PRODUCTID = Product.ID order by APPROXIMATEENDTIME asc limit 5");
            while (rs.next()){
                JSONObject object = new JSONObject();

                object.put("marketName", rs.getString(1));
                object.put("productName", rs.getString(2));
                object.put("stock", rs.getInt(3));
                object.put("apprixamateDateTime", rs.getString(4));

                list.put(object);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.cleanResources(statement);
        }

        return list;
    }

}
