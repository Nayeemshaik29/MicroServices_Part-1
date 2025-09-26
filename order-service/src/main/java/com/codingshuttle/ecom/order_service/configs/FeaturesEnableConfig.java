package com.codingshuttle.ecom.order_service.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@Data
public class FeaturesEnableConfig {

    @Value("${features.user-tracking-enabled}")
    private boolean userTrackingEnabled;
}
