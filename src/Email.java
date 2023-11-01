import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Email {
    public static void dispararEmail() {
        final String username = "gabrenomocha@gmail.com";
        final String password = "fgka abph vhwh jhmt";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("gabrenomocha@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("kelly.bettio@pucpr.br"));
            message.setSubject("Escalonamento de NC");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Uma Não Conformidade foi encontrada.\n Reverente ao trabalho de NC");

            BodyPart attachmentPart = new MimeBodyPart();
            String filename = "conformidades.csv";
            DataSource source = new FileDataSource(filename);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(filename);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

