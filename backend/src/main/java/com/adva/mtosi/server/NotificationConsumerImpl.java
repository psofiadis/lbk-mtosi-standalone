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
import org.apache.log4j.Logger;
import org.tmforum.mtop.fmw.wsdl.notc.v1_0.NotificationConsumer;
import org.tmforum.mtop.fmw.xsd.cei.v1.CommonEventInformationType;
import org.tmforum.mtop.fmw.xsd.hdr.v1.Header;
import org.tmforum.mtop.fmw.xsd.notmsg.v1.Notify;
import org.tmforum.mtop.nra.xsd.alm.v1.AlarmType;


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
      }
    }
  }

}
