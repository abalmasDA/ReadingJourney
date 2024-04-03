package com.readingjourney.notification.service;

import com.readingjourney.notification.exception.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.Year;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * The type Email service.
 */
@Service
public class EmailService {

  private static final Logger log = LogManager.getLogger(EmailService.class);

  private final JavaMailSender mailSender;

  private final TemplateEngine templateEngine;

  public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
  }

  /**
   * Sends an email with the specified recipient, subject, and text.
   *
   * @param email   the recipient's email address
   * @param subject the subject of the email
   */
  public void sendEmail(String email, String subject) {
    try {
      log.info("mail preparation started");
      Context context = new Context();
      context.setVariable("serviceName", "Reading Journey");
      context.setVariable("year", Year.now().getValue());
      String htmlFormat = templateEngine.process("RegistrationConfirmation", context);
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setTo(email);
      helper.setSubject(subject);
      helper.setText(htmlFormat, true);
      mailSender.send(mimeMessage);
      log.info("The email sent successfully");
    } catch (MessagingException e) {
      throw new EmailSendingException("Failed to send email");
    }

  }

}