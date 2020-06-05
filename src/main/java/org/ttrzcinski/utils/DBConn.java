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

    private boolean connect() {
        if (conn != null) {
            System.err.println("Connection is already set.");
            return false;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(DB_CONN_STR, DB_USER, DB_PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();//CLASS FOR NAME
        } catch (SQLException sqle) {
            sqle.printStackTrace();//GET CONN
        }

        return true;
    }

    private boolean disconnect() {
        if (conn != null) {
            boolean result = true;
            try {
                conn.close();
            } catch (SQLException throwables) {
                result = false;
                throwables.printStackTrace();
            }
            return result;
        }
        return false;
    }

    private boolean isConnected() {
        try {
            if (conn != null && !conn.isClosed()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean executeInsert(Action action) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            this.disconnect();
        }
        return result > 0;
    }
}
