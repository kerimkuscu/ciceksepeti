package scheduled;

import bean.Location;
import repository.InteractionRepository;
import repository.LocationRepository;
import repository.ProductRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StockTracerSchedular extends BaseSchedular {

    private static final int TRACING_TIME_RANGE = 30000;

    private ProductRepository repository;

    public StockTracerSchedular(){
        super();

        initialize();
    }

    public void initialize(){
        try{
            this.repository = new ProductRepository();

            System.out.println("Scheduling Stock Tracer ...");
            Timer stockTracer = new Timer();
            stockTracer.schedule(new StockTracerTask(), 0, TRACING_TIME_RANGE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startTracing(){
        Statement statement = null;

        try {
            statement = repository.createStatement();

            ArrayList<Integer> marketIdList = new ArrayList<>();
            ResultSet rs = statement.executeQuery("select ID from Market");
            while (rs.next()){
                marketIdList.add(rs.getInt(1));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            repository.cleanResources(statement);
        }
    }

    public void produceRandomSoldRecord(){
        Statement statement = null;

        try {
            statement = repository.createStatement();

            ArrayList<Integer> marketIdList = new ArrayList<>();
            ResultSet rs = statement.executeQuery("select ID from Market");
            while (rs.next()){
                marketIdList.add(rs.getInt(1));
            }

            ArrayList<Integer> userList = new ArrayList<>();
            rs = statement.executeQuery("select ID from User");
            while (rs.next()){
                userList.add(rs.getInt(1));
            }

            ArrayList<Integer> productList = new ArrayList<>();
            rs = statement.executeQuery("select ID from Product");
            while (rs.next()){
                productList.add(rs.getInt(1));
            }

            Random rand = new Random();
            String sql = "insert into SOLD (USERID, PRODUCTID, MARKETID, TIMESTAMP) values ";
            for (int i=0; i<500; i++){
                sql += "(" + rand.nextInt(userList.size()-1) +
                        ", " + rand.nextInt(productList.size()-1) + "," + rand.nextInt(marketIdList.size()) + ",'" + generateRandomDate() + "'),";
            }

            statement.executeUpdate(sql.substring(0, sql.length()-1));

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            repository.cleanResources(statement);
        }
    }

    public String generateRandomDate () {
        String result = new String();

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal=Calendar.getInstance();
            String str_date1="2019-01-01 02:10:15";
            String str_date2="2019-23-03 02:10:20";

            cal.setTime(formatter.parse(str_date1));
            Long value1 = cal.getTimeInMillis();

            cal.setTime(formatter.parse(str_date2));
            Long value2 = cal.getTimeInMillis();

            long value3 = (long)(value1 + Math.random()*(value2 - value1));
            cal.setTimeInMillis(value3);
            result = formatter.format(cal.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public class StockTracerTask extends TimerTask {
        @Override
        public void run() {
            startTracing();
        }
    }

}
