package Classes.Database;


/**
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Tom Muck
 * @Date: Jul 17, 2018
 * @Subclass DatabaseInterface Description:
 */
//Imports

import Classes.Utilities.Debug;
import static Classes.Utilities.Debug.logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.*;

import java.text.SimpleDateFormat;
import org.apache.commons.dbcp2.BasicDataSource;
//Begin Subclass DatabaseInterface

/**
 *
 * @author Tom.Muck
 */
public class DatabaseInterface implements Debug {

    private String connectionString = "";

    private String dbIpaddress;
    private String dbUsername;
    private String dbPassword;
    private String dbName;
    private String dbPort;
    private BasicDataSource connectionPool = new BasicDataSource();
    private Connection connection;
    private Statement stmt;
    private ResultSet rs;
    private boolean debug = false; // for debug mode
    private boolean logToFile = true; // for debugging, log to file or screen
    
    /**
     *
     */
    public void DatabaseInterface() {
    }

    /**
     *
     */
    public void init() {
        loadProperties();
        buildConnectionString();
        connectionPool.setUsername(getDbUsername());
        connectionPool.setPassword(getDbPassword());
        connectionPool.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connectionPool.setUrl("jdbc:sqlserver://" + getDbIpaddress() + ":"
                + getDbPort() + ";databaseName=" + getDbName());
        connectionPool.setInitialSize(1);
        //System.out.println(getDbUsername() + getDbPassword());
    }

    /**
     *
     */
    public void loadProperties() {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input =  getClass().getClassLoader().getResourceAsStream("TicketManager.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            setDbIpaddress(prop.getProperty("dbIpaddress"));
            setDbUsername(prop.getProperty("dbUsername"));
            setDbPassword(prop.getProperty("dbPassword"));
            setDbName(prop.getProperty("dbName"));
            setDbPort(prop.getProperty("dbPort"));
            setDebug(Boolean.getBoolean(prop.getProperty("debug")));
            setLogToFile(Boolean.getBoolean(prop.getProperty("logtofile")));
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // might not use this!
    private void buildConnectionString() {
        //loadProperties();
        connectionString = "jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s";
        connectionString = String.format(connectionString, dbIpaddress, dbPort,
                dbName, dbUsername, dbPassword);

    }

    /**
     * retrieve RS given a query
     * @param SQL
     * @return 
     */
    public ResultSet retrieveRS(String SQL) {
        if(getDebug()) System.out.println(SQL);
        try {// Declare the JDBC objects.
            if(getDebug()) System.out.println(SQL);
            connection = connectionPool.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);
            return rs;
        } // Handle any errors that may have occurred.
        catch (Exception e) {
            //e.printStackTrace();
            //String module, String query, Boolean exit, String error
            JDBCError("retrieveRS", SQL, true, e.getMessage());
        } //finally {
        //close();
        //}
        return rs;
    }

    /**
     * execute a prepared statement
     *
     * @param query
     * @param args
     * @param datatypes (int or string)
     */
    public void preparedStatement(String query, String[] args,
            String[] datatypes) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(query);

            for (int i = 0; i < args.length; i++) {
                if ("int".equalsIgnoreCase(datatypes[i])) {
                    ps.setInt(i+1, Integer.parseInt(args[i]));
                } else if ("string".equalsIgnoreCase(datatypes[i])) {
                    ps.setString(i+1, args[i]);
                } else if ("date".equalsIgnoreCase(datatypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d");
                    ps.setDate(i+1, java.sql.Date.valueOf(args[i]));
                } else if ("datetime".equalsIgnoreCase(datatypes[i])) {
                    
                   
                    java.util.Date result;                    
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d HH:mm:ss");//2018-09-18 11:09:44
                    result = d.parse (args[i]);
                    java.sql.Date sqlDate = new java.sql.Date(result.getTime());
                    
                    ps.setDate(i+1, sqlDate);
                } // other data types
            }

            ps.executeUpdate();
            con.commit();
        } catch (Exception e) {
            //String module, String query, Boolean exit, String error
            e.printStackTrace();
            JDBCError("preparedStatement", query, true, e.getMessage()  + "Args:" + myToString(args) + "Datatypes:" + myToString(datatypes));
        }

    }
   /**
     * execute a prepared statement
     *
     * @param query
     * @param args
     * @param datatypes (int or string or date, etc)
     */
    public ResultSet callableStatement(String query, String[] args,
            String[] datatypes) {
        Connection con = null;
        CallableStatement ps = null;

        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareCall(query);

            for (int i = 0; i < args.length; i++) {
                if ("int".equalsIgnoreCase(datatypes[i])) {
                    ps.setInt(i+1, Integer.parseInt(args[i]));
                } else if ("string".equalsIgnoreCase(datatypes[i])) {
                    ps.setString(i+1, args[i]);
                } else if ("date".equalsIgnoreCase(datatypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d");
                    ps.setDate(i+1, java.sql.Date.valueOf(args[i]));
                } else if ("datetime".equalsIgnoreCase(datatypes[i])) {
                    
                   
                    java.util.Date result;                    
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d HH:mm:ss");//2018-09-18 11:09:44
                    result = d.parse (args[i]);
                    java.sql.Date sqlDate = new java.sql.Date(result.getTime());
                    
                    ps.setDate(i+1, sqlDate);
                } // other data types
            }
            rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            //String module, String query, Boolean exit, String error
            e.printStackTrace();
            JDBCError("callableStatement", query, true, e.getMessage()  + "Args:" + myToString(args) + "Datatypes:" + myToString(datatypes));
        }
        return rs;
    }



    /**
     *
     */
    public void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                JDBCError("close", "", true, e.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                JDBCError("close", "", true, e.getMessage());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                JDBCError("close", "", true, e.getMessage());
            }
        }
    }

    /**
     * Sample recordset display method, not used in app
     *
     * @param title
     * @param rs
     */
    private void displayRow(String title, ResultSet rs) {
        try {
            System.out.println(title);
            while (rs.next()) {
                System.out.println(rs.getString("factsid") + " : " + rs.getString("siteadd"));
            }
        } catch (Exception e) {
            JDBCError("displayRow", "", true, e.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public String getConnectionString() {
        return connectionString;
    }

    /**
     *
     * @param connectionString
     */
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     *
     * @return
     */
    public String getDbIpaddress() {
        return dbIpaddress;
    }

    /**
     *
     * @param dbIpaddress
     */
    public void setDbIpaddress(String dbIpaddress) {
        this.dbIpaddress = dbIpaddress;
    }

    /**
     *
     * @return
     */
    public String getDbUsername() {
        return dbUsername;
    }

    /**
     *
     * @param dbUsername
     */
    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    /**
     *
     * @return
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     *
     * @param dbPassword
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     *
     * @return
     */
    public String getDbName() {
        return dbName;
    }

    /**
     *
     * @param dbName
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     *
     * @return
     */
    public String getDbPort() {
        return dbPort;
    }

    /**
     *
     * @param dbPort
     */
    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    /**
     *
     * @return
     */
    public BasicDataSource getConnectionPool() {
        return connectionPool;
    }

    /**
     *
     * @param connectionPool
     */
    public void setConnectionPool(BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     *
     * @return
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     *
     * @param connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     *
     * @return
     */
    public Statement getStmt() {
        return stmt;
    }

    /**
     *
     * @param stmt
     */
    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    /**
     *
     * @return
     */
    public ResultSet getRs() {
        return rs;
    }

    /**
     *
     * @param rs
     */
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    /**
     *
     * @param module
     * @param query
     * @param exit
     * @param error
     */
    public void JDBCError(String module, String query, Boolean exit, String error) {

        String errorMessage = "Development::Fatal JDBC error in :" + module
                + " error:" + error;
        if (query.length() == 0) {
            query = "No query found";
        } 
        logDebug(errorMessage + "\n" + query, "error");

        if (exit) { // Non recoverable error
            /* TODO: implement methods below
            notifyHelpDesk(errorMessage, " with $Error");
            publishError("My error message is: $Error");
            publishError("My SQl is: $Q");
            publishError(" An internal process error has occurred.\n The FACTS Technical Help desk has been notified", 'Abort'
            );
            // Set the log flag to Errors found
            updateLog("X", $glogID);
             */
            close();
            System.exit(1);
        }

    } // end JDBCError

  

    /**
     * 
     * @param message
     * @param level 
     */

    public void logDebug(String message, String level) {
        this.logDebug(message, level, true);
    }
    
    /**
     * 
     * @param message 
     */
    @Override
    public void logDebug(String message) {
        this.logDebug(message, "info");
    }
            /**
     * 
     * @param message
     * @param level
     * @param robust 
     */
    @Override
    public void logDebug(String message, String level, boolean robust) {
        String callingMethod = Thread.currentThread().getStackTrace()[3].getMethodName();
        String lineNumber = Integer.toString(Thread.currentThread().getStackTrace()[3].getLineNumber());
        if (level.length() == 0) {
            level = "info";
        }
        
        if(robust) {
            message = message  + " From:" + callingMethod + " linenumber:" + lineNumber; 
        }
        if (getLogToFile()) {
            // log to file
            if("info".equalsIgnoreCase(level)) {
                logger.info(message);
            }else{
                // level = error
                //logger.error(message);
            }
        } else { // log debugging messages to output
            System.out.println(message);
        }
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
     public boolean getLogToFile() {
        return logToFile;
    }
    /**
     * 
     * @param ltf
     */
    @Override
    public void setLogToFile(boolean ltf) {
        logToFile = ltf;
    }

    /**
     *
     * @param debug
     */
    @Override
    public void setDebug(boolean debug) {
        
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean getDebug() {
        return debug;
    }

    /**
     *
     * @param theArray
     * @return
     */
    @Override
     public String myToString(String[] theArray) {
        String result = "[";
        for (int i = 0; i < theArray.length; i++) {
           if (i > 0) {
              result = result + ",";
           }
           String item = theArray[i];
           result = result + item;
        }
        result = result + "]";
        return result;
     }




} //End Subclass DatabaseInterface