package Comcast.KEB.service;

import java.util.logging.Logger;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import Comcast.KEB.domain.Country;

@Component
public class MqSubscriber {
private static final Logger logger = Logger.getLogger(MqSubscriber.class.getName());

//@JmsListener(destination = "keerthiTest")
@JmsListener(destination = "keerthiTopic",containerFactory="topicListenerFactory", id="testKeerthi1")
/*public void onMessage(String msg)
{
	logger.info("received"+ msg);
	}
}

*/

public void onMessage(@Payload Country country)
{
	logger.info("received"+ country);
	}
}
