package ownpli.v2.ownplicollector.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spotify")
public class ClientInformation {

    private String clientId;
    private String clientSecret;

}
