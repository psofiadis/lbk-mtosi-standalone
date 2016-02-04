/*
 *  Copyright 2016 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: ext_psofiadis
 *
 *  $Id: $ 
 */

package com.adva.mtosi.notify;

import java.applet.Applet;
import java.awt.*;

public class NotificationApplet extends Applet {
    @Override
    public void paint(Graphics g) {
        g.drawString("Hello applet!", 50, 25);
    }
}
