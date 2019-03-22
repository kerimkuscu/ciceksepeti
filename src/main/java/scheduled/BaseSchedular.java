package scheduled;

import repository.BaseRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

public class BaseSchedular implements ServletContextListener {

    protected static final int MINUTEINMILLISECONDS = 60000;
    protected static final int DAYINMILLISECONDS    = 86400000;

    protected BaseRepository baseRepository;
    protected int tradeListControlRange;
    protected int requestControlRange;

    public BaseSchedular(){

        baseRepository = new BaseRepository();
        requestControlRange = Integer.parseInt(baseRepository.getProperty("RequestControlRange"));
        tradeListControlRange = Integer.parseInt(baseRepository.getProperty("TradeListControlRange"));

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

