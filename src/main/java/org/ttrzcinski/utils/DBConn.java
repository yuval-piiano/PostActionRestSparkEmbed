package org.ttrzcinski.utils;

import org.ttrzcinski.rdb.Action;

import java.sql.*;

public class DBConn {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONN_STR = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2345632";
    private static final String DB_USER = "sql2345632";
    private static final String DB_PASS = "fP7*yP1!";

    /**
     * Kept instance of this class.
     */
    private static DBConn INSTANCE;

    /**
     * Kept connection to DB.
     */
    private Connection conn;

    /**
     * Hidden constructor.
     */
    private DBConn() {}

    /**
     * Returns the only instance of this connector.
     *
     * @return connector instance
     */
    public static DBConn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DBConn();
        }
        return INSTANCE;
    }

    /**
     * Calls DB to make a connection.
     *
     * @return true, if connected, false otherwise
     */
    private boolean connect() {
        boolean result = false;
        if (conn != null) {
            System.err.println("Connection is already set.");
            return result;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(DB_CONN_STR, DB_USER, DB_PASS);
            result = true;
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Couldn't find MySQL JDBC driver.");
            cnfe.printStackTrace();//CLASS FOR NAME
            result = false;
        } catch (SQLException sqle) {
            System.err.println("Couldn't get the DB connection with given credentails.");
            sqle.printStackTrace();//GET CONN
            result = false;
        }

        return result;
    }

    /**
     * Disconnection kept connectino to DB.
     *
     * @return true, if disconnected, false otherwise
     */
    private boolean disconnect() {
        boolean result = false;
        if (conn != null) {
            try {
                conn.close();
                result = true;
            } catch (SQLException sqle) {
                System.err.println("Couldn't close connection.");
                sqle.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Checks, if DB connection is ON.
     *
     * @return true, if is ON, false otherwise
     */
    private boolean isConnected() {
        boolean result = false;
        try {
            result = conn != null && !conn.isClosed();
        } catch (SQLException sqle) {
            result = false;
            sqle.printStackTrace();
        }
        return result;
    }

    /**
     * Calls an insert to DB.
     *
     * @param action Passed action to add to DB
     * @return true, if added, false otherwise
     */
    public boolean executeInsert(Action action) {
        // Make a DB connection
        if (!this.isConnected()) {
            this.connect();
        }

        String prepStmt_str = "INSERT INTO actions(user_id, game_id, action_name) VALUES (?,?,?)";

        int result = -1;
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(prepStmt_str);
            prepStmt.setInt(1, action.getUserId());
            prepStmt.setInt(2, action.getGameId());
            prepStmt.setString(3, action.getAction());
            result = prepStmt.executeUpdate();
        } catch (SQLException sqle) {
            System.err.println("Couldn't add new entry to DB.");
            sqle.printStackTrace();
            result = 0;
        } finally {
            this.disconnect();
        }
        return result > 0;
    }
}
