package repository;

import bean.ProductLocation;
import dto.Interaction;
import dto.Product;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends BaseRepository {

    public Product getProductById(int id){
        Statement statement = null;
        Product product= Product.getDummyProduct();
        try {
            statement = this.createStatement();
            ResultSet resultSet= statement.executeQuery("SELECT * FROM Product WHERE ID="+id);

            CategoryRepository categoryRepository = new CategoryRepository();

            while (resultSet.next())
            {
                product= new Product(resultSet.getInt(1),
                        resultSet.getString(2),
                        categoryRepository.getCategoryById(resultSet.getInt(3)),
                        resultSet.getDouble(4),
                        resultSet.getString(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            cleanResources(statement);
        }
        return product;
    }

    public JSONArray getProductLocationList(int marketId){
        Statement statement = null;
        JSONArray productList = new JSONArray();

        try {
            statement = this.createStatement();

            ResultSet rs = statement.executeQuery("select PRODUCTID, x, y from Market_Products where MARKETID = " + marketId);
            while (rs.next()){
                JSONObject object = new JSONObject();

                object.put("productId", rs.getInt(1));
                object.put("x", rs.getInt(2));
                object.put("y", rs.getInt(3));

                productList.put(object);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.cleanResources(statement);
        }

        return productList;
    }

}
