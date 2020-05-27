package com.rzdp.winestoreapi.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "image.user")
@Data
public class ImageUserProperties {

    private int small;
    private int medium;
    private int large;
    private String format;
    private String extension;
    private String toProcessPath;
    private String processedPath;
    private String remotePath;
    private String prefix;

}
