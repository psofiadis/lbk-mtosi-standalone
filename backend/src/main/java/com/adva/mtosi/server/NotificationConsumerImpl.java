/*
 *  Copyright 2016 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: ext_psofiadis
 *
 *  $Id: $ 
 */

package com.adva.mtosi.server;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import com.adva.mtosi.Utils.MtosiAddress;
import com.adva.mtosi.gui.NotificationMainHandler;
import com.adva.mtosi.gui.beans.Notification;
import com.adva.mtosi.gui.utils.NotificationManager;
import com.adva.mtosi.gui.utils.TutorialUtils;
import com.jgoodies.binding.list.SelectionInList;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.apache.log4j.Logger;
import org.tmforum.mtop.fmw.wsdl.notc.v1_0.NotificationConsumer;
import org.tmforum.mtop.fmw.xsd.cei.v1.CommonEventInformationType;
import org.tmforum.mtop.fmw.xsd.hdr.v1.Header;
import org.tmforum.mtop.fmw.xsd.notmsg.v1.Notify;
import org.tmforum.mtop.nra.xsd.alm.v1.AlarmType;

import java.util.Iterator;
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
    for(CommonEventInformationType informationType : mtopBody.getMessage().getCommonEventInformation()) {
      if (informationType instanceof AlarmType) {
        AlarmType alarmType = (AlarmType) informationType;
        log.info("Output logger received: " + alarmType.toString());
        System.out.println(alarmType.getObjectName().getRdn().get(0));
        List<Object> objects = alarmType.getVendorExtensions().getAny();
        MtosiAddress address = new MtosiAddress(alarmType.getObjectName());
        Notification notification = NotificationMainHandler.albumManager.createItem(
                address.getMdName(),address.getMeName(),address.getMtosiAddress(),
                alarmType.getAdditionalText(),
                alarmType.getPerceivedSeverity().value(),
                alarmType.getX733EventType(),
                "True".equals(getVendorAttributeValue(objects, "Security")),
                getVendorAttributeValue(objects, "Impairement"));
        NotificationMainHandler.albumManager.addItem(notification);
//        Notification notification = new Notification(alarmType.getPerceivedSeverity().value(), alarmType.getX733EventType(),
//                "True".equals(getVendorAttributeValue(objects, "Security")),
//                getVendorAttributeValue(objects, "Impairement") );
//        Notification.NOTIFICATIONS.add(notification);
//        TutorialUtils.AlbumTableModel albumTableModel =((TutorialUtils.AlbumTableModel)NotificationMainHandler.INSTANCE.albumManagerModel.getNewAction().getModel());
//        ((SelectionInList)albumTableModel.getListModel()).

//        NotificationMainHandler.INSTANCE.table.addrepaint();
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
