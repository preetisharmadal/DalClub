package com.dal.cs.backend.Email.ObjectBuilder;

import com.dal.cs.backend.Email.ClassObject.Email;

public class EmailBuilder
{
    private String emailRecipient;
    private  String emailSubject;
    private String emailBody;
    private String emailAttachment;

    public EmailBuilder setEmailRecipient(String emailRecipient)
    {
        this.emailRecipient = emailRecipient;
        return this;
    }

    public EmailBuilder setEmailSubject(String emailSubject)
    {
        this.emailSubject = emailSubject;
        return this;
    }

    public EmailBuilder setEmailBody(String emailBody)
    {
        this.emailBody = emailBody;
        return this;
    }

    public EmailBuilder setEmailAttachment(String emailAttachment)
    {
        this.emailAttachment = emailAttachment;
        return this;
    }
    public Email buildEmail()
    {
        return new Email( emailRecipient, emailSubject, emailBody, emailAttachment);
    }
}
