package Comcast.KEB.service;

import java.util.logging.Logger;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import Comcast.KEB.domain.Country;

@Component
public class MqSubscriber2 {
private static final Logger logger = Logger.getLogger(MqSubscriber2.class.getName());

//@JmsListener(destination = "keerthiTest")queue
@JmsListener(destination = "keerthiTopic",containerFactory="topicListenerFactory", id="testKeerthi2")//topic
/*public void onMessage(String msg)
{
	logger.info("received"+ msg);
	}
}

*/

public void onMessage(@Payload Country country)
{
	logger.info("received2"+ country);
	}
}
