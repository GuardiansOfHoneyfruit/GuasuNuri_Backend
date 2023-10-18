package com.GuardiansOfHoneyfruit.project.global.config.rabbitMq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;


    /**
     * 알림 관련 큐 정의
     */

    @Bean
    Queue notificationWorkQueue() {
        return QueueBuilder.durable("notification_work_queue")
                .withArgument("x-dead-letter-exchange", "dead_letter_exchange")
                .withArgument("x-dead-letter-routing-key", "dead_letter_queue")
                .build();
    }

    @Bean
    Queue notificationMessageQueue() {
        return new Queue("notification_message_queue", true);
    }

    @Bean
    TopicExchange notificationExchange() {
        return new TopicExchange("notification_exchange");
    }

    @Bean
    public Binding bindingNotificationWorkQueue(Queue notificationWorkQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(notificationWorkQueue).to(notificationExchange).with("notification.work");
    }

    @Bean
    public Binding bindingNotificationMessageQueue(Queue notificationMessageQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(notificationMessageQueue).to(notificationExchange).with("notification.message");
    }


    /**
     * 메타데이터 처리 작업 큐 정의
     */

    @Bean
    Queue batchWorkQueue() {
        return QueueBuilder.durable("batch_work_queue")
                .withArgument("x-dead-letter-exchange", "dead_letter_exchange")
                .withArgument("x-dead-letter-routing-key", "dead_letter_queue")
                .build();
    }

    @Bean
    TopicExchange batchExchange() {
        return new TopicExchange("batch_exchange");
    }


    /**
     *  데이터 분석 및 요청 큐 정의
     */


    @Bean
    Queue dataProcessingQueue() {
        return QueueBuilder.durable("data_processing_queue")
                .withArgument("x-dead-letter-exchange", "dead_letter_exchange")
                .withArgument("x-dead-letter-routing-key", "dead_letter_queue")
                .build();
    }

    @Bean
    Queue dataprocessingResponseQueue() {
        return QueueBuilder.durable("data_processing_response_queue")
                .withArgument("x-dead-letter-exchange", "dead_letter_exchange")
                .withArgument("x-dead-letter-routing-key", "dead_letter_queue")
                .build();
    }

    @Bean
    TopicExchange dataProcessingExchange() {
        return new TopicExchange("data_processing_exchange");
    }

    @Bean
    public Binding bindingDataProcessingRequestQueue(Queue dataProcessingQueue, TopicExchange dataProcessingExchange) {
        return BindingBuilder.bind(dataProcessingQueue).to(dataProcessingExchange).with("data.processing.request");
    }

    @Bean
    public Binding bindingDataProcessingResponseQueue(Queue dataprocessingResponseQueue, TopicExchange dataProcessingExchange) {
        return BindingBuilder.bind(dataprocessingResponseQueue).to(dataProcessingExchange).with("data.response.response");
    }

    /**
     *  공용 큐 및 RabbitMq Configuration 정의
     */

    @Bean
    Queue deadLetterQueue() {
        return new Queue("dead_letter_queue", true);
    }

    @Bean
    Exchange deadLetterExchange() {
        return ExchangeBuilder.topicExchange("dead_letter_exchange").durable(true).build();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setChannelCacheSize(25);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    public Binding bindingBatchWorkQueue(Queue batchWorkQueue, TopicExchange batchExchange) {
        return BindingBuilder.bind(batchWorkQueue).to(batchExchange).with("batch.work");
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with("dead_letter_queue")
                .noargs();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .maxAttempts(3) // 재시도 횟수 설정
                .backOffOptions(1000, 2.0, 10000) // 초기 지연, 지수, 최대 지연 시간
                .recoverer(new RejectAndDontRequeueRecoverer()) // 마지막 재시도 후 메시지를 requeue하지 않음 (DLQ로 이동)
                .build());
        return factory;
    }
}

