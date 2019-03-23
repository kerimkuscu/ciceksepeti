package api.provider;

import bean.ProductLocation;
import org.json.JSONArray;
import org.json.JSONObject;
import repository.ProductRepository;

import java.util.ArrayList;

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
