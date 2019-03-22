package repository;

import dto.Interaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InteractionRepository extends BaseRepository {

    public List<Interaction> getInteractions(int userId){
        Statement statement = null;
        List<Interaction> ınteractionList= new ArrayList<>();

        try {
            statement = this.createStatement();
            ResultSet resultSet= statement.executeQuery("SELECT * FROM Interactions WHERE USERID="+userId);

            while (resultSet.next())
            {
                ınteractionList.add(new Interaction(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            cleanResources(statement);
        }
        return ınteractionList;
    }

}
