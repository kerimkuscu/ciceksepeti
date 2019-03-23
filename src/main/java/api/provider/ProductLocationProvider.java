package api.provider;

import org.json.JSONArray;
import org.json.JSONObject;
import repository.ProductRepository;

public class ProductLocationProvider {

    public static JSONObject getProductLocationList(int marketId){
        JSONObject result = new JSONObject();

        ProductRepository repository = new ProductRepository();
        JSONArray productLocations = repository.getProductLocationList(marketId);

        result.put("data", productLocations);
        result.put("status", 200);
        return result;
    }

}
