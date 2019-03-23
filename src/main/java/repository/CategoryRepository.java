package repository;

import dto.Category;
import dto.Interaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository extends BaseRepository {

    public Category getCategoryById(int id){
        Statement statement = null;

        Category retData = Category.getDummyCategory();
        try {
            statement = this.createStatement();
            ResultSet resultSet= statement.executeQuery("SELECT * FROM Category WHERE ID="+id);

            if(resultSet.next()){
                retData=new Category(resultSet.getInt(1), resultSet.getString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            cleanResources(statement);
        }
        return retData;
    }

}
