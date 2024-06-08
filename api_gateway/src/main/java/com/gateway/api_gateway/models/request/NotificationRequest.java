package com.gateway.api_gateway.models.request;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationRequest {
    private Set<String> receivers;
    private String title;
    private String message;
}
