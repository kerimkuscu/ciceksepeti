package repository;

import bean.Location;
import dto.Interaction;
import dto.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Location> getLocations(int seconds, int userId, String now){
        ArrayList<Location> locationList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = this.createStatement();

            ResultSet rs = statement.executeQuery("select x,y,TIMESTAMP,MARKETID from Location " +
                    "where USERID = " + userId + " and TIMESTAMP between DATE_SUB('" + now + "', INTERVAL " + seconds + " second) and '" + now + "' order by TIMESTAMP asc");
            while (rs.next()){
                Location location = new Location();

                location.setX(rs.getInt(1));
                location.setY(rs.getInt(2));
                location.setTimeStamp(rs.getString(3));
                location.setMarketId(rs.getInt(4));

                locationList.add(location);
            }

            statement.executeUpdate("delete from Location " +
                    "where USERID = " + userId + " and TIMESTAMP between DATE_SUB('" + now + "', INTERVAL " + seconds + " second) and '" + now + "'");

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

    public List<Interaction> getInteractions(int userId){
        Statement statement = null;
        List<Interaction> interactionList= new ArrayList<>();

        try {
            statement = this.createStatement();
            ResultSet resultSet= statement.executeQuery("SELECT * FROM Interactions WHERE USERID="+userId);

            while (resultSet.next())
            {
                interactionList.add(new Interaction(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            cleanResources(statement);
        }
        return interactionList;
    }


    public List<Product> getRecommendeds(int userId){
        Statement statement = null;
        List<Product> productList= new ArrayList<>();

        CategoryRepository categoryRepository = new CategoryRepository();

        try {
            statement = this.createStatement();
            ResultSet resultSet= statement.executeQuery("(SELECT *\n" +
                    "FROM Product\n" +
                    "WHERE CATEGORYID IN (SELECT CATEGORYID FROM Product WHERE ID IN (SELECT PRODUCTID FROM Interactions WHERE USERID = 1))\n" +
                    "  AND ID NOT IN (SELECT PRODUCTID FROM Interactions WHERE USERID ="+userId +") LIMIT 5)\n" +
                    "\n" +
                    "UNION\n" +
                    "    (SELECT *\n" +
                    "FROM Product\n" +
                    "WHERE CATEGORYID IN (SELECT CATEGORYID FROM Product WHERE ID IN (SELECT PRODUCTID FROM Interactions WHERE USERID =" +userId+")) LIMIT 5)\n");

            while (resultSet.next())
            {
                productList.add(new Product(resultSet.getInt(1),
                        resultSet.getString(2),
                        categoryRepository.getCategoryById(resultSet.getInt(3)),
                        resultSet.getDouble(4),
                        resultSet.getString(5)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            cleanResources(statement);
        }
        return productList;
    }
}
