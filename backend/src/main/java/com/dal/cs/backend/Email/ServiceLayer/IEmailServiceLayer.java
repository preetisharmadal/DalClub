package com.dal.cs.backend.Email.ServiceLayer;

import com.dal.cs.backend.Email.ClassObject.Email;

public interface IEmailServiceLayer
{
    public boolean sendEmail(Email email);
}
