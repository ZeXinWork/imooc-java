package com.imooc.first.domain.listener;

import com.imooc.first.domain.request.GetCaptcha;
import com.imooc.first.domain.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "emailQueue")
    public void receiveEmail(String email) {
        GetCaptcha captchaPrams = new GetCaptcha();
        captchaPrams.setEmail(email);
        emailService.sendEmail(captchaPrams);
    }
}
