package org.ttrzcinski;

import static spark.Spark.post;

import org.ttrzcinski.rdb.ActionsDAO;

/**
 * Action's REST Endpoint.
 */
public class ActionEndpoint {

    /**
     * Main entry point to the endpoint.
     *
     * @param args entered arguments
     */
    public static void main(String[] args) {
        post("/actions", (req_01, resp_01) -> ActionsDAO.getInstance().saveAction(req_01));
    }
}
