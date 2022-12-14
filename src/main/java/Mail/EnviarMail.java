package Mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EnviarMail {

    private static final Properties prop = new Properties();
    private static final Session session;
    private static final String username = "callofzombiesdds@gmail.com";
    // GMAIL app specific password: https://support.google.com/accounts/answer/185833?p=InvalidSecondFactor
    private static final String password = "nwowazaypcpjxykp";

    static {
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.starttls.required", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public static void enviarPuntaje(String to, int puntaje) {

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("callofzombies2022@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject("CALL OF ZOMBIES");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent("El puntaje obtenido es: " + puntaje, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
