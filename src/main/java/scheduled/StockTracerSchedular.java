package scheduled;

import dto.StockTracerObject;
import repository.ProductRepository;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.rules.M5Rules;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StockTracerSchedular extends BaseSchedular {

    private static final int TRACING_TIME_RANGE = 3000000;

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

            ArrayList<StockTracerObject> intervalData = new ArrayList<>();
            ResultSet rs = statement.executeQuery("select floor((TIMESTAMPDIFF(HOUR, mp.STOCKTIME, NOW()) / count(*)) * 60 * 60) as seconds, " +
                    "mp.MARKETID, mp.PRODUCTID, mp.STOCKTIME, STOK from SOLD s\n" +
                    "inner join Market_Products mp on mp.MARKETID = s.MARKETID and mp.PRODUCTID = s.PRODUCTID \n" +
                    "where s.TIMESTAMP between mp.STOCKTIME and NOW() group by mp.MARKETID, mp.PRODUCTID");
            while (rs.next()){
                intervalData.add(new StockTracerObject(rs.getInt(2), rs.getInt(3), rs.getInt(1), rs.getString(4), rs.getInt(5)));
            }

            String csv = new String();
            for(StockTracerObject object : intervalData){
                rs = statement.executeQuery("select TIMESTAMPDIFF(SECOND, STOCKTIME, NOW()) / " + object.getSeconds() + " from Market_Products " +
                        "where MARKETID = " + object.getMarketId() + " and PRODUCTID = " + object.getProductId());
                if(rs.next()){
                    int counter = rs.getInt(1);
                    for(int i=0; i<counter; i++){
                        rs = statement.executeQuery("select count(*) from SOLD where MARKETID = " + object.getMarketId() + " " +
                                "and PRODUCTID = " + object.getProductId() + " and TIMESTAMP between " +
                                "DATE_ADD('" + object.getStockTime() + "', INTERVAL " + ((i-1) * object.getSeconds()) + " SECOND) and " +
                                "DATE_ADD('" + object.getStockTime() + "', INTERVAL " + (i * object.getSeconds()) + " SECOND)");
                        if(rs.next()){
                            if(rs.getInt(1) != 0){
                                csv += object.getMarketId() + "," + object.getProductId() + "," + 1 + "\n";
                            }else {
                                csv += object.getMarketId() + "," + object.getProductId() + "," + 0 + "\n";
                            }
                        }else {
                            csv += object.getMarketId() + "," + object.getProductId() + "," + 0 + "\n";
                        }
                    }
                }

                try {

                    csv = "marketId, productId, isExist\n" + csv;
                    PrintWriter printWriter = new PrintWriter(new FileWriter(new File("temp.csv")));
                    printWriter.print(csv);
                    printWriter.close();

                    CSVLoader loader = new CSVLoader();
                    loader.setSource(new File("temp.csv"));
                    Instances data = loader.getDataSet();

                    // set outcome (which column? starts with 0)
                    // for last column use data.numAttributes() - 1
                    data.setClassIndex(2);


                    Classifier classifier = new weka.classifiers.functions.Logistic();
                    classifier.buildClassifier(data);

                    int counter = 0;
                    int stock = object.getStock();
                    while (counter < stock){
                        printWriter = new PrintWriter(new FileWriter(new File("temp.csv")));
                        printWriter.print("marketId, productId, isExist\n1," + object.getProductId() + ",?");
                        printWriter.close();

                        loader = new CSVLoader();
                        loader.setSource(new File("temp.csv"));
                        data = loader.getDataSet();

                        data.setClassIndex(data.numAttributes()-1);

                        if(classifier.classifyInstance(data.instance(0)) == Double.NaN || classifier.classifyInstance(data.instance(0)) < 0.5){
                            stock++;
                        }

                        counter++;
                    }

                    if(object.getStock() != 0){
                        statement.executeUpdate("insert into StockCounts (MARKETID, PRODUCTID, STOCK, APPROXIMATEENDTIME) " +
                                "values(" + object.getMarketId() + "," + object.getProductId() + "," + object.getStock() +
                                ",DATE_ADD(NOW(), INTERVAL " + (object.getSeconds() * counter) + " SECOND))");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

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
                        ", " + rand.nextInt(productList.size()-1) + ", 1,'" + generateRandomDate() + "'),";
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
