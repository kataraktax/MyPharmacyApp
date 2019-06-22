package sample.database;

import java.time.LocalDate;

public class Const {

    // User Table Const
    public static final String USERS_TABLE = "users";
    public static final String USERS_ID = "userid";
    public static final String USERS_FIRSTNAME = "firstname";
    public static final String USERS_LASTNAME = "lastname";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";

    // Medicine Table Const
    public static final String MEDICINES_TABLE = "medicines";
    public static final String MEDICINES_ID = "medicineid";
    public static final String MEDICINES_NAME = "name";
    public static final String MEDICINES_DESCRIPTION = "description";
    public static final String MEDICINES_EXPIREDATE = "expiredate";
    public static final String MEDICINES_TREATMENTID = "treatmentid";

    // Treatment Table Const
    public static final String TREATMENTS_TABLE = "treatments";
    public static final String TREATMENTS_ID = "treatmentid";
    public static final String TREATMENTS_USERID = "userid";
    public static final String TREATMENTS_NAME = "treatmentname";
    public static final String TREATMENTS_STARTDATE = "startdate";
    public static final String TREATMENTS_DURATION = "duration";
}
