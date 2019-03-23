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
        List<Interaction> interactionList= repository.getInteractions(Integer.parseInt(form.get("userId").get(0)));

        Gson g= new Gson();
        String jsonString = g.toJson(interactionList);
        return new JSONObject("{interactions: "+jsonString+"}").put("status", 200);

    }

    public void interactionToProduct()
    {

    }

}
