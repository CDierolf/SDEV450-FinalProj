package Classes.Email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



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

    private String uName = "";
    private String pWord = "";

    private String recipient;
    private String subject;
    private String message;
    private String event;

    public SendEmail() throws IOException {
        init();
    }

    public SendEmail(String recipient, String subject, String message) throws MessagingException, IOException {
        init();
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        sendMail();
    }

    public SendEmail(String recipient, String subject, String message, String event) throws MessagingException, IOException {
        init();
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.event = event;
        sendMail();
    }
    
    private void init() throws IOException {
        Properties props = new Properties();
        InputStream input = null;
        
        input =  getClass().getClassLoader().getResourceAsStream("resources/TicketManager.properties");
        
        props.load(input);
        this.uName = props.getProperty("sendEmailAddress");
        this.pWord = props.getProperty("sendEmailPassword");
    }

    private String getMessage() {
        return this.message;
    }

    private String getRecipient() {
        return this.recipient;
    }

    private String getEvent() {
        return this.event;
    }

    private String getSubject() {
        return this.subject;
    }

    private Properties setProps() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return prop;
    }

    private Session setSession(Properties props) {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uName, pWord);
            }
        });

        return session;
    }

    private Message constructMessage(Session session) throws AddressException, MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("ticketamateur@TicketAmateurEnterprises.com"));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(getRecipient())
        );

        message.setSubject(getSubject());
        message.setText(getMessage());

        return message;
    }

    public void sendMail() throws MessagingException {
        Properties props = setProps();
        Session session = setSession(props);
        Message message = constructMessage(session);

        try {

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

} //End Subclass SendEmail
