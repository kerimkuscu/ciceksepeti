package repository;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class BaseRepository {

    protected String resourceName = "java:comp/env/jdbc/cryptonotifier";
    protected Context ctx;
    protected DataSource ds;

    public BaseRepository(){
        try {
            this.ctx = new InitialContext();
            this.ds = (DataSource) ctx.lookup(this.resourceName);
        }catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates statement
     */
    public Statement createStatement() {
        Statement statement = null;

        try {
            statement = ds.getConnection().createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statement;
    }

    /**
     * Frees Statement and Connection variables.
     */
    public void cleanResources(Statement statement) {
        try {

            Connection connection = statement.getConnection();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
