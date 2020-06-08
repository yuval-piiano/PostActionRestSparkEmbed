package org.ttrzcinski.rdb;

import org.ttrzcinski.utils.DBConn;
import spark.Request;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Acts as a single point of access to Actions details.
 */
public class ActionsDAO {

    /**
     * Kept the only instance of Data-Access Object.
     */
    private static ActionsDAO INSTANCE;

    /**
     * Hidden constructor.
     */
    private ActionsDAO() {}

    /**
     * Returns instance of Actions Data-Access Object.
     *
     * @return Actions DAO
     */
    public static ActionsDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActionsDAO();
        }
        return INSTANCE;
    }

    /**
     * Inserts given entry to database.
     *
     * @param request passed request
     * @return response message
     */
    public String saveAction(Request request) {
        return new StringBuilder().append("Saved action result: ")
                .append(insertAction(request) ? "Saved" : "Denied")
                .append("\r\n").toString();
    }

    /**
     * Checks, if request is valid to further process it.
     *
     * Example valid request: "{"userId": 111, "gameId": 222, "action": "joined-the-game"}"
     *
     * @param request given request
     * @return true, if is, false otherwise
     */
    public static boolean checkRequest(Request request) {
        return request != null && request.body().contains("userId")
                && request.body().contains("gameId")
                && request.body().contains("action");

    }

    /**
     * Converts given request to Action item.
     *
     * @param request given Spark's request
     * @return action, if parsed, null otherwise
     */
    private Action parseAction(Request request) {
        return checkRequest(request)
                ? Action.fromJSON(request.body())
                : null;
    }

    /**
     * Adds action to DB.
     *
     * @param request given request to parse
     * @return true means added, false otherwise
     */
    private boolean insertAction(Request request) {
        Action action = parseAction(request);
        return action != null && this.executeInsert(action);
    }

    /**
     * Calls an insert to DB.
     *
     * @param action Passed action to add to DB
     * @return true, if added, false otherwise
     */
    private boolean executeInsert(Action action) {
        // Make a DB connection
        DBConn dbc = DBConn.getInstance();
        if (!dbc.isConnected()) {
            dbc.connect();
        }

        String prepStmt_str = "INSERT INTO actions(user_id, game_id, action_name) VALUES (?,?,?)";

        int result;
        PreparedStatement prepStmt;
        try {
            prepStmt = dbc.getConnection().prepareStatement(prepStmt_str);
            prepStmt.setInt(1, action.getUserId());
            prepStmt.setInt(2, action.getGameId());
            prepStmt.setString(3, action.getAction());
            result = prepStmt.executeUpdate();
        } catch (SQLException sqle) {
            System.err.println("Couldn't add new entry to DB.");
            sqle.printStackTrace();
            result = 0;
        } finally {
            dbc.disconnect();
        }
        return result > 0;
    }
}
