package Classes.Email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.Email
 * @Date: Nov 7, 2019
 * @Subclass SendEmail Description:
 */
//Imports
//Begin Subclass SendEmail
public class SendEmail {

    private final String uName = "ticketamateur@gmail.com";
    private final String pWord = "6yhbv12@";

    private String recipient;
    private String message;
    private String event;

    public SendEmail() {
    }

    public SendEmail(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }

    public SendEmail(String recipient, String message, String event) throws MessagingException {
        this.recipient = recipient;
        this.message = message;
        this.event = event;

        sendMail();

    }

    public void sendMail() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uName, pWord);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ticketamateur@TicketAmateurEnterprises.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("Thomas.muck@mymail.champlain.edu, chidi117@gmail.com")
            );
            message.setSubject("Your new Events!");
            message.setText("Dear, Tom\n Based on your previous purchases, we thought"
                    + "you would enjoy the following\n"
                    + "Snoop Dog\n"
                    + "Ariana Grande\n"
                    + "The Asteroids Galaxy Tour"
                    + "Mickey Avalon");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

} //End Subclass SendEmail
