package api.provider;

import org.json.JSONObject;
import repository.LocationRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

public class LocationProvider {

    public static JSONObject saveLocation(HttpServletRequest request, int x, int y, int marketId, int userId){

        LocationRepository repository = new LocationRepository();
        JSONObject result = new JSONObject();

         repository.saveLocation(x,y,userId,marketId);
         result.put("status", 200);

         return result;
    }

}
