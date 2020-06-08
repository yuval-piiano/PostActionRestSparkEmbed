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
    public boolean connect() {
        if (conn != null) {
            System.err.println("Connection is already set.");
            return false;
        }

        boolean result = true;
        try {
            Class.forName(JDBC_DRIVER);
            this.conn = DriverManager.getConnection(DB_CONN_STR, DB_USER, DB_PASS);
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
    public boolean disconnect() {
        boolean result = false;
        if (this.conn != null) {
            try {
                this.conn.close();
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
    public boolean isConnected() {
        boolean result;
        try {
            result = this.conn != null && !this.conn.isClosed();
        } catch (SQLException sqle) {
            result = false;
            sqle.printStackTrace();
        }
        return result;
    }

    public Connection getConnection() {
        return this.conn;
    }
}
