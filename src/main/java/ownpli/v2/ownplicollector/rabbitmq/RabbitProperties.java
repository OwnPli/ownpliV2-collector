package ownpli.v2.ownplicollector.rabbitmq;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Getter
@Setter
@ConfigurationProperties(prefix = "rabbitmq")
@Component
public class RabbitProperties {
    private String musicExchange;
    private String albumRoutingKey;
    private String trackRoutingKey;
}
