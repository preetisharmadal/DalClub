package com.dal.cs.backend.Email.ClassObject;

public class Email
{
    private String emailRecipient;
    private  String emailSubject;
    private String emailBody;
    private String emailAttachment;

    public Email(String emailRecipient, String emailSubject, String emailBody, String emailAttachment)
    {
        this.emailRecipient = emailRecipient;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
        this.emailAttachment = emailAttachment;
    }

    public String getEmailRecipient() {
        return emailRecipient;
    }

    public void setEmailRecipient(String emailRecipient) {
        this.emailRecipient = emailRecipient;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getEmailAttachment() {
        return emailAttachment;
    }

    public void setEmailAttachment(String emailAttachment) {
        this.emailAttachment = emailAttachment;
    }
}
