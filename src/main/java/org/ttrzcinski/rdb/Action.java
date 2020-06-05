package org.ttrzcinski.rdb;

import com.google.gson.Gson;

/**
 * Represents a single action entry saved in DB.
 */
public class Action {

    //"{"userId": 111, "gameId": 222, "action": "joined-the-game"}"
    private Integer userId;
    private Integer gameId;
    private String action;

    private Action(int userId, int gameId, String action) {
        this.userId = userId;
        this.gameId = gameId;
        this.action = action;
    }

    public static Action fromJSON(String request) {
        if (request == null || request.trim().length() == 0) {
            return null;
        }

        Action parsed = new Gson().fromJson(request, Action.class);
        return parsed;
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
