package api.provider;

import org.json.JSONObject;
import repository.StockTracerRepository;

public class StockTracerProvider {

    public static JSONObject getStockTracerData(int marketId){
        JSONObject result = new JSONObject();

        result.put("data", new StockTracerRepository().getStockInfo(marketId));

        result.put("status", 200);
        return result;
    }

}
