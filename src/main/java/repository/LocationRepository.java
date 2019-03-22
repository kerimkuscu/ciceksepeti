package repository;

import java.sql.SQLException;
import java.sql.Statement;

public class LocationRepository extends BaseRepository {

    public void saveLocation(int x, int y, int userId, int marketId){
        Statement statement = null;

        try {
            statement = this.createStatement();

            statement.executeUpdate("insert into Location (x,y,USERID,MARKETID,TIMESTAMP) values(" + x + ", " + y + ", " + userId + ", " + marketId + ", NOW())");

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            cleanResources(statement);
        }

    }

}
