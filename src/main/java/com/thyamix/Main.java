package com.thyamix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final Server server = new Server();

    public static void main(String[] args) {
        server.startServer();
    }

}