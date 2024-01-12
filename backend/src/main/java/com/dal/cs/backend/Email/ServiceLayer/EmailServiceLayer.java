package com.dal.cs.backend.Email.ServiceLayer;

import com.dal.cs.backend.Email.ClassObject.Email;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class implements the IEmailServiceLayer that has a function to send email to a user.
 */
public  class EmailServiceLayer  implements  IEmailServiceLayer
{
    private static final Logger logger= LogManager.getLogger(EmailServiceLayer.class);
    private final String sender;
    private final String senderPassword;
    private Session session;
    public EmailServiceLayer()
    {
        sender="mail.username";
        senderPassword="mail.password";
        String host = "mail.host";
        String port = "mail.port";
        String CONFIGURATION_FILE = "src/main/resources/application.properties";
        try(InputStream propertiesFile = new FileInputStream(CONFIGURATION_FILE))
        {
            Properties properties=new Properties();
            properties.load(propertiesFile);
            Properties systemProperties = System.getProperties();
            systemProperties.put("mail.smtp.host", properties.getProperty(host));
            systemProperties.put("mail.smtp.port", properties.getProperty(port));
            systemProperties.put("mail.smtp.ssl.enable", "true");
            systemProperties.put("mail.smtp.auth", "true");
            session=Session.getInstance(systemProperties,new javax.mail.Authenticator()
            {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(properties.getProperty(sender), properties.getProperty(senderPassword));
                }
            });

        }
        catch(IOException e)
        {
           logger.error(e.getMessage());
        }

    }

    /**
     * This method sends an email to a user
     * @param email is the email object that has the to , from, subject and body.
     * @return true if the email is transmitted else return false.
     * @link: <a href="https://netcorecloud.com/tutorials/send-email-in-java-using-gmail-smtp/">...</a>
     */
    public boolean sendEmail(Email email)
    {
        try
        {
            logger.info("EmailServiceLayer: entered sendEmail()");
            logger.info("sendEmail(): setting email to, from,subject and body");
            MimeMessage message=new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            String recipient = email.getEmailRecipient();
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            String subject = email.getEmailSubject();
            message.setSubject(subject);
            String body = email.getEmailBody();
            message.setText(body);
            logger.info("sendEmail():email to, from,subject and body set successfully");
            logger.info("sendEmail():sending email");
            Transport.send(message);
            logger.info("sendEmail():email sent successfully. Returning true.");
            return true;
        }
        catch(MessagingException e)
        {
            logger.info("sendEmail():email not sent successfully. Returning false.");
            logger.error(e.getMessage());
        }
        return false;
    }
}
