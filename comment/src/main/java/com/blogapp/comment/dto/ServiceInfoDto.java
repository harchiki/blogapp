package com.blogapp.comment.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "blogapp")
public record ServiceInfoDto(String serviceInfo, Map<String, String> contactDetails) {
}
