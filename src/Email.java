import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
public class Email {
    public static void main(String[] args) {
        final String username = "gabrenomocha@gmail.com";
        final String password = "Q1w2e3r4.";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

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
                    InternetAddress.parse("gabriel.molec@pucpr.edu.br"));
            message.setSubject("Não Conformidade encontrada!");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Uma ou mais Não Conformidade foram encontradas.");

            BodyPart attachmentPart = new MimeBodyPart();
            String filename = "/NFCGP7/conformidades.csv";
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

