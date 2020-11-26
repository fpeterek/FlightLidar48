package org.fpeterek.flightlidar48.validator;

import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.mail.*;
import javax.mail.internet.*;

public class MailClient {

  private final Properties props = createProps();
  private final Lock lock = new ReentrantLock();

  private static Properties createProps() {
    final var props = new Properties();
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.seznam.cz");
    props.put("mail.smtp.port", "25");
    props.put("mail.smtp.ssl.trust", "smtp.seznam.cz");
    return props;
  }

  private static Authenticator createAuthenticator() {
    return new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(Config.get().emailUser, Config.get().emailPassword);
      }
    };
  }

  private Session createSession() {
    return Session.getInstance(props, createAuthenticator());
  }

  private String createEmail(int recvId) {
    return "fl-receiver-" + recvId + "@seznam.cz";
  }

  private Message createMessage(int recvId) throws MessagingException {
    Message msg = new MimeMessage(createSession());
    msg.setFrom(new InternetAddress(Config.get().emailUser));
    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(createEmail(recvId)));

    String text = """
      Hello, <br>
      <br>
      Your receiver has recently surpassed 50% error rate. Please check your receiver and make sure it works properly. <br>
      <br>
      Thank you, <br>
      <br>
      FlightLidar48 team <br>
      """;

    var bodyPart = new MimeBodyPart();
    bodyPart.setContent(text, "text/html");

    var multipart = new MimeMultipart();
    multipart.addBodyPart(bodyPart);

    msg.setContent(multipart);

    return msg;

  }

  public boolean notifyReceiver(ReceiverData recv) {
    try {
      Transport.send(createMessage(recv.id));
      return true;
    } catch (MessagingException ex) {
      System.out.println("Failed to notify receiver " + recv.id);
    }
    return false;
  }

}
