package api.provider;

import com.google.gson.Gson;
import dto.Interaction;
import dto.Product;
import dto.Recommendation;
import org.json.JSONObject;
import repository.InteractionRepository;
import repository.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationProvider {

    public JSONObject getRecommendations(HttpServletRequest request, int userId){

        InteractionRepository repository = new InteractionRepository();

        //TODO: interactions olarak değil ilerde düzelecek..
        List<Product> interactionList= repository.getRecommendeds(userId);

        Gson g= new Gson();
        String jsonString = g.toJson(interactionList);
        return new JSONObject("{recommendations:"+jsonString+"}").put("status", 200);

    }
    public List<Product> interactionToProduct(List<Interaction> interactionList)
    {
        ProductRepository productRepository= new ProductRepository();

        List<Product> recommendationProducts = interactionList.stream().map(e-> productRepository.getProductById(e.getId())).collect(Collectors.toList());
        return recommendationProducts;
    }

}
