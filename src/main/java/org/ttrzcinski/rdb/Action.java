package org.ttrzcinski.rdb;

import com.google.gson.Gson;

/**
 * Represents a single action entry saved in DB.
 */
public class Action {

    private Integer userId;
    private Integer gameId;
    private String action;

    private Action(int userId, int gameId, String action) {
        this.userId = userId;
        this.gameId = gameId;
        this.action = action;
    }

    /**
     * Converts given JSON request into Action item.
     *
     * @param request given JSON request
     * @return Action item, if parsed, null otherwise
     */
    public static Action fromJSON(String request) {
        return request != null && request.trim().length() != 0
                ? (Action) new Gson().fromJson(request, Action.class)
                : null;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getGameId() {
        return this.gameId;
    }

    public String getAction() {
        return this.action;
    }
}
