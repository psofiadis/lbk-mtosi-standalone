/*
 *  Copyright 2016 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: ext_psofiadis
 *
 *  $Id: $ 
 */

package init;

import com.adva.mtosi.server.config.WebServer;

public class Main {
    public static void main(String[] args) throws Exception{
        new WebServer(9090).start();
    }
}
