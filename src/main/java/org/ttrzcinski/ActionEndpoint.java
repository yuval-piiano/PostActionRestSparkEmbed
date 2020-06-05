package org.ttrzcinski;

import static spark.Spark.post;

import org.ttrzcinski.rdb.ActionsDAO;

public class ActionEndpoint {

    public static void main(String[] args) {
        post("/actions", (req_01, resp_01) -> ActionsDAO.getInstance().saveAction(req_01));
    }
}
