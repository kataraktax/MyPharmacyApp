package sample.database;

import sample.model.Medicine;
import sample.model.Treatment;
import sample.model.User;

import java.lang.reflect.ParameterizedType;
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

        String query = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME
                + "," + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" + "VALUES(?,?,?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
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

    public ResultSet getUserById(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        if (id > 0){
            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_ID + "=?";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

        }

        return resultSet;
    }

    public void updateUser(User user, int id) throws SQLException, ClassNotFoundException {

        if (id >= 1){
            String query = "UPDATE " + Const.USERS_TABLE + " SET " + Const.USERS_FIRSTNAME + " =? ," + Const.USERS_LASTNAME
                    + " =? ," + Const.USERS_USERNAME + " =? ," +  Const.USERS_PASSWORD + " =? " + "WHERE "
                    + Const.USERS_ID + " =?";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5,id);

            preparedStatement.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException, ClassNotFoundException {
        if (id >= 1){
            String query = "DELETE FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_ID + "=?";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public void addMedicine(Medicine medicine) throws SQLException, ClassNotFoundException {

        String query = "INSERT INTO " + Const.MEDICINES_TABLE + "(" + Const.MEDICINES_NAME + ","
                + Const.MEDICINES_DESCRIPTION + "," + Const.MEDICINES_EXPIREDATE + ")"
                + "VALUES(?,?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1, medicine.getName());
        preparedStatement.setString(2, medicine.getDescription());
        preparedStatement.setDate(3, medicine.getExpireDate());

        preparedStatement.executeUpdate();
    }

    public ResultSet getMedicines() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = null;

        String query = "SELECT * FROM " + Const.MEDICINES_TABLE;

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        return resultSet;

    }

    public void updateMedicine(Medicine medicine, int id) throws SQLException, ClassNotFoundException {
        String query = "UPDATE " + Const.MEDICINES_TABLE + " SET " + Const.MEDICINES_NAME + " =? ,"
                + Const.MEDICINES_DESCRIPTION + " =? ," + Const.MEDICINES_EXPIREDATE + " =? " + "WHERE "
                + Const.MEDICINES_ID + " =?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1, medicine.getName());
        preparedStatement.setString(2, medicine.getDescription());
        preparedStatement.setDate(3, medicine.getExpireDate());
        preparedStatement.setInt(4, id);

        preparedStatement.executeUpdate();
    }

    public void deleteMedicine(int id) throws SQLException, ClassNotFoundException {
        if (id >= 0){
            String query = "DELETE FROM " + Const.MEDICINES_TABLE + " WHERE " + Const.MEDICINES_ID + "=?";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        }
    }

    public void createTreatment(Treatment treatment, int userId) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO " + Const.TREATMENTS_TABLE + "(" + Const.TREATMENTS_USERID + ","
                + Const.TREATMENTS_NAME + "," + Const.TREATMENTS_STARTDATE + ","
                + Const.TREATMENTS_DURATION + ")" + "VALUES(?,?,?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, treatment.getName());
        preparedStatement.setDate(3, treatment.getStartDate());
        preparedStatement.setInt(4, treatment.getDuration());

        preparedStatement.executeUpdate();
    }

    public ResultSet getTreatmentsByUserId(int userId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        if (userId > 0) {
            String query = "SELECT " + Const.TREATMENTS_NAME + " FROM " + Const.TREATMENTS_TABLE
                    + " WHERE " + Const.TREATMENTS_USERID + "=?";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
        }

        return resultSet;
    }

    public ResultSet getTreatmentByName(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        if (!name.equals("")){
            String query = "SELECT " + Const.TREATMENTS_ID + "," + Const.TREATMENTS_STARTDATE
                    + "," + Const.TREATMENTS_DURATION + " FROM " + Const.TREATMENTS_TABLE + " WHERE "
                    + Const.TREATMENTS_NAME + "=?";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
        }

        return resultSet;
    }
}
