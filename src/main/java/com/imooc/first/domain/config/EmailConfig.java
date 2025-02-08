package com.imooc.first.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.ssl.enable}")
    private String smtpSslEnable;

    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private String smtpTimeout;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // 设置额外的邮件属性
        java.util.Properties properties = new java.util.Properties();
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.ssl.enable", smtpSslEnable);
        properties.put("mail.smtp.timeout", smtpTimeout);
        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }
}
