package api.provider;

import dto.User;
import org.json.JSONObject;
import repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

public class UserProvider {

    public static JSONObject getUserInfo(HttpServletRequest request, int userId){
        JSONObject result = new JSONObject();
        UserRepository repository = new UserRepository();

        User user = repository.getUserInfo(userId);
        result.put("name", user.getName());
        result.put("surname", user.getSurname());
        result.put("age", user.getAge());
        result.put("url", user.getUrl());

        result.put("status", 200);
        return result;
    }

}
