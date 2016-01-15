/*
 *  Copyright 2016 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: ext_psofiadis
 *
 *  $Id: $ 
 */

package com.adva.mtosi.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tmforum.mtop.fmw.wsdl.notb.v1_0.NotificationBroker;
import org.tmforum.mtop.fmw.xsd.cei.v1.CommonEventInformationType;
import org.tmforum.mtop.fmw.xsd.gen.v1.AnyListType;
import org.tmforum.mtop.fmw.xsd.hdr.v1.CommunicationPatternType;
import org.tmforum.mtop.fmw.xsd.hdr.v1.CommunicationStyleType;
import org.tmforum.mtop.fmw.xsd.hdr.v1.Header;
import org.tmforum.mtop.fmw.xsd.hdr.v1.MessageTypeType;
import org.tmforum.mtop.fmw.xsd.notmsg.v1.Notify;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.util.GregorianCalendar;

@Component
public class NotificationBrokerClient {

  private static final Logger log = LoggerFactory.getLogger(NotificationBrokerClient.class);

  @Autowired
  private NotificationBroker notificationBrokerBean;

  private org.tmforum.mtop.fmw.xsd.notmsg.v1.ObjectFactory notmsgFactory = new org.tmforum.mtop.fmw.xsd.notmsg.v1.ObjectFactory();
  private org.tmforum.mtop.fmw.xsd.gen.v1.ObjectFactory genFactory = new org.tmforum.mtop.fmw.xsd.gen.v1.ObjectFactory();



  public <T> void generateSubscriberMessages() throws DatatypeConfigurationException {
    Notify notify = new Notify();
    notify.setTopic("Inventory");
    Notify.Message message = notmsgFactory.createNotifyMessage();
    CommonEventInformationType commonEventInformationType = new CommonEventInformationType() {
      @Override
      public void setNotificationId(String value) {
        super.setNotificationId(value);
      }

      @Override
      public void setSourceTime(XMLGregorianCalendar value) {
        super.setSourceTime(value);
      }

      @Override
      public void setVendorExtensions(AnyListType value) {
        super.setVendorExtensions(value);
      }
    };
    long time = System.currentTimeMillis();
    commonEventInformationType.setNotificationId(String.valueOf(time));

    GregorianCalendar gc = new GregorianCalendar();
    gc.setTimeInMillis(time);

    XMLGregorianCalendar xc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
    commonEventInformationType.setSourceTime(xc);
    AnyListType anyListType = genFactory.createAnyListType();
    JAXBElement<T> je = new JAXBElement<>(new QName("adva.tmf864ext.v1", "alarmType"), (Class<T>) String.class, (T) "test");
    anyListType.getAny().add(je);
    commonEventInformationType.setVendorExtensions(anyListType);

    message.getCommonEventInformation().add(commonEventInformationType);
    notify.setMessage(message);

    Header header = new Header();
    header.setActivityName("notify");
    header.setMsgName("notify");

    header.setMsgType(MessageTypeType.REQUEST);
    header.setCommunicationPattern(CommunicationPatternType.SIMPLE_RESPONSE);
    header.setCommunicationStyle(CommunicationStyleType.RPC);
    header.setRequestedBatchSize(0L);

    notificationBrokerBean.notify(header, notify);
  }


}
