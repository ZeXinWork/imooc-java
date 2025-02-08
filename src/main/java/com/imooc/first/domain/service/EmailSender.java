package com.imooc.first.domain.service;

import com.imooc.first.domain.request.GetCaptcha;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEmail(String email) {
        System.out.println("发送邮件任务提交到队列：" + email);
        rabbitTemplate.convertAndSend("emailExchange", "email.routing.key", email);
    }
}