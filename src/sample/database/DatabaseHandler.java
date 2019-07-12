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
                + Const.MEDICINES_DESCRIPTION + "," + Const.MEDICINES_EXPIREDATE + "," + Const.MEDICINES_HEADACHE
                + "," + Const.MEDICINES_FEVER + "," + Const.MEDICINES_COLD + "," + Const.MEDICINES_COUGH +")"
                + "VALUES(?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1, medicine.getName());
        preparedStatement.setString(2, medicine.getDescription());
        preparedStatement.setDate(3, medicine.getExpireDate());
        preparedStatement.setInt(4, medicine.getHeadache());
        preparedStatement.setInt(5, medicine.getFever());
        preparedStatement.setInt(6, medicine.getCold());
        preparedStatement.setInt(7, medicine.getCough());

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
                + Const.MEDICINES_DESCRIPTION + " =? ," + Const.MEDICINES_EXPIREDATE + " =? ,"
                + Const.MEDICINES_HEADACHE + " =? ," + Const.MEDICINES_FEVER + " =? ," + Const.MEDICINES_COLD + " =? ,"
                + Const.MEDICINES_COUGH + " =? " + " WHERE "
                + Const.MEDICINES_ID + " =?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1, medicine.getName());
        preparedStatement.setString(2, medicine.getDescription());
        preparedStatement.setDate(3, medicine.getExpireDate());
        preparedStatement.setInt(4, medicine.getHeadache());
        preparedStatement.setInt(5,medicine.getFever());
        preparedStatement.setInt(6, medicine.getCold());
        preparedStatement.setInt(7, medicine.getCough());
        preparedStatement.setInt(8, id);

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
                + Const.TREATMENTS_DURATION + "," + Const.TREATMENTS_HEADACHE + "," + Const.TREATMENTS_FEVER
                + "," + Const.TREATMENTS_COLD + "," + Const.TREATMENTS_COUGH  + ")" + "VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, treatment.getName());
        preparedStatement.setDate(3, treatment.getStartDate());
        preparedStatement.setInt(4, treatment.getDuration());
        preparedStatement.setInt(5, treatment.getHeadache());
        preparedStatement.setInt(6, treatment.getFever());
        preparedStatement.setInt(7, treatment.getCold());
        preparedStatement.setInt(8,treatment.getCough());

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
            String query = "SELECT * FROM " + Const.TREATMENTS_TABLE + " WHERE "
                    + Const.TREATMENTS_NAME + "=?";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
        }

        return resultSet;
    }

    public void updateTreatment(Treatment treatment, int id) throws SQLException, ClassNotFoundException {
        String query = "UPDATE " + Const.TREATMENTS_TABLE + " SET " + Const.TREATMENTS_NAME + " =? ,"
                + Const.TREATMENTS_DURATION + " =? ," + Const.TREATMENTS_HEADACHE + " =? ,"
                + Const.TREATMENTS_FEVER + " =? ," + Const.TREATMENTS_COLD + " =? ,"
                + Const.TREATMENTS_COUGH + " =? " + " WHERE " + Const.TREATMENTS_ID + " =?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1, treatment.getName());
        preparedStatement.setInt(2, treatment.getDuration());
        preparedStatement.setInt(3, treatment.getHeadache());
        preparedStatement.setInt(4, treatment.getFever());
        preparedStatement.setInt(5, treatment.getCold());
        preparedStatement.setInt(6, treatment.getCough());
        preparedStatement.setInt(7, id);

        preparedStatement.executeUpdate();
    }
}
