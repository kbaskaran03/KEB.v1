package Comcast.KEB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import Comcast.KEB.domain.Country;

@Component

public class MqPublisher {

	@Autowired
	JmsTemplate template;
public void sendCountryMessage(String queueName,Country country) throws JmsException, JsonProcessingException
{
	template.convertAndSend(queueName, country);
	}
	
	
}
