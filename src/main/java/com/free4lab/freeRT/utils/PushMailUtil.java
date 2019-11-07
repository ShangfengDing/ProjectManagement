package com.free4lab.freeRT.utils;

import com.free4lab.utils.enabler.MailSend;

public class PushMailUtil {

    public static void sendMail(String mailTo, String title, String content) throws Exception {
            MailSend mailSend = new MailSend();
            mailSend.setMailFrom(Constants.EMAIL_FROM);
            mailSend.setPassword(Constants.EMAIL_PWD);
            mailSend.setHost(Constants.EMAIL_HOST);
            mailSend.setMailTo(mailTo);
            mailSend.setSubject(title);
            mailSend.setMsgContent(content);
            mailSend.send(false);
    }

}
