package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    private static String dbUrl;
    private static String login;
    private static String password;
    //private static String driver;

    /** The object containing the connexion */
    private static Connection connection;

    /**
     * Constructor
     *
     * @throws DataBaseException
     */

    private DBConnection() throws  DataBaseException {

        try{
            // Load the configuration
            Properties dbProperties = DbPropertiesLoader.loadPoperties("conf.properties");
            dbUrl = dbProperties.getProperty("db.url");
            login = dbProperties.getProperty("db.login");
            password = dbProperties.getProperty("db.password");
            //driver = dbProperties.getProperty("db.driver");

            // Load the driver of the database
           // Class.forName(driver);

            // create a connexion to database
            connection = DriverManager.getConnection(dbUrl, login, password);

        }catch (Exception ex){
            //log the error

            //raise the exception stack
            throw new DataBaseException(ex);
        }

    }

    /**
     * returns the unique instance of connection
     *
     * @return connection
     * @throws DataBaseException
     */
    public static Connection getInstance() throws DataBaseException {

        if (connection == null) {

            new DBConnection();

        }

        return connection;
    }
}
