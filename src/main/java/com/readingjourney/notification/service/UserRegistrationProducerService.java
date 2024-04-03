package com.readingjourney.notification.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * The User registration producer service.
 */
@Service
public class UserRegistrationProducerService {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Value("${kafka.topic.userRegistration}")
  private String userRegistrationTopic;

  public UserRegistrationProducerService(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendRegistrationMessage(String email) {
    kafkaTemplate.send(userRegistrationTopic, email);
  }

}