/*
Copyright 2016 Janus Friis Nielsen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.glassfish.jersey.examples.helloworld;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.examples.helloworld.resources.HelloWorldResource;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the example entry point, where Jersey application for the example
 * gets populated and published using the Grizzly 2 HTTP container.
 *
 * @author Janus Friis Nielsen (jfn at fagidiot.dk)
 */
public class App {

    private static final URI BASE_URI = URI.create("http://localhost:8080/base/");
    /**
     * "Hello World" root resource path.
     */
    public static final String ROOT_PATH = "helloworld";

    /**
     * Main application entry point.
     *
     * @param args application arguments.
     */
    public static void main(String[] args) {
        try {
            System.out.println("\"Hello World\" Jersey Example App");

            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, create(), false);
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.shutdownNow();
                }
            }));
            server.start();

            System.out.println(
                    String.format("Application started.%n"
                                    + "Try out %s%s%n"
                                    + "Stop the application using CTRL+C",
                            BASE_URI, ROOT_PATH));

            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Create example application resource configuration.
     *
     * @return initialized resource configuration of the example application.
     */
    public static ResourceConfig create() {
        final ResourceConfig resourceConfig = new ResourceConfig(HelloWorldResource.class);

        return resourceConfig;
    }
}