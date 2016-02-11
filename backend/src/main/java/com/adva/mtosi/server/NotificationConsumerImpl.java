/*
 *  Copyright 2016 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: ext_psofiadis
 *
 *  $Id: $ 
 */

package com.adva.mtosi.server;

import com.adva.mtosi.Utils.MtosiAddress;
import com.adva.mtosi.gui.NotificationMainHandler;
import com.adva.mtosi.gui.beans.Notification;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.apache.log4j.Logger;
import org.tmforum.mtop.fmw.wsdl.notc.v1_0.NotificationConsumer;
import org.tmforum.mtop.fmw.xsd.hdr.v1.Header;
import org.tmforum.mtop.fmw.xsd.notmsg.v1.Notify;
import org.tmforum.mtop.nra.xsd.alm.v1.AlarmType;

import javax.xml.bind.JAXBElement;
import java.text.SimpleDateFormat;
import java.util.List;


@javax.jws.WebService(
        serviceName = "NotificationConsumerHttp",
        portName = "NotificationConsumerSoapHttp",
        targetNamespace = "http://www.tmforum.org/mtop/fmw/wsdl/notc/v1-0",
        endpointInterface = "org.tmforum.mtop.fmw.wsdl.notc.v1_0.NotificationConsumer")
public class NotificationConsumerImpl implements NotificationConsumer{
  private static final Logger log = Logger.getLogger(NotificationConsumerImpl.class);

  @Override
  public void notify(Header mtopHeader, Notify mtopBody) {
    for(JAXBElement informationType : mtopBody.getMessage().getCommonEventInformation()) {
      if (informationType.getValue() instanceof AlarmType) {
        AlarmType alarmType = (AlarmType)informationType.getValue();
        log.info("Output logger received: " + alarmType.toString());
        System.out.println(alarmType.getObjectName().getRdn().get(0));
        List<Object> objects = alarmType.getVendorExtensions().getAny();
        MtosiAddress address = new MtosiAddress(alarmType.getObjectName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        formatter.setTimeZone(alarmType.getOsTime().toGregorianCalendar().getTimeZone());
        String dateString = formatter.format(alarmType.getOsTime().toGregorianCalendar().getTime());
        Notification notification = NotificationMainHandler.notificationManager.createItem(
                address.getMdName(),address.getMeName(),address.getMtosiAddress(),
                alarmType.getAdditionalText(),
                dateString,
                alarmType.getPerceivedSeverity().value(),
                alarmType.getX733EventType(),
                "True".equals(getVendorAttributeValue(objects, "Security")),
                getVendorAttributeValue(objects, "Impairment"));
        NotificationMainHandler.notificationManager.addItem(notification);
      }
    }
  }

  private String getVendorAttributeValue(List<Object> objects, String attr) {
    for(Object o : objects){
      if(((ElementNSImpl) o).getLocalName().equals(attr)){
        return ((ElementNSImpl) o).getFirstChild().getNodeValue();
      }
    }
    return "";
  }

}
