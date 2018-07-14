package com.example;

import unchained.Configuration;
import unchained.http.HttpApplication;
import unchained.http.HttpRoute;

import static unchained.Unchained.*;

public final class HelloWorld {



    public static void main(String[] args) {

        // Step 1: define

        Configuration cfg = emptyConfiguration();
        HttpApplication app = newHttpApplication(cfg);

        app.get("/hello", (request, response) -> {
            // Say hello
        });
        app.get("/bye", (input, output) -> null);

        HttpRoute secure = newHttpRoute();

        secure.use((request, response) -> {
            // Check security
        });
        secure.get("/hello", (request) -> {
            // Say secured hello
            return "Hello, World";
        });

        app.use("/secure", secure);

    }

}
