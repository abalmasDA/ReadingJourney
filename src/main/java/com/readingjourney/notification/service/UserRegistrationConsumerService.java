package com.readingjourney.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * The type User registration consumer service.
 */
@Service
public class UserRegistrationConsumerService {

  private static final String SUBJECT_REGISTRATION_MESSAGE = "Registration Confirmation";

  private final EmailService emailService;

  public UserRegistrationConsumerService(EmailService emailService) {
    this.emailService = emailService;
  }

  /**
   * Handles user registration notifications received through Kafka. Upon receiving a message from
   * the Kafka topic, this method utilizes the {@link EmailService} to send a registration
   * confirmation email to the provided email address.
   *
   * @param email The email address received from the Kafka message. It is assumed to be a valid
   *              email address of a user who has just completed the registration process.
   */

  @KafkaListener(topics = "${kafka.topic.userRegistration}",
      groupId = "${kafka.group.userRegistration}")
  public void listenRegistrationNotification(String email) {
    emailService.sendEmail(email, SUBJECT_REGISTRATION_MESSAGE);
  }

}