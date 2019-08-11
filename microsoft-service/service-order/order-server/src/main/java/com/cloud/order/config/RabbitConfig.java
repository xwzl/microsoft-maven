package com.cloud.order.config;

/**
 * @author xuweizhi
 * @date 2019/05/23 10:31
 */
//@Configuration
public class RabbitConfig {

    //@Value("${spring.rabbitmq.host}")
    //private String addresses;
    //
    //@Value("${spring.rabbitmq.port}")
    //private String port;
    //
    //@Value("${spring.rabbitmq.username}")
    //private String username;
    //
    //@Value("${spring.rabbitmq.password}")
    //private String password;
    //
    //@Value("${spring.rabbitmq.virtual-host}")
    //private String virtualHost;
    //
    //@Value("${spring.rabbitmq.publisher-confirms}")
    //private boolean publisherConfirms;
    //
    //@Bean
    //public ConnectionFactory connectionFactory() {
    //
    //    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    //    connectionFactory.setAddresses(addresses + ":" + port);
    //    connectionFactory.setUsername(username);
    //    connectionFactory.setPassword(password);
    //    connectionFactory.setVirtualHost(virtualHost);
    //
    //    //如果要进行消息回调，则这里必须要设置为true，消息会调是为了判断方法是否执行完成
    //    connectionFactory.setPublisherConfirms(publisherConfirms);
    //
    //    return connectionFactory;
    //}
    //
    ///**
    // * 找的例子大多只是生产和消费，要实现消息的可靠性还是需要回调确认，下面记录下最简单的回调实现案例，使用的springboot搭建.
    // * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置.
    // */
    //@Bean
    //@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //public RabbitTemplate rabbitTemplateNew() {
    //    return new RabbitTemplate(connectionFactory());
    //}

}
