package edu.uw.info314.xmlrpc.server;

import java.util.*;
import java.util.logging.*;

import static spark.Spark.*;

class Call {
    public String name;
    public List<Object> args = new ArrayList<Object>();
}

public class App {
    public static final Logger LOG = Logger.getLogger(App.class.getCanonicalName());
    private static final int PORT = 8080;


    public static void main(String[] args) {
        portNumber(PORT);
        LOG.info("Starting up on port 4567");
    }


    
}

        // This is the mapping for POST requests to "/RPC";

        // this is where you will want to handle incoming XML-RPC requests
        post("/RPC", (request, response) -> { response.status(500); return "TBD"; });

        // Each of the verbs has a similar format: get() for GET,
        // put() for PUT, delete() for DELETE. There's also an exception()
        // for dealing with exceptions thrown from handlers.
        // All of this is documented on the SparkJava website (https://sparkjava.com/).