package com.notification.notification_service.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.notification.notification_service.core.EmailCallback;
import com.notification.notification_service.core.EmailServiceConfigurer;

import jakarta.mail.internet.MimeMessage;

public class EmailServiceConfigurerData extends EmailServiceConfigurer{

    private String subject;
    private String message;
    private String receiver;
    private Iterator<String> iterator;
    private boolean async; 
    private MessageBundle bundle;
    private EmailCallback callback;

    protected EmailServiceConfigurerData(JavaMailSender javaMailSender) {
        super(javaMailSender);
    }

    @Override
    public EmailServiceConfigurer subject(String subject) {
        this.subject = subject;
        return this;
    }

    @Override
    public EmailServiceConfigurer message(String msg) {
        this.message = msg;
        return this;
    }

    @Override
    public EmailServiceConfigurer async(boolean async) {
        this.async = async;
        return this;
    }

    @Override
    public EmailServiceConfigurer receiver(String receiver) {
        this.receiver = receiver;
        iterator = null;
        return this;
    }

    @Override
    public EmailServiceConfigurer receiver(Collection<String> receiver) {
        this.receiver = null;
        iterator = receiver.iterator();
        return this;
    }

    @Override
    public EmailServiceConfigurer messageBundle(MessageBundle bundle) {
        this.bundle = bundle;
        return this;
    }

    @Override
    public EmailServiceConfigurer callback(EmailCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void send() {

        try {
            if(async){
                sendAsync();
            }else{
                sendSingle();
            }
        } catch (Exception e) {
            callbackCall(true, e.getMessage());
        }
    }

    private void sendAsync() throws Exception{
        Executor executors = null;

        if(iterator != null){
            executors = Executors.newFixedThreadPool(15);

            while (iterator.hasNext()) {
                executors.execute(() -> {
                    try {
                        String next = iterator.next();
                        sendImplementation(next);
                        callbackCall(false, "mesagem enviada para: "+next);
                    } catch (Exception e) {
                        callbackCall(true, e.getMessage());
                    }
                });
            }
        }else if(receiver != null){
            executors = Executors.newSingleThreadExecutor();
            executors.execute(() -> {
                try {
                    sendImplementation(receiver);
                    callbackCall(false, "mesagem enviada para: "+receiver);
                } catch (Exception e) {
                    callbackCall(true, e.getMessage());
                }
            });
        }else{
            throw new Exception("defina um receptor ou uma lista de recepitor");
        }

    }

    private void sendSingle() throws Exception{
        if(iterator != null){

            while (iterator.hasNext()) {
                String next = iterator.next();
                sendImplementation(next);
                callbackCall(false, "mesagem enviada para: "+next);
            }
        }else if(receiver != null){
            sendImplementation(receiver);
            callbackCall(false, "mesagem enviada para: "+receiver);
        }else{
            throw new Exception("defina um receptor ou uma lista de recepitor");
        }
    }

    private void sendImplementation(String to) throws Exception{
        SimpleMailMessage simpleMailMessage;
        MimeMessage mimeMessage;
        MimeMessageHelper messageHelper;
        if(bundle == MessageBundle.SIMPLEBUNDLE){
            simpleMailMessage = new SimpleMailMessage();
            sendSimple(simpleMailMessage, to);
        }else{
            mimeMessage = javaMailSender.createMimeMessage();
            messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            sendHtml(mimeMessage, messageHelper, to);
        }
    }

    private void sendSimple(SimpleMailMessage simpleMailMessage, String to){
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(this.message);
        javaMailSender.send(simpleMailMessage);
    }
    
    private void sendHtml(MimeMessage mimeMessage, MimeMessageHelper messageHelper, String to) throws Exception{
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(this.message, true);
        javaMailSender.send(mimeMessage);
    }

    private void callbackCall(boolean error ,String msg){
        if(this.callback != null && msg != null){
            this.callback.notificate(error, msg);
        }
    }
}
