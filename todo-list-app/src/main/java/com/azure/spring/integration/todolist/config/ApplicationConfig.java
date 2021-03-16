package com.azure.spring.integration.todolist.config;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.qpid.jms.message.JmsBytesMessage;
import org.apache.qpid.jms.provider.amqp.message.AmqpJmsMessageFacade;
import org.apache.qpid.proton.amqp.Symbol;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;
import java.io.IOException;

/**
 * @author Xiaolu Dai, 2021/3/16.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    CustomMappingJackson2MessageConverter jmsMessageConverter() {
        return new CustomMappingJackson2MessageConverter();
    }

    public static class CustomMappingJackson2MessageConverter extends MappingJackson2MessageConverter {

        public static final String TYPE_ID_PROPERTY = "_type";
        public static final String CONTENT_TYPE = "application/json";

        public CustomMappingJackson2MessageConverter() {
            this.setTargetType(MessageType.BYTES);
            this.setTypeIdPropertyName(TYPE_ID_PROPERTY);
        }

        @Override
        protected BytesMessage mapToBytesMessage(Object object, Session session, ObjectWriter objectWriter) throws JMSException, IOException {
            final BytesMessage message = super.mapToBytesMessage(object, session, objectWriter);
            JmsBytesMessage msg = (JmsBytesMessage) message;
            AmqpJmsMessageFacade facade = (AmqpJmsMessageFacade) msg.getFacade();
            facade.setContentType(Symbol.valueOf(CONTENT_TYPE));
            return msg;
        }
    }
}
