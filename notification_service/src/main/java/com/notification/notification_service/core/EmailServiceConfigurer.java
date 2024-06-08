package com.notification.notification_service.core;

import java.util.Collection;

import org.springframework.mail.javamail.JavaMailSender;

public abstract class EmailServiceConfigurer {
    protected JavaMailSender javaMailSender;
    protected EmailServiceConfigurer(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public abstract EmailServiceConfigurer subject(String subject);
    public abstract EmailServiceConfigurer message(String msg);
    public abstract EmailServiceConfigurer async(boolean async);
    public abstract EmailServiceConfigurer receiver(String receiver);
    public abstract EmailServiceConfigurer receiver(Collection<String> receiver);
    public abstract EmailServiceConfigurer messageBundle(MessageBundle bundle);
    public abstract EmailServiceConfigurer callback(EmailCallback callback);
    public abstract void send() ;

    public static enum MessageBundle{
        HTMLBUNDLE,
        SIMPLEBUNDLE
    }
}
