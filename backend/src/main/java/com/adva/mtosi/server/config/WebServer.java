/*
 *  Copyright 2016 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: ext_psofiadis
 *
 *  $Id: $ 
 */

package com.adva.mtosi.server.config;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

public class WebServer {

    private static final String DEFAULT_PROFILE = "dev";
    private static final String CONTEXT_PATH = "/";
    private static final String MAPPING_URL = "/*";
    private static ServletContextHandler context;

    private Server server;
    private int port;
    private String bindInterface;

    public WebServer(int aPort) {
        this(aPort, null);
    }

    public WebServer(int aPort, String aBindInterface) {
        port = aPort;
        bindInterface = aBindInterface;
    }

    public void start() throws Exception {
        server = new Server(port);
        context = getServletContextHandler(getContext());
        server.setHandler(context);
        server.start();
        server.join();
    }

    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));
        contextHandler.setResourceBase(new ClassPathResource("./").getURI().toString());
        return contextHandler;
    }

    private static WebApplicationContext getContext() {
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("context-provider.xml");
        context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
        return context;
    }
}
