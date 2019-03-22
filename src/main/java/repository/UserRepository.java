package repository;

import bean.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserRepository extends BaseRepository {

    public User getUserInfo(int userId){
        User user = new User();
        Statement statement = null;
        try {
            statement = this.createStatement();

            ResultSet rs = statement.executeQuery("select Name, Surname, Age, URL from User where ID = " + userId);
            if (rs.next()){
                user.setName(rs.getString(1));
                user.setSurname(rs.getString(2));
                user.setAge(rs.getInt(3));
                user.setUrl(rs.getString(4));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.cleanResources(statement);
        }

        return user;
    }

}
