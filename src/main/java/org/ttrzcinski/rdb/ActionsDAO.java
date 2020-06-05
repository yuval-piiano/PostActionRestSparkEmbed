package org.ttrzcinski.rdb;

import org.ttrzcinski.utils.DBConn;
import spark.Request;

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
    private ActionsDAO() {
        System.out.println("Connecting to DB.");
    }

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

    private Action parseAction(Request request) {
        if (!checkRequest(request)) {
            return null;
        }

        Action action = Action.fromJSON(request.body());

        return action;
    }

    private boolean insertAction(Request request) {
        Action action = parseAction(request);
        return action != null && DBConn.getInstance().executeInsert(action);
    }
}
