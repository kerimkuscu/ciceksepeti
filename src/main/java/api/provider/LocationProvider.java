package api.provider;

import org.json.JSONObject;
import repository.LocationRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

public class LocationProvider {

    public static JSONObject saveLocation(HttpServletRequest request, MultivaluedMap<String, String> form){

        LocationRepository repository = new LocationRepository();
        JSONObject result = new JSONObject();

        int x = Integer.parseInt(form.get("x").get(0));
        int y = Integer.parseInt(form.get("y").get(0));
        int marketId = Integer.parseInt(form.get("marketId").get(0));
        int userId = Integer.parseInt(form.get("userId").get(0));

         repository.saveLocation(x,y,userId,marketId);
         result.put("status", 200);

         return result;
    }

}
