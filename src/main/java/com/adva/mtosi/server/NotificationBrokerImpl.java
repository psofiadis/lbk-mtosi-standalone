/*
 *  Copyright 2016 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: ext_psofiadis
 *
 *  $Id: $ 
 */

package com.adva.mtosi.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmforum.mtop.fmw.wsdl.notb.v1_0.NotificationBroker;
import org.tmforum.mtop.fmw.wsdl.notb.v1_0.SubscribeException;
import org.tmforum.mtop.fmw.wsdl.notb.v1_0.UnsubscribeException;
import org.tmforum.mtop.fmw.wsdl.notc.v1_0.NotificationConsumer;
import org.tmforum.mtop.fmw.xsd.hdr.v1.Header;
import org.tmforum.mtop.fmw.xsd.notmsg.v1.Notify;
import org.tmforum.mtop.fmw.xsd.notmsg.v1.SubscribeRequest;
import org.tmforum.mtop.fmw.xsd.notmsg.v1.SubscribeResponse;
import org.tmforum.mtop.fmw.xsd.notmsg.v1.UnsubscribeRequest;
import org.tmforum.mtop.fmw.xsd.notmsg.v1.UnsubscribeResponse;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import java.util.Map;

public class NotificationBrokerImpl implements NotificationBroker {
  private static final Logger log = LoggerFactory.getLogger(NotificationBrokerImpl.class);

  @Override
  public void notify(Header mtopHeader, Notify mtopBody) {
    Service srv = Service.create(new QName("tmf854.v1.ws", "NotificationService"));
    NotificationConsumer notificationConsumerPort = srv.getPort(NotificationConsumer.class);
    BindingProvider bp = (BindingProvider) notificationConsumerPort;
    Map<String, Object> context = bp.getRequestContext();
    context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"/subscriber");
    notificationConsumerPort.notify(mtopHeader, mtopBody);
  }

  @Override
  public SubscribeResponse subscribe(Holder<Header> mtopHeader, SubscribeRequest mtopBody) throws SubscribeException {
    return null;
  }

  @Override
  public UnsubscribeResponse unsubscribe(Holder<Header> mtopHeader, UnsubscribeRequest mtopBody) throws UnsubscribeException {
    return null;
  }
}
