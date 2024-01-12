package Email.ServiceLayer;

import com.dal.cs.backend.Email.ClassObject.Email;
import com.dal.cs.backend.Email.ObjectBuilder.EmailBuilder;
import com.dal.cs.backend.Email.ServiceLayer.EmailServiceLayer;
import com.dal.cs.backend.Email.ServiceLayer.IEmailServiceLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailServiceLayerTest
{
    private IEmailServiceLayer iEmailServiceLayer;
    @BeforeEach
    public void beforeTestRun()
    {
        iEmailServiceLayer=new EmailServiceLayer();
    }
    @Test
    public void sendEmailTest()
    {
        Email email=new EmailBuilder().setEmailBody("Test Body")
                .setEmailRecipient("swit@dal.ca")
                .setEmailSubject("Test Subject").buildEmail();
        assertTrue((iEmailServiceLayer.sendEmail(email)));
    }
}
