package repository;

import bean.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InteractionRepository extends BaseRepository {

    private static final Integer CLOSENESS = 2;

    public ArrayList<Integer> getActiveUsers(int seconds){
        ArrayList<Integer> userList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = this.createStatement();

            ResultSet rs = statement.executeQuery("select distinct USERID from Location where TIMESTAMP between DATE_SUB(NOW(), INTERVAL " + seconds + " second) and NOW()");
            while (rs.next()){
                userList.add(rs.getInt(1));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            cleanResources(statement);
        }

        return userList;
    }

    public ArrayList<Location> getLocations(int seconds, int userId){
        ArrayList<Location> locationList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = this.createStatement();
            ;
            ResultSet rs = statement.executeQuery("select x,y,TIMESTAMP,MARKETID from Location " +
                    "where USERID = " + userId + " and TIMESTAMP between DATE_SUB(NOW(), INTERVAL " + seconds + " second) and NOW() order by TIMESTAMP asc");
            while (rs.next()){
                Location location = new Location();

                location.setX(rs.getInt(1));
                location.setY(rs.getInt(2));
                location.setTimeStamp(rs.getString(3));
                location.setMarketId(rs.getInt(4));

                locationList.add(location);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            cleanResources(statement);
        }

        return locationList;
    }

    public ArrayList<Integer> getClosestProducts(int x, int y, int marketId){
        ArrayList<Integer> closestProductIdList = new ArrayList<>();
        Statement statement = null;

        try {
            statement = this.createStatement();

            ResultSet rs = statement.executeQuery("select PRODUCTID from Market_Products " +
                    "where MARKETID = " + marketId + " and (SQRT((ABS(x-" + x + ") * ABS(x-" + x + ")) + (ABS(y-" + y + ") * ABS(y-" + y + ")))) < " + CLOSENESS);
            while (rs.next()){
                closestProductIdList.add(rs.getInt(1));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.cleanResources(statement);
        }

        return closestProductIdList;
    }

    public void createInteraction(int userId, int productId){
        Statement statement = null;

        try {

            statement = this.createStatement();

            statement.executeUpdate("insert into Interactions (USERID, PRODUCTID) values(" + userId + ", " + productId + ")");

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.cleanResources(statement);
        }
    }

}
