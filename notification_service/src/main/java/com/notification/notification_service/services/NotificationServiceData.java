package com.notification.notification_service.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.notification.notification_service.core.EmailService;
import com.notification.notification_service.core.NotificationService;
import com.notification.notification_service.core.UserExtractor;
import com.notification.notification_service.core.EmailServiceConfigurer.MessageBundle;
import com.notification.notification_service.core.repository.HistoryRepository;
import com.notification.notification_service.models.entitys.History;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class NotificationServiceData implements NotificationService{

    @Value("${token.header.name}")
    private String tokenName;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserExtractor extractor;

    @Autowired
    private HistoryRepository repository;

    @Override
    public void notificateSingle(String to, String title, String message, HttpServletRequest request) throws Exception{
        salvarEmissor(request);
        emailService.sendEmail(configurer -> configurer
            .async(true)
            .messageBundle(MessageBundle.SIMPLEBUNDLE)
            .receiver(to)
            .message(message)
            .subject(title)
            .callback((hasError, $) -> {
                System.out.println($);
            })
            .send()
        );
    }

    @Override
    public void notificateAll(Set<String> to, String title, String message, HttpServletRequest request) throws Exception{
        salvarEmissor(request);
        emailService.sendEmail(configurer -> configurer
            .async(true)
            .messageBundle(MessageBundle.SIMPLEBUNDLE)
            .receiver(to)
            .message(message)
            .subject(title)
            .callback((hasError, $) -> {
                System.out.println($);
            })
            .send()
        );
    }
    
    private void salvarEmissor(HttpServletRequest request) throws Exception{
        String email = getEmailByToken(request);
        History history = new History();
        history.setIdentifierUser(email);
        repository.save(history);
    }


    private String getEmailByToken(HttpServletRequest request) throws Exception{
        String token = request.getHeader(tokenName);
        String email =  extractor.getUser(token);

        if(email == null){
            throw new Exception("erro Token invalido");
        }else if(email.isEmpty()){
            throw new Exception("erro Token invalido");
        }

        return email;
    }

}
