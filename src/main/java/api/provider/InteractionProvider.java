package api.provider;

import com.google.gson.Gson;
import dto.Interaction;
import org.json.JSONArray;
import org.json.JSONObject;
import repository.InteractionRepository;
import repository.LocationRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

public class InteractionProvider {

    public static JSONObject getInteractions(HttpServletRequest request, int userId){

        InteractionRepository repository = new InteractionRepository();
        JSONArray interactionList= repository.getInteractions(userId);


        return new JSONObject(){{put("status", 200); put("data", interactionList);}};

    }

    public void interactionToProduct()
    {

    }

}
