package com.imooc.first.domain.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // 定义队列
    @Bean
    public Queue emailQueue() {
        return new Queue("emailQueue", true);
    }

    // 定义交换机
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("emailExchange");
    }

    // 绑定队列和交换机
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(emailQueue()).to(exchange()).with("email.routing.key");
    }


}
