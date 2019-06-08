package sample.database;

import sample.model.User;

import java.sql.*;

public class DatabaseHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;

    }

    public void createUser(User newUser) throws SQLException, ClassNotFoundException {

        String insert = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME
                + "," + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" + "VALUES(?,?,?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1, newUser.getFirstName());
        preparedStatement.setString(2, newUser.getLastName());
        preparedStatement.setString(3, newUser.getUserName());
        preparedStatement.setString(4, newUser.getPassword());

        preparedStatement.executeUpdate();

    }

    public ResultSet getUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        if (!user.getUserName().equals("") || (!user.getPassword().equals(""))){
            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_USERNAME + "=?"
                    + " AND " + Const.USERS_PASSWORD + "=?";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());

            resultSet = preparedStatement.executeQuery();
        }

        return resultSet;
    }
}
