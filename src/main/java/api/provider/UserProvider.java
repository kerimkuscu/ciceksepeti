package api.provider;

import bean.User;
import org.json.JSONObject;
import repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;

public class UserProvider {

    public static JSONObject getUserInfo(HttpServletRequest request, MultivaluedMap<String, String> form){
        JSONObject result = new JSONObject();
        UserRepository repository = new UserRepository();

        User user = repository.getUserInfo(Integer.parseInt(form.get("userId").get(0)));
        result.put("name", user.getName());
        result.put("surname", user.getSurname());
        result.put("age", user.getAge());
        result.put("url", user.getUrl());

        result.put("status", 200);
        return result;
    }

}
