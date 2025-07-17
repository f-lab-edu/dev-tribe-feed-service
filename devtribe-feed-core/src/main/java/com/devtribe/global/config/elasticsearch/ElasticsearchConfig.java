package com.devtribe.global.config.elasticsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris[0]}")
    private String host;

    @Value("{spring.elasticsearch.password}")
    private String password;

    @Value("{spring.elasticsearch.username}")
    private String username;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
            .connectedTo(host)
            .withBasicAuth(username, password)
            .build();
    }
}
