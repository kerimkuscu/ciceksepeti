package scheduled;

import repository.BaseRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

public class BaseSchedular implements ServletContextListener {

    protected static final int MINUTEINMILLISECONDS = 60000;
    protected static final int DAYINMILLISECONDS    = 86400000;

    protected BaseRepository baseRepository;

    public BaseSchedular(){

        baseRepository = new BaseRepository();

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

