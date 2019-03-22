package api.provider;

import com.google.gson.Gson;
import dto.Interaction;
import org.json.JSONObject;
import repository.InteractionRepository;
import repository.LocationRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

public class InteractionProvider {

    public static JSONObject getInteractions(HttpServletRequest request, MultivaluedMap<String, String> form){

        InteractionRepository repository = new InteractionRepository();
        List<Interaction> ınteractionList= repository.getInteractions(Integer.parseInt(form.get("userId").get(0)));

        Gson g= new Gson();
        String jsonString = g.toJson(ınteractionList);
        return new JSONObject(jsonString).put("status", 200);

    }

}
