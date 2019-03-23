package repository;

import dto.Interaction;
import dto.Product;

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
                product= new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), categoryRepository.getCategoryById(resultSet.getInt(4)), resultSet.getString(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            cleanResources(statement);
        }
        return product;
    }

}
