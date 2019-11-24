package Classes.Database;


/**
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Tom Muck
 * @Date: Jul 17, 2018
 * @Subclass DatabaseInterface Description:
 */
//Imports

import Classes.Utilities.Debug;
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
    private boolean debug = true; // for debug mode
    private boolean logToFile = false; // for debugging, log to file or screen
    private boolean connectionStringSet = false; // do we have a connection string?

    /**
     *
     */
    public void DatabaseInterface() {
    }

    /**
     *
     */
    public void init() {
        if(!connectionStringSet) {
            loadProperties();
            buildConnectionString();
            connectionPool.setUsername(getDbUsername());
            connectionPool.setPassword(getDbPassword());
            connectionPool.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connectionPool.setUrl("jdbc:sqlserver://" + getDbIpaddress() + ":"
                    + getDbPort() + ";databaseName=" + getDbName());
            connectionPool.setInitialSize(1);
            connectionStringSet = true;
        }
    }

    /**
     *
     */
    public void loadProperties() {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input =  getClass().getClassLoader().getResourceAsStream("resources/TicketManager.properties");
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
            if(connection == null)
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
        PreparedStatement ps = null;
        try {
            if(connection == null)
                connection = connectionPool.getConnection();
                connection.setAutoCommit(false);

            ps = connection.prepareStatement(query);

            for (int i = 0; i < args.length; i++) {
                 if ("int".equalsIgnoreCase(datatypes[i])) {
                    ps.setInt(i+1, Integer.parseInt(args[i]));
                } else if ("bit".equalsIgnoreCase(datatypes[i])) {
                    ps.setBoolean(i+1, Boolean.parseBoolean(args[i]) );
                } else if ("money".equalsIgnoreCase(datatypes[i])) {
                    ps.setDouble(i+1, Double.parseDouble(args[i]) );
                } else if ("string".equalsIgnoreCase(datatypes[i])) {
                    ps.setString(i+1, args[i]);
                } else if ("date".equalsIgnoreCase(datatypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d");
                    ps.setDate(i+1, java.sql.Date.valueOf(args[i]));
                } else if ("time".equalsIgnoreCase(datatypes[i])) {
                    /*SimpleDateFormat d = new SimpleDateFormat("HH:mm:ss.S");
                    cs.setDate(i+1, java.sql.Date.valueOf(args[i]));*/
                    
                    ps.setDate(i+1, Date.valueOf(args[i]));
                } else if ("datetime".equalsIgnoreCase(datatypes[i])) {
                    java.util.Date result;                    
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d HH:mm:ss");//2018-09-18 11:09:44
                    result = d.parse (args[i]);
                    java.sql.Date sqlDate = new java.sql.Date(result.getTime());
                    
                    ps.setDate(i+1, sqlDate);
                } // other data types
            }

            ps.executeUpdate();
            connection.commit();
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
     * @param datatypes (int or string)
     */
    public ResultSet preparedStatementRs(String query, String[] args,
            String[] datatypes) {
        PreparedStatement ps = null;
        try {
            if(connection == null)
                connection = connectionPool.getConnection();


            ps = connection.prepareStatement(query);

            for (int i = 0; i < args.length; i++) {
                 if ("int".equalsIgnoreCase(datatypes[i])) {
                    ps.setInt(i+1, Integer.parseInt(args[i]));
                } else if ("bit".equalsIgnoreCase(datatypes[i])) {
                    ps.setBoolean(i+1, Boolean.parseBoolean(args[i]) );
                } else if ("money".equalsIgnoreCase(datatypes[i])) {
                    ps.setDouble(i+1, Double.parseDouble(args[i]) );
                } else if ("string".equalsIgnoreCase(datatypes[i])) {
                    ps.setString(i+1, args[i]);
                } else if ("date".equalsIgnoreCase(datatypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d");
                    ps.setDate(i+1, java.sql.Date.valueOf(args[i]));
                } else if ("time".equalsIgnoreCase(datatypes[i])) {
                    /*SimpleDateFormat d = new SimpleDateFormat("HH:mm:ss.S");
                    cs.setDate(i+1, java.sql.Date.valueOf(args[i]));*/
                    
                    ps.setDate(i+1, Date.valueOf(args[i]));
                } else if ("datetime".equalsIgnoreCase(datatypes[i])) {
                    java.util.Date result;                    
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d HH:mm:ss");//2018-09-18 11:09:44
                    result = d.parse (args[i]);
                    java.sql.Date sqlDate = new java.sql.Date(result.getTime());
                    
                    ps.setDate(i+1, sqlDate);
                } // other data types
            }

            rs = ps.executeQuery();
            
        } catch (Exception e) {
            //String module, String query, Boolean exit, String error
            e.printStackTrace();
            JDBCError("preparedStatement", query, true, e.getMessage()  + "Args:" + myToString(args) + "Datatypes:" + myToString(datatypes));
        }
        return rs;
    }
   /**
     * execute a callable statement
     *
     * @param query
     * @param args
     * @param datatypes (int or string or date, etc)
     * @return resultset
     */
    public ResultSet callableStatementRs(String query, String[] args,
            String[] datatypes) throws SQLException {
        if(connection == null)
            connection = connectionPool.getConnection();
        CallableStatement cs = null;

        try {
            if(connection == null)
                connection = connectionPool.getConnection();


            cs = connection.prepareCall(query);

            for (int i = 0; i < args.length; i++) {
                if ("int".equalsIgnoreCase(datatypes[i])) {
                    cs.setInt(i+1, Integer.parseInt(args[i]));
                } else if ("bit".equalsIgnoreCase(datatypes[i])) {
                    cs.setBoolean(i+1, Boolean.parseBoolean(args[i]) );
                } else if ("money".equalsIgnoreCase(datatypes[i])) {
                    cs.setDouble(i+1, Double.parseDouble(args[i]) );
                } else if ("string".equalsIgnoreCase(datatypes[i])) {
                    cs.setString(i+1, args[i]);
                } else if ("date".equalsIgnoreCase(datatypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d");
                    cs.setDate(i+1, java.sql.Date.valueOf(args[i]));
                } else if ("time".equalsIgnoreCase(datatypes[i])) {
                    /*SimpleDateFormat d = new SimpleDateFormat("HH:mm:ss.S");
                    cs.setDate(i+1, java.sql.Date.valueOf(args[i]));*/

                    cs.setDate(i+1, Date.valueOf(args[i]));
                } else if ("datetime".equalsIgnoreCase(datatypes[i])) {
                    java.util.Date result;
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d HH:mm:ss");//2018-09-18 11:09:44
                    result = d.parse (args[i]);
                    java.sql.Date sqlDate = new java.sql.Date(result.getTime());

                    cs.setDate(i+1, sqlDate);
                } // other data types
            }
            rs = cs.executeQuery();

            return rs;
        } catch (Exception e) {
            //String module, String query, Boolean exit, String error
            e.printStackTrace();
            JDBCError("callableStatement", query, true, e.getMessage()  + "Args:" + myToString(args) + "Datatypes:" + myToString(datatypes));
        }
        return rs;
    }

       /**
     * execute a callable statement
     *
     * @param query
     * @param args
     * @param datatypes (int or string or date, etc)
     * @return nothing
     */
    public void callableStatement(String query, String[] args,
            String[] datatypes) {
        CallableStatement cs = null;

        try {
            if(connection == null)
                connection = connectionPool.getConnection();


            cs = connection.prepareCall(query);

            for (int i = 0; i < args.length; i++) {
                if ("int".equalsIgnoreCase(datatypes[i])) {
                    cs.setInt(i+1, Integer.parseInt(args[i]));
                } else if ("bit".equalsIgnoreCase(datatypes[i])) {
                    cs.setBoolean(i+1, Boolean.parseBoolean(args[i]) );
                } else if ("money".equalsIgnoreCase(datatypes[i])) {
                    cs.setDouble(i+1, Double.parseDouble(args[i]) );
                } else if ("string".equalsIgnoreCase(datatypes[i])) {
                    cs.setString(i+1, args[i]);
                } else if ("date".equalsIgnoreCase(datatypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d");
                    cs.setDate(i+1, java.sql.Date.valueOf(args[i]));
                } else if ("time".equalsIgnoreCase(datatypes[i])) {
                    /*SimpleDateFormat d = new SimpleDateFormat("HH:mm:ss.S");
                    cs.setDate(i+1, java.sql.Date.valueOf(args[i]));*/
                    
                    cs.setDate(i+1, Date.valueOf(args[i]));
                } else if ("datetime".equalsIgnoreCase(datatypes[i])) {
                    java.util.Date result;                    
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d HH:mm:ss");//2018-09-18 11:09:44
                    result = d.parse (args[i]);
                    java.sql.Date sqlDate = new java.sql.Date(result.getTime());
                    
                    cs.setDate(i+1, sqlDate);
                } // other data types
            }
            cs.execute();
            return;
        } catch (Exception e) {
            //String module, String query, Boolean exit, String error
            e.printStackTrace();
            JDBCError("callableStatement", query, true, e.getMessage()  + "Args:" + myToString(args) + "Datatypes:" + myToString(datatypes));
        }
        return;
    }
   /**
     * execute a callable statement that returns an integer
     * for getting counts, etc
     * @param query
     * @param args
     * @param datatypes (int or string or date, etc)
     */
    public int callableStatementReturnInt(String query, String[] args,
            String[] datatypes) {
        CallableStatement cs = null;
        int returnValue = 0;
        try {
            if(connection == null)
                connection = connectionPool.getConnection();

            cs = connection.prepareCall(query);

            for (int i = 0; i < args.length; i++) {
                if ("int".equalsIgnoreCase(datatypes[i])) {
                    cs.setInt(i+1, Integer.parseInt(args[i]));
                } else if ("bit".equalsIgnoreCase(datatypes[i])) {
                    cs.setBoolean(i+1, Boolean.parseBoolean(args[i]) );
                } else if ("money".equalsIgnoreCase(datatypes[i])) {
                    cs.setDouble(i+1, Double.parseDouble(args[i]) );
                } else if ("string".equalsIgnoreCase(datatypes[i])) {
                    cs.setString(i+1, args[i]);
                } else if ("date".equalsIgnoreCase(datatypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d");
                    cs.setDate(i+1, java.sql.Date.valueOf(args[i]));
                } else if ("time".equalsIgnoreCase(datatypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("HH:mm:ss");
                    cs.setDate(i+1, java.sql.Date.valueOf(args[i]));
                } else if ("datetime".equalsIgnoreCase(datatypes[i])) {
                    java.util.Date result;
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d HH:mm:ss");//2018-09-18 11:09:44
                    result = d.parse (args[i]);
                    java.sql.Date sqlDate = new java.sql.Date(result.getTime());

                    cs.setDate(i+1, sqlDate);
                } // other data types
            }
            cs.registerOutParameter(args.length+1, java.sql.Types.INTEGER);
            cs.execute();
            returnValue = cs.getInt(args.length+1);
            close();
        } catch (Exception e) {
            //String module, String query, Boolean exit, String error
            e.printStackTrace();
            JDBCError("callableStatement", query, true, e.getMessage()  + "Args:" + myToString(args) + "Datatypes:" + myToString(datatypes));
        }
        return returnValue;
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
 /*       if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                JDBCError("close", "", true, e.getMessage());
            }
        }*/
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
                //System.out.println(rs.getString("factsid") + " : " + rs.getString("siteadd"));
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





} //End Subclass DatabaseInterface