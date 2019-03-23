package scheduled;

import bean.Location;
import repository.InteractionRepository;
import repository.LocationRepository;
import repository.ProductRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class StockTracerSchedular extends BaseSchedular {

    private static final int TRACING_TIME_RANGE = 30;

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

    public class StockTracerTask extends TimerTask {
        @Override
        public void run() {
            startTracing();
        }
    }

}
